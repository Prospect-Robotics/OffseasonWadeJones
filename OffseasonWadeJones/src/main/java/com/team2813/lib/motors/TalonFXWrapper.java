package com.team2813.lib.motors;

import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.team2813.frc.util.Units2813;

import java.util.ArrayList;
import java.util.List;

public class TalonFXWrapper extends TalonFX {
    List<TalonFX> followers = new ArrayList<>();

    /**
     * Constructor
     * @param deviceNumber [0,62]
     * @param canbus Name of the CANbus; can be a SocketCAN interface (on Linux),
     *               or a CANivore device name or serial number
     */
    public TalonFXWrapper(int deviceNumber, String canbus) {
        super(deviceNumber, canbus);

        configAllSettings(new TalonFXConfiguration());

        enableVoltageCompensation(true);
        configClosedloopRamp(0);
        configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 40, 40, 0.25));
        configVoltageCompSaturation(12);
    }

    /**
     * Constructor
     * @param deviceNumber [0,62]
     */
    public TalonFXWrapper(int deviceNumber) {
        super(deviceNumber);

        configAllSettings(new TalonFXConfiguration());

        enableVoltageCompensation(true);
        configClosedloopRamp(0);
        configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 40, 40, 0.25));
        configVoltageCompSaturation(12);
    }

    public void set(TalonFXControlMode controlMode, double demand, double feedForward) {
        switch (controlMode){
            case Velocity:
                demand = Units2813.motorRevsToTicks(demand / 60 / 10, 2048);
                break;
            case MotionMagic:
                demand = Units2813.motorRevsToTicks(demand, 2048);
                break;
        }
        set(controlMode, demand, DemandType.ArbitraryFeedForward, feedForward);
    }

    public void set(TalonFXControlMode controlMode, double demand) {
        set(controlMode, demand, 0);
    }

    public double getEncoderPosition() {
        return Units2813.ticksToMotorRevs(getSelectedSensorPosition(), 2048);
    }

    /**
     * Sets the PID coefficients for the PID at the given slot id (0 for Primary PID, 1 for Auxiliary PID)
     * @param slotID Parameter slot for the constant
     */
    public void configPIDF(double p, double i, double d, double f, int slotID) {
        config_kP(slotID, p);
        config_kI(slotID, i);
        config_kD(slotID, d);
        config_kF(slotID, f);
    }

    public void configMotionMagic(double maxVelocity, double maxAcceleration) {
        configMotionCruiseVelocity(maxVelocity);
        configMotionAcceleration(maxAcceleration);
    }

    public void addFollower(int deviceNumber, String canbus, TalonFXInvertType invertType) {
        TalonFX follower = new TalonFX(deviceNumber, canbus);
        follower.follow(this);
        follower.setInverted(invertType);
        followers.add(follower); // add to the list to preserve TalonFX follower object
    }

    public void addFollower(int deviceNumber, TalonFXInvertType invertType) {
        TalonFX follower = new TalonFX(deviceNumber);
        follower.follow(this);
        follower.setInverted(invertType);
        followers.add(follower); // add to the list to preserve TalonFX follower object
    }

    public double getVelocity() { // returns in rpm
        return Units2813.ticksToMotorRevs(getSelectedSensorVelocity(), 2048) * 10 * 60; // from ticks/100ms to rpm
    }
}
