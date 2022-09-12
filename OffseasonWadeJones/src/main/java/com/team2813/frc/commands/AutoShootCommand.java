package com.team2813.frc.commands;

import com.team2813.frc.commands.util.LockFunctionCommand;
import com.team2813.frc.subsystems.Drive;
import com.team2813.frc.subsystems.Magazine;
import com.team2813.frc.subsystems.Shooter;
import com.team2813.frc.util.Lightshow;
import com.team2813.frc.util.Limelight;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

import static com.team2813.frc.Robot.LIGHTSHOW;

public class AutoShootCommand extends SequentialCommandGroup {

    private static final Limelight limelight = Limelight.getInstance();

    public AutoShootCommand(Drive driveSubsystem, Magazine magazineSubsystem, Shooter shooterSubsystem) {
        super(
                new RotateCommand(limelight.getValues().getTx(), driveSubsystem),
                new InstantCommand(() -> LIGHTSHOW.setLight(Lightshow.Light.SPOOLING)),
                new LockFunctionCommand(shooterSubsystem::isSpiking, () -> shooterSubsystem.setFlywheelRPM(limelight.getFlywheelDemand()), shooterSubsystem),
                new WaitUntilCommand(shooterSubsystem::isFlywheelReady),
                new InstantCommand(() -> LIGHTSHOW.setLight(Lightshow.Light.READY_TO_SHOOT)),
                new InstantCommand(magazineSubsystem::shoot, magazineSubsystem),
                new WaitCommand(2),
                new InstantCommand(() -> LIGHTSHOW.setLight(Lightshow.Light.AUTONOMOUS)),
                new InstantCommand(magazineSubsystem::stop, magazineSubsystem)
        );
    }
}
