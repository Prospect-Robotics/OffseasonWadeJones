package com.team2813.frc;

import com.team2813.frc.commands.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import static com.team2813.frc.Constants.*;
import static com.team2813.frc.Robot.ROBOT_CONTAINER;

public enum AutoRoutine {

    ZERO_BALL_KNOCK("0-ball Knock", new SequentialCommandGroup(
            new AutoInitDriveCommand("ZeroBall", ROBOT_CONTAINER.getDrive()),
            new FollowCommand("ZeroBall", ROBOT_CONTAINER.getDrive())
    )),
    ZERO_BALL_INTAKE("0-ball Intake", new SequentialCommandGroup(
            new AutoIntakeCommand(ROBOT_CONTAINER.getIntake(), ROBOT_CONTAINER.getMagazine()),
            new AutoInitDriveCommand("ZeroBall", ROBOT_CONTAINER.getDrive()),
            new FollowCommand("ZeroBall", ROBOT_CONTAINER.getDrive()),
            new WaitCommand(1),
            new AutoStopIntakeCommand(ROBOT_CONTAINER.getIntake(), ROBOT_CONTAINER.getMagazine())
    )),
    TWO_BALL_BASIC("2-ball Basic", new SequentialCommandGroup(
            new InstantCommand(() -> ROBOT_CONTAINER.getShooter().setFlywheelRPM(DEFAULT_SHOOT_DEMAND)),
            new AutoInitDriveCommand("TwoBall_Basic", true, ROBOT_CONTAINER.getDrive()),
            new FollowCommand("TwoBall_Basic", true, ROBOT_CONTAINER.getDrive()),
            new AutoShootCommand(ROBOT_CONTAINER.getDrive(), ROBOT_CONTAINER.getMagazine(), ROBOT_CONTAINER.getShooter())
    )),
    TWO_BALL_KNOCK("2-ball Knock", new SequentialCommandGroup(
            new InstantCommand(() -> ROBOT_CONTAINER.getShooter().setFlywheelRPM(DEFAULT_SHOOT_DEMAND)),
            new AutoInitDriveCommand("TwoBall_Knock", ROBOT_CONTAINER.getDrive()),
            new FollowCommand("TwoBall_Knock", ROBOT_CONTAINER.getDrive()),
            new AutoShootCommand(ROBOT_CONTAINER.getDrive(), ROBOT_CONTAINER.getMagazine(), ROBOT_CONTAINER.getShooter())
    )),
    THREE_BALL_BASIC("3-ball Basic", new SequentialCommandGroup(
            new InstantCommand(() -> ROBOT_CONTAINER.getShooter().setFlywheelRPM(DEFAULT_SHOOT_DEMAND)),
            new AutoInitDriveCommand("ThreeBall_Shoot", true, ROBOT_CONTAINER.getDrive()),
            new FollowCommand("ThreeBall_Shoot", ROBOT_CONTAINER.getDrive()),
            new AutoShootCommand(ROBOT_CONTAINER.getDrive(), ROBOT_CONTAINER.getMagazine(), ROBOT_CONTAINER.getShooter()),
            new AutoIntakeCommand(ROBOT_CONTAINER.getIntake(), ROBOT_CONTAINER.getMagazine()),
            //new WaitCommand(1),
            new FollowCommand("ThreeBall_Shoot", true, ROBOT_CONTAINER.getDrive()),
            new AutoStopIntakeCommand(ROBOT_CONTAINER.getIntake(), ROBOT_CONTAINER.getMagazine()),
            new FollowCommand("ThreeBall_Shoot", true, ROBOT_CONTAINER.getDrive()),
            new AutoShootCommand(ROBOT_CONTAINER.getDrive(), ROBOT_CONTAINER.getMagazine(), ROBOT_CONTAINER.getShooter())
    ))
    THREE_BALL_KNOCK("3-ball Knock", new SequentialCommandGroup(
        new InstantCommand(() -> ROBOT_CONTAINER.getShooter().setFlywheelRPM(DEFAULT_SHOOT_DEMAND)),
        new FollowCommand("ThreeBall_Shoot", ROBOT_CONTAINER.getDrive()),
        new AutoShootCommand(ROBOT_CONTAINER.getDrive(), ROBOT_CONTAINER.getMagazine(), ROBOT_CONTAINER.getShooter()),
        new FollowCommand("ThreeBall_Shoot", ROBOT_CONTAINER.getDrive()),
        new AutoIntakeCommand(ROBOT_CONTAINER.getIntake(), ROBOT_CONTAINER.getMagazine()),
        //new WaitCommand(1),
        new AutoStopIntakeCommand(ROBOT_CONTAINER.getIntake(), ROBOT_CONTAINER.getMagazine()),
        new FollowCommand("ThreeBall_Shoot", ROBOT_CONTAINER.getDrive()),
        new AutoShootCommand(ROBOT_CONTAINER.getDrive(), ROBOT_CONTAINER.getMagazine(), ROBOT_CONTAINER.getShooter())
    )),
    THREE_BALL_COMPLEX("3-ball Complex", new SequentialCommandGroup(
        new InstantCommand(() -> ROBOT_CONTAINER.getShooter().setFlywheelRPM(DEFAULT_SHOOT_DEMAND)),
        new FollowCommand("ThreeBall_Shoot", ROBOT_CONTAINER.getDrive()),
        new AutoShootCommand(ROBOT_CONTAINER.getDrive(), ROBOT_CONTAINER.getMagazine(), ROBOT_CONTAINER.getShooter()),
        new AutoShootCommand(ROBOT_CONTAINER.getDrive(), ROBOT_CONTAINER.getMagazine(), ROBOT_CONTAINER.getShooter()),
        new FollowCommand("ThreeBall_Shoot", ROBOT_CONTAINER.getDrive()),
        new AutoIntakeCommand(ROBOT_CONTAINER.getIntake(), ROBOT_CONTAINER.getMagazine()),
        new WaitCommand(1),
        new AutoStopIntakeCommand(ROBOT_CONTAINER.getIntake(), ROBOT_CONTAINER.getMagazine()),
        new FollowCommand("ThreeBall_Shoot", ROBOT_CONTAINER.getDrive()),
        new AutoShootCommand(ROBOT_CONTAINER.getDrive(), ROBOT_CONTAINER.getMagazine(), ROBOT_CONTAINER.getShooter()),
        new FollowCommand("ThreeBall_Shoot", ROBOT_CONTAINER.getDrive()),
        new AutoIntakeCommand(ROBOT_CONTAINER.getIntake(), ROBOT_CONTAINER.getMagazine()),
        new WaitCommand(1),
        new AutoStopIntakeCommand(ROBOT_CONTAINER.getIntake(), ROBOT_CONTAINER.getMagazine()),
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
