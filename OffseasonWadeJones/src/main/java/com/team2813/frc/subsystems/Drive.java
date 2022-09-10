package com.team2813.frc.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.team2813.frc.Robot;
import com.team2813.frc.util.Units2813;
import com.team2813.lib.drive.DriveDemand;
import com.team2813.lib.imu.PigeonWrapper;
import com.team2813.lib.motors.ControlMode;
import com.team2813.lib.motors.TalonFXWrapper;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static com.team2813.frc.Constants.*;

public class Drive extends SubsystemBase {

    public static final double MAX_VELOCITY = 6380.0 / 60.0 * DRIVE_GEAR_RATIO * WHEEL_CIRCUMFERENCE;
    public static final double MAX_ANGULAR_VELOCITY = MAX_VELOCITY / (TRACKWIDTH / 2); // radians per second
    public static final double MAX_ANGULAR_ACCELERATION = Math.PI;

    private final TalonFXWrapper leftMotor = new TalonFXWrapper(LEFT_DRIVE_MASTER_ID, TalonFXInvertType.Clockwise);
    private final TalonFXWrapper rightMotor = new TalonFXWrapper(RIGHT_DRIVE_MASTER_ID, TalonFXInvertType.CounterClockwise);
    private final PigeonWrapper pigeon = new PigeonWrapper(PIGEON_ID);

    private final double kP = 0.25;
    private final double kI = 0.001;
    private final double kD = 0.125;

    private final SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(0.86746, 0.04491, 0.0072141);

    private final DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(TRACKWIDTH);

    private DifferentialDriveOdometry odometry;

    private DriveDemand speedDemand = new DriveDemand(0, 0);

    public Drive() {
        leftMotor.addFollower(LEFT_DRIVE_FOLLOWER_ID, TalonFXInvertType.FollowMaster);
        rightMotor.addFollower(RIGHT_DRIVE_FOLLOWER_ID, TalonFXInvertType.FollowMaster);

        leftMotor.setNeutralMode(NeutralMode.Brake);
        rightMotor.setNeutralMode(NeutralMode.Brake);

        leftMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_21_FeedbackIntegrated, 125);
        rightMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_21_FeedbackIntegrated, 125);

        leftMotor.configPID(kP, kI, kD);
        rightMotor.configPID(kP, kI, kD);
    }

    public DifferentialDriveKinematics getKinematics() {
        return kinematics;
    }

    public Rotation2d getRotation() {
        return Rotation2d.fromDegrees(pigeon.getHeading());
    }

    public Pose2d getPose() {
        return odometry.getPoseMeters();
    }

    @Override
    public void periodic() {
        double leftVelocity = Units2813.motorRpmToDtVelocity(leftMotor.getVelocity()); // rpm to m/s
        double rightVelocity = Units2813.motorRpmToDtVelocity(rightMotor.getVelocity());
        SmartDashboard.putNumber("Left Velocity", leftVelocity);
        SmartDashboard.putNumber("Right Velocity", rightVelocity);

        SmartDashboard.putNumber("Current Heading (Degrees)", pigeon.getHeading());

        if (odometry != null) {
            double leftRevolutions = leftMotor.getEncoderPosition();
            double leftDistance = Units2813.motorRevsToWheelRevs(leftRevolutions, DRIVE_GEAR_RATIO) * WHEEL_CIRCUMFERENCE;

            double rightRevolutions = rightMotor.getEncoderPosition();
            double rightDistance = Units2813.motorRevsToWheelRevs(rightRevolutions, DRIVE_GEAR_RATIO) * WHEEL_CIRCUMFERENCE;

            odometry.update(getRotation(), leftDistance, rightDistance);

            SmartDashboard.putString("Current Pose", odometry.getPoseMeters().toString());
        }

        if (!Robot.isAuto) speedDemand = new DriveDemand(speedDemand.getLeft() * MAX_VELOCITY, speedDemand.getRight() * MAX_VELOCITY); // m/s
        SmartDashboard.putNumber("Left Demand", speedDemand.getLeft());
        SmartDashboard.putNumber("Right Demand", speedDemand.getRight());
        DriveDemand motorDemand = Units2813.dtDemandToMotorDemand(speedDemand); // rpm

        leftMotor.set(ControlMode.VELOCITY, motorDemand.getLeft(), feedforward.calculate(speedDemand.getLeft()) / 12);
        rightMotor.set(ControlMode.VELOCITY, motorDemand.getRight(), feedforward.calculate(speedDemand.getRight()) / 12);
    }

    public void drive(DriveDemand driveDemand) {
        speedDemand = driveDemand;
    }

    public void initAutonomous(Pose2d initialPose) {
        leftMotor.setEncoderPosition(0);
        rightMotor.setEncoderPosition(0);

        pigeon.setHeading(initialPose.getRotation().getDegrees());
        odometry = new DifferentialDriveOdometry(initialPose.getRotation(), initialPose);
    }
}
