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

import java.util.function.DoubleSupplier;

import static com.team2813.frc.Constants.*;

public class Shooter extends SubsystemBase 
{
    private final TalonFXWrapper flywheelMotor = new TalonFXWrapper(FLYWHEEL_MASTER_ID, TalonFXInvertType.Clockwise);
    private final SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(0.86105, 0.18297, 0.045778);
    private final Limelight limelight = Limelight.getInstance();
    private final double marginOfError = 100;

    private double demand;
    private double snapshotSpeed = 0; // Speed of flywheel when the demand changes
    private double prevFlywheelVelocity;
    private double flywheelVelocity;

    public Shooter() {
        flywheelMotor.addFollower(FLYWHEEL_FOLLOWER_ID, TalonFXInvertType.OpposeMaster);
        flywheelMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_21_FeedbackIntegrated, 125);
        flywheelMotor.configPID(0.035677, 0.00015, 0);
    }

    public boolean isFlywheelReady() {
        return Math.abs(Units2813.motorRevsToWheelRevs(flywheelMotor.getVelocity(), FLYWHEEL_UPDUCTION) - demand) < marginOfError;
    }

    @Override
    public void periodic() {
        prevFlywheelVelocity = flywheelVelocity;
        flywheelVelocity = Units2813.motorRevsToWheelRevs(flywheelMotor.getVelocity(), FLYWHEEL_UPDUCTION);
        double error = demand - flywheelVelocity;
        SmartDashboard.putNumber("Flywheel Demand", demand);
        SmartDashboard.putNumber("Flywheel Velocity", flywheelVelocity);
        SmartDashboard.putNumber("Flywheel Error", error);
        SmartDashboard.putNumber("Flywheel Encoder", flywheelMotor.getEncoderPosition());
        SmartDashboard.putNumber("Distance to Target", limelight.calculateHorizontalDistance());
    }

    public void setFlywheelRPM(double demand) {
        this.demand = demand;
        snapshotSpeed = Units2813.motorRevsToWheelRevs(flywheelMotor.getVelocity(), FLYWHEEL_UPDUCTION);

        double motorDemand = Units2813.wheelRevsToMotorRevs(demand, FLYWHEEL_UPDUCTION);
        flywheelMotor.set(ControlMode.VELOCITY, motorDemand, feedforward.calculate(motorDemand / 60) / 12);
    }

    public void setFlywheelRPM(DoubleSupplier demandSupplier) {
        setFlywheelRPM(demandSupplier.getAsDouble());
    }
}
