package com.team2813.frc.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.revrobotics.CANSparkMax;
import com.team2813.lib.motors.ControlMode;
import com.team2813.lib.motors.Motor;
import com.team2813.lib.motors.SparkMaxWrapper;
import com.team2813.lib.motors.TalonFXWrapper;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Subsystem1d<P extends Subsystem1d.Position> extends SubsystemBase {

    private final Motor motor;
    protected PeriodicIO periodicIO = new PeriodicIO();
    private boolean motionMagicEnabled = true;
    private boolean demandSet = false; // done so that motor does not go to a specific position until a position has been set

    public Subsystem1d(SparkMaxWrapper motor) {
        this.motor = motor;
        motor.set(ControlMode.DUTY_CYCLE, 0);
        motor.setIdleMode(CANSparkMax.IdleMode.kBrake);
    }

    public Subsystem1d(TalonFXWrapper motor) {
        this.motor = motor;
        motor.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 125);
        motor.set(ControlMode.DUTY_CYCLE, 0);
        motor.setNeutralMode(NeutralMode.Brake);
    }

    // if overridden, overriding method should call this method (use super keyword)
    @Override
    public void periodic() {
        readPeriodicInputs();
        writePeriodicOutputs();
    }

    private void writePeriodicOutputs() {
        if (demandSet && motionMagicEnabled) motor.set(ControlMode.MOTION_MAGIC, periodicIO.demand);
    }

    private void readPeriodicInputs() {
        periodicIO.positionTicks = motor.getEncoderPosition();
    }

    public void zeroSensors() {
        motor.setEncoderPosition(0);
    }

    public void enableMotionMagic(boolean enabled) {
        motionMagicEnabled = enabled;
    }

    public boolean isMotionMagicEnabled() {
        return motionMagicEnabled;
    }

    class PeriodicIO {
        double demand;

        double positionTicks;
    }

    /*==========================
     * POSITION
     * ==========================*/

    protected interface Position<E> {
        /**
         * @return encoder ticks of given position
         */
        double getPos();

        E getMin();

        E getMax();
    }

    synchronized void setPosition(double encoderTicks) {
        demandSet = true;
        enableMotionMagic(true);
        periodicIO.demand = encoderTicks;
    }

    synchronized void setPosition(P position) {
        setPosition(position.getPos());
    }

    protected Motor getMotor() {
        return motor;
    }
}
