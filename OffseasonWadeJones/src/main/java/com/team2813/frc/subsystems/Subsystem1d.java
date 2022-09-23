package com.team2813.frc.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.team2813.lib.motors.ControlMode;
import com.team2813.lib.motors.Motor;
import com.team2813.lib.motors.SparkMaxWrapper;
import com.team2813.lib.motors.TalonFXWrapper;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Subsystem1d<P extends Subsystem1d.Position> extends SubsystemBase {

    protected final Motor motor;
    protected Position currentPosition;

    public Subsystem1d(SparkMaxWrapper motor) {
        this.motor = motor;
        motor.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus1, 25);
        motor.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus2, 100);
        motor.set(ControlMode.DUTY_CYCLE, 0);
        motor.setIdleMode(CANSparkMax.IdleMode.kBrake);
    }

    public Subsystem1d(TalonFXWrapper motor) {
        this.motor = motor;
        motor.setStatusFramePeriod(StatusFrameEnhanced.Status_21_FeedbackIntegrated, 125);
        motor.set(ControlMode.DUTY_CYCLE, 0);
        motor.setNeutralMode(NeutralMode.Brake);
    }

    public void zeroSensors() {
        motor.setEncoderPosition(0);
    }

    /*==========================
     * POSITION
     * ==========================*/

    protected interface Position {
        /**
         * @return encoder ticks of given position
         */
        double getPos();

        Position getMin();

        Position getMax();
    }

    public void setPosition(double encoderTicks) {
        motor.set(ControlMode.MOTION_MAGIC, encoderTicks);
    }

    public void setPosition(P position) {
        currentPosition = position;
        setPosition(position.getPos());
    }
}
