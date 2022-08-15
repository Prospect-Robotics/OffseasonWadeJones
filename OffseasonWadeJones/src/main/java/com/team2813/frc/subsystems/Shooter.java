package com.team2813.frc.subsystems;

import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.team2813.lib.motors.ControlMode;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.team2813.lib.motors.TalonFXWrapper;
import com.team2813.frc.util.Limelight;
import com.team2813.frc.util.Units2813;

import static com.team2813.frc.Constants.*;

public class Shooter extends SubsystemBase 
{
    private final double kP = 0.75;
    private final double kI = 0.0025;
    private final double kD = 2.5;

    private final TalonFXWrapper flywheel = new TalonFXWrapper(7, TalonFXInvertType.Clockwise);
    private final SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(1.2795, 0.18545, 0.081586);
    private final Limelight limelight = Limelight.getInstance();

    private double demand;
    private boolean isFullyRevvedUp;

    public Shooter() {
        flywheel.addFollower(8, TalonFXInvertType.OpposeMaster);
        flywheel.setStatusFramePeriod(StatusFrameEnhanced.Status_21_FeedbackIntegrated, 125);
        flywheel.configPID(kP, kI, kD);
    }

    public boolean isFlywheelReady() {
        return Math.abs(Units2813.motorRevsToWheelRevs(flywheel.getVelocity(), FLYWHEEL_UPDUCTION) - demand) < 25;
    }

    boolean isFullyRevvedUp() {
        return isFullyRevvedUp;
    }

    @Override
    public void periodic() {
        double flywheelVelocity = Units2813.motorRevsToWheelRevs(flywheel.getVelocity(), FLYWHEEL_UPDUCTION);
        double error = demand - flywheelVelocity;
        SmartDashboard.putNumber("Flywheel Demand", demand);
        SmartDashboard.putNumber("Flywheel Velocity", flywheelVelocity);
        SmartDashboard.putNumber("Flywheel Error", error);
        SmartDashboard.putNumber("Flywheel Encoder", flywheel.getEncoderPosition());
        SmartDashboard.putNumber("Distance to Target", limelight.calculateHorizontalDistance());
    }

    public void setFlywheelRPM(double demand) {
        double motorDemand = Units2813.wheelRevsToMotorRevs(demand, FLYWHEEL_UPDUCTION);
        flywheel.set(ControlMode.VELOCITY, motorDemand, feedforward.calculate(motorDemand / 60));
    }
}