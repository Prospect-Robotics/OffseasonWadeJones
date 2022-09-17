// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team2813.frc;

import com.team2813.frc.commands.RotateCommand;
import com.team2813.frc.commands.def.DefaultClimberCommand;
import com.team2813.frc.commands.def.DefaultDriveCommand;
import com.team2813.frc.commands.def.DefaultShooterCommand;
import com.team2813.frc.commands.util.LockFunctionCommand;
import com.team2813.frc.commands.ClimberRetractCommand;
import com.team2813.frc.subsystems.*;
import com.team2813.frc.util.Lightshow;
import com.team2813.frc.util.Limelight;
import com.team2813.frc.util.ShuffleboardData;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.*;

import static com.team2813.frc.Constants.*;
import static com.team2813.frc.Controls.*;
import static com.team2813.frc.Robot.LIGHTSHOW;



/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer
{
    // The robot's subsystems and commands are defined here...
    private final Shooter shooter = new Shooter();
    private final Magazine magazine = new Magazine();
    private final Climber climber = new Climber();
    private final Intake intake = new Intake();
    private final Drive drive = new Drive();

    private final XboxController controller = new XboxController(0);

    private final Limelight limelight = Limelight.getInstance();
    
    
    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer()
    {
        drive.setDefaultCommand(new DefaultDriveCommand(
                controller::getRightTriggerAxis,
                controller::getLeftTriggerAxis,
                controller::getLeftX,
                PIVOT_BUTTON::get,
                drive
        ));
        shooter.setDefaultCommand(new DefaultShooterCommand(shooter));
        climber.setDefaultCommand(new DefaultClimberCommand(climber));

        // Configure the button bindings
        configureButtonBindings();
    }

    // Package-private subsystem getters
    Drive getDrive() {
        return drive;
    }

    Intake getIntake() {
        return intake;
    }

    Magazine getMagazine() {
        return magazine;
    }

    Shooter getShooter() {
        return shooter;
    }
    
    
    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings()
    {
        // Add button to command mappings here.
        // See https://docs.wpilib.org/en/stable/docs/software/commandbased/binding-commands-to-triggers.html
        INTAKE_PISTONS_BUTTON.whenPressed(intake::toggle, intake);

        INTAKE_BUTTON.whenHeld(new ParallelCommandGroup(
                new InstantCommand(intake::intake, intake),
                new InstantCommand(magazine::intake, magazine)
        ));
        INTAKE_BUTTON.whenReleased(new ParallelCommandGroup(
                new InstantCommand(intake::stop, intake),
                new InstantCommand(magazine::stop, magazine)
        ));

        OUTTAKE_BUTTON.whenHeld(new ParallelCommandGroup(
                new InstantCommand(intake::outtake, intake),
                new InstantCommand(magazine::outtake, magazine)
        ));
        OUTTAKE_BUTTON.whenReleased(new ParallelCommandGroup(
                new InstantCommand(intake::stop, intake),
                new InstantCommand(magazine::stop, magazine)
        ));

        SPOOL_BUTTON.whenPressed(new SequentialCommandGroup(
                new InstantCommand(() -> LIGHTSHOW.setLight(Lightshow.Light.SPOOLING)),
                new LockFunctionCommand(shooter::hasSpiked, () -> shooter.setFlywheelRPM(MANUAL_SHOOT_DEMAND), shooter),
                new WaitUntilCommand(shooter::isFlywheelReady),
                new InstantCommand(() -> LIGHTSHOW.setLight(Lightshow.Light.READY_TO_SHOOT)),
                new WaitUntilCommand(() -> (AUTO_SHOOTER_BUTTON.get() || MANUAL_SHOOTER_BUTTON.get() || LOW_SHOOTER_BUTTON.get()))
        ));

        AUTO_SHOOTER_BUTTON.whenHeld(new SequentialCommandGroup(
                //new RotateCommand(() -> -limelight.getValues().getTx(), drive),
//                new InstantCommand(() -> LIGHTSHOW.setLight(Lightshow.Light.SPOOLING)),
//                new LockFunctionCommand(shooter::hasSpiked, () -> shooter.setFlywheelRPM(1700), shooter),
//                new WaitUntilCommand(shooter::isFlywheelReady),
//                new InstantCommand(() -> LIGHTSHOW.setLight(Lightshow.Light.READY_TO_SHOOT)),
                new InstantCommand(magazine::shoot, magazine),
                new WaitUntilCommand(() -> !AUTO_SHOOTER_BUTTON.get())
        ));
        AUTO_SHOOTER_BUTTON.whenReleased(() -> new ParallelCommandGroup(
                new InstantCommand(() -> LIGHTSHOW.setLight(Lightshow.Light.ENABLED)),
                new InstantCommand(magazine::stop, magazine)
        ));

        MANUAL_SHOOTER_BUTTON.whenHeld(new SequentialCommandGroup(
                new InstantCommand(() -> shooter.setFlywheelRPM(MANUAL_SHOOT_DEMAND), shooter),
                new InstantCommand(magazine::shoot, magazine),
                new WaitUntilCommand(() -> !MANUAL_SHOOTER_BUTTON.get())
        ));
        MANUAL_SHOOTER_BUTTON.whenReleased(new ParallelCommandGroup(
                new InstantCommand(() -> LIGHTSHOW.setLight(Lightshow.Light.ENABLED)),
                new InstantCommand(magazine::stop, magazine)
        ));

        LOW_SHOOTER_BUTTON.whenHeld(new SequentialCommandGroup(
                new InstantCommand(() -> LIGHTSHOW.setLight(Lightshow.Light.SPOOLING)),
                new LockFunctionCommand(shooter::hasSpiked, () -> shooter.setFlywheelRPM(LOW_SHOOT_DEMAND), shooter),
                new WaitUntilCommand(shooter::isFlywheelReady),
                new InstantCommand(() -> LIGHTSHOW.setLight(Lightshow.Light.READY_TO_SHOOT)),
                new InstantCommand(magazine::lowShoot, magazine),
                new WaitUntilCommand(() -> !LOW_SHOOTER_BUTTON.get())
        ));
        LOW_SHOOTER_BUTTON.whenReleased(new ParallelCommandGroup(
                new InstantCommand(() -> LIGHTSHOW.setLight(Lightshow.Light.ENABLED)),
                new InstantCommand(magazine::stop, magazine)
        ));

        EXTEND_BUTTON.whenPressed(new SequentialCommandGroup(
                new InstantCommand(() -> LIGHTSHOW.setLight(Lightshow.Light.CLIMBING)),
                new LockFunctionCommand(climber::positionReached, () -> climber.setPosition(Climber.Position.EXTENDED), climber),
                new WaitUntilCommand(MID_CLIMB_BUTTON::get)
        ));

        MID_CLIMB_BUTTON.whenPressed(new SequentialCommandGroup(
                new InstantCommand(intake::deploy, intake),
                new ClimberRetractCommand(climber)
        ));

        RISE_UP_BUTTON.whenPressed(new SequentialCommandGroup(
                new InstantCommand(climber::extendPistons, climber),
                new LockFunctionCommand(climber::positionReached, () -> climber.setPosition(Climber.Position.RISE_POS), climber),
                new LockFunctionCommand(climber::positionReached, () -> climber.setPosition(Climber.Position.NEXT_BAR), climber),
                new InstantCommand(climber::retractPistons, climber),
                new WaitCommand(0.75),
                new ClimberRetractCommand(climber)
        ));
    }
    
    
    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand()
    {
        AutoRoutine selectedRoutine = ShuffleboardData.routineChooser.getSelected();
        return selectedRoutine.getCommand();
    }

    public void addAutoRoutines() {
        for (AutoRoutine routine : AutoRoutine.values()) {
            ShuffleboardData.routineChooser.addOption(routine.getName(), routine);
        }
    }
}
