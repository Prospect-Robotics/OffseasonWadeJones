package com.team2813.frc;

import com.team2813.frc.commands.*;
import edu.wpi.first.wpilibj2.command.*;

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
            new InstantCommand(() -> ROBOT_CONTAINER.getShooter().setFlywheelRPM(DEFAULT_SHOOT_DEMAND), ROBOT_CONTAINER.getShooter()),
            new AutoInitDriveCommand("TwoBall_Basic", true, ROBOT_CONTAINER.getDrive()),
            new FollowCommand("TwoBall_Basic", true, ROBOT_CONTAINER.getDrive()),
            new AutoShootCommand(ROBOT_CONTAINER.getDrive(), ROBOT_CONTAINER.getMagazine(), ROBOT_CONTAINER.getShooter())
    )),
    TWO_BALL_KNOCK("2-ball Knock", new SequentialCommandGroup(
            new InstantCommand(() -> ROBOT_CONTAINER.getShooter().setFlywheelRPM(DEFAULT_SHOOT_DEMAND), ROBOT_CONTAINER.getShooter()),
            new AutoInitDriveCommand("TwoBall_Knock", ROBOT_CONTAINER.getDrive()),
            new FollowCommand("TwoBall_Knock", ROBOT_CONTAINER.getDrive()),
            new AutoShootCommand(ROBOT_CONTAINER.getDrive(), ROBOT_CONTAINER.getMagazine(), ROBOT_CONTAINER.getShooter())
    )),
    THREE_BALL_BASIC("3-ball Basic", new SequentialCommandGroup(
            new InstantCommand(() -> ROBOT_CONTAINER.getShooter().setFlywheelRPM(DEFAULT_SHOOT_DEMAND), ROBOT_CONTAINER.getShooter()),
            new AutoInitDriveCommand("ThreeBall_Basic_TarmacShoot", ROBOT_CONTAINER.getDrive()),
            new FollowCommand("ThreeBall_Basic_TarmacShoot", ROBOT_CONTAINER.getDrive()),
            new AutoShootCommand(ROBOT_CONTAINER.getDrive(), ROBOT_CONTAINER.getMagazine(), ROBOT_CONTAINER.getShooter()),
            new RotateCommand(180, ROBOT_CONTAINER.getDrive()),
            new AutoIntakeCommand(ROBOT_CONTAINER.getIntake(), ROBOT_CONTAINER.getMagazine()),
            new FollowCommand("ThreeBall_Basic_IntakeThenShoot", ROBOT_CONTAINER.getDrive()),
            new AutoStopIntakeCommand(ROBOT_CONTAINER.getIntake(), ROBOT_CONTAINER.getMagazine()),
            new AutoShootCommand(ROBOT_CONTAINER.getDrive(), ROBOT_CONTAINER.getMagazine(), ROBOT_CONTAINER.getShooter())
    )),
    THREE_BALL_KNOCK("3-ball Knock", new SequentialCommandGroup(
            new InstantCommand(() -> ROBOT_CONTAINER.getShooter().setFlywheelRPM(DEFAULT_SHOOT_DEMAND), ROBOT_CONTAINER.getShooter()),
            new AutoInitDriveCommand("TwoBall_Basic", true, ROBOT_CONTAINER.getDrive()),
            new FollowCommand("TwoBall_Basic", true, ROBOT_CONTAINER.getDrive()),
            new AutoShootCommand(ROBOT_CONTAINER.getDrive(), ROBOT_CONTAINER.getMagazine(), ROBOT_CONTAINER.getShooter()),
            new RotateCommand(90, ROBOT_CONTAINER.getDrive()),
            new AutoIntakeCommand(ROBOT_CONTAINER.getIntake(), ROBOT_CONTAINER.getMagazine()),
            new ParallelCommandGroup(
                    new FollowCommand("ThreeBall_Knock", ROBOT_CONTAINER.getDrive()),
                    new SequentialCommandGroup(
                            new WaitCommand(1.125),
                            new AutoStopIntakeCommand(ROBOT_CONTAINER.getIntake(), ROBOT_CONTAINER.getMagazine())
                    )
            ),
            new AutoShootCommand(ROBOT_CONTAINER.getDrive(), ROBOT_CONTAINER.getMagazine(), ROBOT_CONTAINER.getShooter())
    )),
    THREE_BALL_INTAKE("3-ball Intake", new SequentialCommandGroup(
            new InstantCommand(() -> ROBOT_CONTAINER.getShooter().setFlywheelRPM(DEFAULT_SHOOT_DEMAND), ROBOT_CONTAINER.getShooter()),
            new AutoInitDriveCommand("ThreeBall_Intake_TarmacShoot", true, ROBOT_CONTAINER.getDrive()),
            new FollowCommand("ThreeBall_Intake_TarmacShoot", true, ROBOT_CONTAINER.getDrive()),
            new AutoShootCommand(ROBOT_CONTAINER.getDrive(), ROBOT_CONTAINER.getMagazine(), ROBOT_CONTAINER.getShooter()),
            new RotateCommand(135, ROBOT_CONTAINER.getDrive()),
            new AutoIntakeCommand(ROBOT_CONTAINER.getIntake(), ROBOT_CONTAINER.getMagazine()),
            new FollowCommand("ThreeBall_Intake_IntakeThenShoot", ROBOT_CONTAINER.getDrive()),
            new AutoStopIntakeCommand(ROBOT_CONTAINER.getIntake(), ROBOT_CONTAINER.getMagazine()),
            new AutoShootCommand(ROBOT_CONTAINER.getDrive(), ROBOT_CONTAINER.getMagazine(), ROBOT_CONTAINER.getShooter()),
            new RotateCommand(180, ROBOT_CONTAINER.getDrive()),
            new AutoIntakeCommand(ROBOT_CONTAINER.getIntake(), ROBOT_CONTAINER.getMagazine()),
            new FollowCommand("ThreeBall_Intake_IntakeOpponentBall", ROBOT_CONTAINER.getDrive()),
            new WaitCommand(1),
            new AutoStopIntakeCommand(ROBOT_CONTAINER.getIntake(), ROBOT_CONTAINER.getMagazine())

    )),
    FOUR_BALL_BASIC("4-ball Basic", new SequentialCommandGroup(
            new InstantCommand(() -> ROBOT_CONTAINER.getShooter().setFlywheelRPM(DEFAULT_SHOOT_DEMAND), ROBOT_CONTAINER.getShooter()),
            new AutoInitDriveCommand("TwoBall_Basic", true, ROBOT_CONTAINER.getDrive()),
            new FollowCommand("TwoBall_Basic", true, ROBOT_CONTAINER.getDrive()),
            new AutoShootCommand(ROBOT_CONTAINER.getDrive(), ROBOT_CONTAINER.getMagazine(), ROBOT_CONTAINER.getShooter()),
            new RotateCommand(180, ROBOT_CONTAINER.getDrive()),
            new AutoIntakeCommand(ROBOT_CONTAINER.getIntake(), ROBOT_CONTAINER.getMagazine()),
            new FollowCommand("FourBall_Basic_IntakeThenShoot", ROBOT_CONTAINER.getDrive()),
            new AutoStopIntakeCommand(ROBOT_CONTAINER.getIntake(), ROBOT_CONTAINER.getMagazine()),
            new AutoShootCommand(ROBOT_CONTAINER.getDrive(), ROBOT_CONTAINER.getMagazine(), ROBOT_CONTAINER.getShooter())
    )),
    FOUR_BALL_REVERSED("4-Ball Reversed", new SequentialCommandGroup(
            new InstantCommand(() -> ROBOT_CONTAINER.getShooter().setFlywheelRPM(DEFAULT_SHOOT_DEMAND), ROBOT_CONTAINER.getShooter()),
            new AutoInitDriveCommand("FourBall_Reversed_TarmacShoot", true, ROBOT_CONTAINER.getDrive()),
            new FollowCommand("FourBall_Reversed_TarmacShoot", true, ROBOT_CONTAINER.getDrive()),
            new AutoShootCommand(ROBOT_CONTAINER.getDrive(), ROBOT_CONTAINER.getMagazine(), ROBOT_CONTAINER.getShooter()),
            new RotateCommand(180, ROBOT_CONTAINER.getDrive()),
            new AutoIntakeCommand(ROBOT_CONTAINER.getIntake(), ROBOT_CONTAINER.getMagazine()),
            new FollowCommand("FourBall_Reversed_IntakeThenShoot", ROBOT_CONTAINER.getDrive()),
            new AutoStopIntakeCommand(ROBOT_CONTAINER.getIntake(), ROBOT_CONTAINER.getMagazine()),
            new AutoShootCommand(ROBOT_CONTAINER.getDrive(), ROBOT_CONTAINER.getMagazine(), ROBOT_CONTAINER.getShooter())
    )),
    FOUR_BALL_HP("4-Ball HP Station", new SequentialCommandGroup(
            new InstantCommand(() -> ROBOT_CONTAINER.getShooter().setFlywheelRPM(DEFAULT_SHOOT_DEMAND), ROBOT_CONTAINER.getShooter()),
            new AutoInitDriveCommand("TwoBall_Basic", true, ROBOT_CONTAINER.getDrive()),
            new FollowCommand("TwoBall_Basic", true, ROBOT_CONTAINER.getDrive()),
            new AutoShootCommand(ROBOT_CONTAINER.getDrive(), ROBOT_CONTAINER.getMagazine(), ROBOT_CONTAINER.getShooter()),
            new RotateCommand(180, ROBOT_CONTAINER.getDrive()),
            new AutoIntakeCommand(ROBOT_CONTAINER.getIntake(), ROBOT_CONTAINER.getMagazine()),
            new FollowCommand("FourBall_HP_IntakeThenShoot", ROBOT_CONTAINER.getDrive()),
            new AutoStopIntakeCommand(ROBOT_CONTAINER.getIntake(), ROBOT_CONTAINER.getMagazine()),
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
