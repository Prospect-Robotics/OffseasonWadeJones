package com.team2813.frc.commands;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.team2813.frc.subsystems.Drive;
import com.team2813.lib.drive.DriveDemand;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.RamseteCommand;

import java.util.function.BiConsumer;

import static com.team2813.frc.Constants.*;

public class FollowCommand extends RamseteCommand {

    private static final RamseteController follower = new RamseteController();
    private final BiConsumer<Double, Double> speedsConsumer;
    private final PathPlannerTrajectory trajectory;

    public FollowCommand(String trajectoryName, Drive driveSubsystem) {
        super(
                PathPlanner.loadPath(trajectoryName, AUTO_MAX_VEL, AUTO_MAX_ACCEL),
                driveSubsystem::getPose,
                follower,
                driveSubsystem.getKinematics(),
                getSpeedsConsumer(driveSubsystem),
                driveSubsystem
        );

        speedsConsumer = getSpeedsConsumer(driveSubsystem);

        trajectory = PathPlanner.loadPath(trajectoryName, AUTO_MAX_VEL, AUTO_MAX_ACCEL);
    }

    public FollowCommand(String trajectoryName, boolean reversed, Drive driveSubsystem) {
        super(
                PathPlanner.loadPath(trajectoryName, AUTO_MAX_VEL, AUTO_MAX_ACCEL, reversed),
                driveSubsystem::getPose,
                follower,
                driveSubsystem.getKinematics(),
                getSpeedsConsumer(driveSubsystem),
                driveSubsystem
        );

        speedsConsumer = getSpeedsConsumer(driveSubsystem);

        trajectory = PathPlanner.loadPath(trajectoryName, AUTO_MAX_VEL, AUTO_MAX_ACCEL, reversed);
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
        super.initialize();

        SmartDashboard.putString("Goal Pose", trajectory.getEndState().poseMeters.toString());
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);

        speedsConsumer.accept(0.0, 0.0);
    }
}
