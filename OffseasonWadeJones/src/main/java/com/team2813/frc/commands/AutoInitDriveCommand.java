package com.team2813.frc.commands;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.team2813.frc.subsystems.Drive;
import edu.wpi.first.wpilibj2.command.InstantCommand;

import static com.team2813.frc.Constants.*;

/**
 * Use before the first FollowCommand in an AutoRoutine, if there is one
 */
public class AutoInitDriveCommand extends InstantCommand {

    public AutoInitDriveCommand(String trajectoryName, Drive driveSubsystem) {
        super(() -> {
            PathPlannerTrajectory trajectory = PathPlanner.loadPath(trajectoryName, AUTO_MAX_VEL, AUTO_MAX_ACCEL);
            driveSubsystem.initAutonomous(trajectory.getInitialPose());
        }, driveSubsystem);
    }

    public AutoInitDriveCommand(String trajectoryName, boolean reversed, Drive driveSubsystem) {
        super(() -> {
            PathPlannerTrajectory trajectory = PathPlanner.loadPath(trajectoryName, AUTO_MAX_VEL, AUTO_MAX_ACCEL, reversed);
            driveSubsystem.initAutonomous(trajectory.getInitialPose());
        }, driveSubsystem);
    }
}
