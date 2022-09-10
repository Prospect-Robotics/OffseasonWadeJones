package com.team2813.frc;

import com.team2813.frc.commands.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import static com.team2813.frc.Constants.*;
import static com.team2813.frc.Robot.ROBOT_CONTAINER;

public enum AutoRoutine {

    ZERO_BALL("0-ball", new SequentialCommandGroup(
            new AutoInitDriveCommand("ZeroBall", ROBOT_CONTAINER.getDrive()),
            new FollowCommand("ZeroBall", ROBOT_CONTAINER.getDrive())
    )),
    ONE_BALL("1-ball", new SequentialCommandGroup(
            new AutoInitDriveCommand("ZeroBall", ROBOT_CONTAINER.getDrive()),
            new FollowCommand("ZeroBall", ROBOT_CONTAINER.getDrive()),
            new AutoIntakeCommand(ROBOT_CONTAINER.getIntake(), ROBOT_CONTAINER.getMagazine()),
            new WaitCommand(1),
            new AutoStopIntakeCommand(ROBOT_CONTAINER.getIntake(), ROBOT_CONTAINER.getMagazine())
    )),
    TWO_BALL("2-ball", new SequentialCommandGroup(
            new InstantCommand(() -> ROBOT_CONTAINER.getShooter().setFlywheelRPM(DEFAULT_SHOOT_DEMAND)),
            new AutoInitDriveCommand("TwoBall_Intake_Ball", ROBOT_CONTAINER.getDrive()),
            new FollowCommand("TwoBall_Intake_Ball", ROBOT_CONTAINER.getDrive()),
            new AutoIntakeCommand(ROBOT_CONTAINER.getIntake(), ROBOT_CONTAINER.getMagazine()),
            new WaitCommand(1),
            new AutoStopIntakeCommand(ROBOT_CONTAINER.getIntake(), ROBOT_CONTAINER.getMagazine()),
            new RotateCommand(180, ROBOT_CONTAINER.getDrive()),
            new FollowCommand("TwoBall_Shoot_Balls", ROBOT_CONTAINER.getDrive()),
            new AutoShootCommand(ROBOT_CONTAINER.getDrive(), ROBOT_CONTAINER.getMagazine(), ROBOT_CONTAINER.getShooter())
    )),
    ROTATE_90_TEST("Rotate 90 Test", new RotateCommand(90, ROBOT_CONTAINER.getDrive())),
    ROTATE_180_TEST("Rotate 180 Test", new RotateCommand(180, ROBOT_CONTAINER.getDrive())),
    TEST("Follow Test", new SequentialCommandGroup(
            new AutoInitDriveCommand("Test", ROBOT_CONTAINER.getDrive()),
            new FollowCommand("Test", ROBOT_CONTAINER.getDrive())
    ));

    private final String name;
    private final Command command;

    AutoRoutine(String name, Command command) {
        this.name = name;
        this.command = command;
    }

    public String getName() {
        return name;
    }

    public Command getCommand() {
        return command;
    }
}
