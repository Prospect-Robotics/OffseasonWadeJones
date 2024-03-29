package com.team2813.frc.commands;

import com.team2813.frc.subsystems.Drive;
import com.team2813.lib.drive.DriveDemand;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import java.util.function.BiConsumer;
import java.util.function.DoubleSupplier;

/**
 * Command to rotate by a given number of degrees.
 * Works like a unit circle:
 *      Positive # of degrees makes the robot turn counter-clockwise
 *      Negative # of degrees makes the robot turn clockwise
 */
public class RotateCommand extends CommandBase {

    private final Drive driveSubsystem;
    private final BiConsumer<Double, Double> speedsConsumer;
    private double degreesToRotateBy;
    private DoubleSupplier degreeSupplier;

    private static final ProfiledPIDController thetaController = new ProfiledPIDController(
            2,
            0,
            0,
            new TrapezoidProfile.Constraints(Drive.MAX_ANGULAR_VELOCITY, Drive.MAX_ANGULAR_ACCELERATION)
    );

    private double setpoint;
    private double startTime;
    private double timeLimit;

    public RotateCommand(double degreesToRotateBy, Drive driveSubsystem) {
        this.driveSubsystem = driveSubsystem;
        this.degreesToRotateBy = degreesToRotateBy;

        speedsConsumer = getSpeedsConsumer(driveSubsystem);
        addRequirements(driveSubsystem);
    }

    public RotateCommand(DoubleSupplier degreeSupplier, Drive driveSubsystem) {
        this.driveSubsystem = driveSubsystem;
        this.degreeSupplier = degreeSupplier;

        speedsConsumer = getSpeedsConsumer(driveSubsystem);
        addRequirements(driveSubsystem);
    }

    private static BiConsumer<Double, Double> getSpeedsConsumer(Drive driveSubsystem) {
        return new BiConsumer<Double, Double>() {
            @Override
            public void accept(Double aDouble, Double aDouble2) {
                driveSubsystem.drive(new DriveDemand(aDouble, aDouble2));
            }
        };
    }

    @Override
    public void initialize() {
        thetaController.reset(driveSubsystem.getRotation().getRadians());

        if (degreeSupplier != null) degreesToRotateBy = degreeSupplier.getAsDouble();
        setpoint = driveSubsystem.getRotation().getRadians() + Math.toRadians(degreesToRotateBy);

        startTime = Timer.getFPGATimestamp();
        timeLimit = 2.912741 - (2.578397 / (Math.pow(2, Math.abs(degreesToRotateBy) / 36.64473)));
    }

    @Override
    public void execute() {
        double error = Math.toDegrees(setpoint) - driveSubsystem.getRotation().getDegrees();
        SmartDashboard.putNumber("Rotation error (Degrees)", error);

        double angularVelocity = thetaController.calculate(driveSubsystem.getRotation().getRadians(), setpoint);
        ChassisSpeeds targetChassisSpeeds = new ChassisSpeeds(0, 0, angularVelocity);
        DifferentialDriveWheelSpeeds wheelSpeeds = driveSubsystem.getKinematics().toWheelSpeeds(targetChassisSpeeds);

        speedsConsumer.accept(wheelSpeeds.leftMetersPerSecond, wheelSpeeds.rightMetersPerSecond);
    }

    @Override
    public boolean isFinished() {
        return (Timer.getFPGATimestamp() - startTime) >= timeLimit;
    }

    @Override
    public void end(boolean interrupted) {
        speedsConsumer.accept(0.0, 0.0);
    }
}
