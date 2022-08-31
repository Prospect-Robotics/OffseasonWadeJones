package com.team2813.frc.commands;

import com.team2813.frc.subsystems.Drive;
import com.team2813.lib.drive.CurvatureDrive;
import com.team2813.lib.drive.DriveDemand;
import edu.wpi.first.wpilibj2.command.CommandBase;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class DefaultDriveCommand extends CommandBase {

    private final DoubleSupplier forwardThrottle;
    private final DoubleSupplier backwardThrottle;
    private final DoubleSupplier steer;
    private final BooleanSupplier pivot;

    private final Drive driveSubsystem;

    private final CurvatureDrive curvatureDrive = new CurvatureDrive();

    public DefaultDriveCommand(DoubleSupplier forwardThrottle, DoubleSupplier backwardThrottle, DoubleSupplier steer, BooleanSupplier pivot, Drive driveSubsystem) {
        this.forwardThrottle = forwardThrottle;
        this.backwardThrottle = backwardThrottle;
        this.steer = steer;
        this.pivot = pivot;

        this.driveSubsystem = driveSubsystem;
        addRequirements(driveSubsystem);
    }

    @Override
    public void execute() {
        driveSubsystem.drive(curvatureDrive.getDemand(
                forwardThrottle.getAsDouble(),
                backwardThrottle.getAsDouble(),
                steer.getAsDouble(),
                pivot.getAsBoolean()
        ));
    }

    @Override
    public void end(boolean interrupted) {
        driveSubsystem.drive(new DriveDemand(0, 0));
    }
}
