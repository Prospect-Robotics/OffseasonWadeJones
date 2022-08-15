// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team2813.frc;

import com.team2813.frc.commands.DefaultShooterCommand;
import com.team2813.frc.commands.ExampleCommand;
import com.team2813.frc.commands.util.LockFunctionCommand;
import com.team2813.frc.subsystems.ExampleSubsystem;
import com.team2813.frc.subsystems.Shooter;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static com.team2813.frc.Constants.*;
import static com.team2813.frc.Controls.*;



/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer
{
    // The robot's subsystems and commands are defined here...
    private final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
    private final Shooter shooter = new Shooter();
    
    private final ExampleCommand autoCommand = new ExampleCommand(exampleSubsystem);
    
    
    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer()
    {
        shooter.setDefaultCommand(new DefaultShooterCommand(shooter));

        // Configure the button bindings
        configureButtonBindings();
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
        MANUAL_SHOOTER_BUTTON.whenActive(new SequentialCommandGroup(
                new LockFunctionCommand(shooter::isFlywheelReady, () -> shooter.setFlywheelRPM(MANUAL_SHOOT_DEMAND))
                // Magazine stuff here
        ));

        LOW_SHOOTER_BUTTON.whenActive(new SequentialCommandGroup(
                new LockFunctionCommand(shooter::isFlywheelReady, () -> shooter.setFlywheelRPM(LOW_SHOOT_DEMAND))
                // Magazine stuff here
        ));


    }
    
    
    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand()
    {
        // An ExampleCommand will run in autonomous
        return autoCommand;
    }
}
