package com.team2813.frc.subsystems;

import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.team2813.frc.util.Units2813;
import com.team2813.lib.drive.DriveDemand;
import com.team2813.lib.imu.PigeonWrapper;
import com.team2813.lib.motors.ControlMode;
import com.team2813.lib.motors.TalonFXWrapper;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static com.team2813.frc.Constants.*;

public class Drive extends SubsystemBase {

    public static final double MAX_VELOCITY = 0;

    private final TalonFXWrapper leftMotor = new TalonFXWrapper(LEFT_DRIVE_MASTER_ID, TalonFXInvertType.Clockwise);
    private final TalonFXWrapper rightMotor = new TalonFXWrapper(RIGHT_DRIVE_MASTER_ID, TalonFXInvertType.CounterClockwise);
    private final PigeonWrapper pigeon = new PigeonWrapper(PIGEON_ID);

    private final double kP = 0.195;
    private final double kI = 0;
    private final double kD = 0.13;

    private final SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(0.70102, 0.044202, 0.006353);

    public Drive() {
        leftMotor.addFollower(LEFT_DRIVE_FOLLOWER_ID, TalonFXInvertType.FollowMaster);
        rightMotor.addFollower(RIGHT_DRIVE_FOLLOWER_ID, TalonFXInvertType.FollowMaster);

        leftMotor.configPID(kP, kI, kD);
        rightMotor.configPID(kP, kI, kD);
    }

    @Override
    public void periodic() {
        double leftVelocity = Units2813.motorRpmToDtVelocity(leftMotor.getVelocity()); // rpm to m/s
        double rightVelocity = Units2813.motorRpmToDtVelocity(rightMotor.getVelocity());
        SmartDashboard.putNumber("Left Velocity", leftVelocity);
        SmartDashboard.putNumber("Right Velocity", rightVelocity);

        SmartDashboard.putNumber("Current Heading (Degrees)", pigeon.getHeading());
    }

    public void drive(DriveDemand driveDemand) {
        DriveDemand velocityDemand = new DriveDemand(driveDemand.getLeft() * MAX_VELOCITY, driveDemand.getRight() * MAX_VELOCITY); // m/s
        DriveDemand motorDemand = Units2813.dtDemandToMotorDemand(velocityDemand); // rpm

        leftMotor.set(ControlMode.VELOCITY, motorDemand.getLeft(), feedforward.calculate(velocityDemand.getLeft()));
        rightMotor.set(ControlMode.VELOCITY, motorDemand.getRight(), feedforward.calculate(velocityDemand.getRight()));
    }
}
