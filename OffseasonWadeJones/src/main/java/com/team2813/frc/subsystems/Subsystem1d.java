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

    private Motor motor;
    protected PeriodicIO periodicIO = new PeriodicIO();
    private boolean zeroed = false;
    private boolean motionMagicEnabled = true;

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

    @Override
    public void periodic() {
        readPeriodicInputs();
        writePeriodicOutputs();
    }

    private void writePeriodicOutputs() {
        resetIfAtLimit();
        if (!periodicIO.openLoop && motionMagicEnabled)
            motor.set(ControlMode.MOTION_MAGIC, periodicIO.demand);
    }

    private void readPeriodicInputs() {
        periodicIO.positionTicks = motor.getEncoderPosition();
    }

    public synchronized void resetIfAtLimit() {
        if (periodicIO.limitSwitch) {
            zeroSensors();
        }
    }

    public void zeroSensors() {
        motor.setEncoderPosition(0);
        zeroed = true;
    }

    public boolean isZeroed() {
        return zeroed;
    }

    public void enableMotionMagic(boolean enabled) {
        motionMagicEnabled = enabled;
    }

    public boolean isMotionMagicEnabled() {
        return motionMagicEnabled;
    }

    class PeriodicIO {
        double demand;

        boolean limitSwitch;

        double positionTicks;

        boolean openLoop = false;
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
        periodicIO.demand = encoderTicks;
        periodicIO.openLoop = false;
    }

    synchronized void setPosition(P position) {
        setPosition(position.getPos());
    }

    protected Motor getMotor() {
        return motor;
    }
}
