package com.team2813.lib.motors;

import com.revrobotics.CANSparkMax;

import java.util.ArrayList;
import java.util.List;

public class SparkMaxWrapper extends CANSparkMax {
    List<CANSparkMax> followers = new ArrayList<>();

    /**
     * Create a new object to control a SPARK MAX motor Controller
     *
     * @param deviceId The device ID.
     * @param type     The motor type connected to the controller. Brushless motor wires must be connected
     *                 to their matching colors and the hall sensor must be plugged in. Brushed motors must be
     *                 connected to the Red and Black terminals only.
     * @param inverted Whether the motor is inverted
     */
    public SparkMaxWrapper(int deviceId, MotorType type, boolean inverted) {
        super(deviceId, type);

        restoreFactoryDefaults();

        enableVoltageCompensation(12);
        setClosedLoopRampRate(0);
        setPeriodicFramePeriod(PeriodicFrame.kStatus2, 20);
        setSmartCurrentLimit(40);

        setInverted(inverted);
    }

    /**
     * Sets the PID coefficients for the PID at the given slot id
     * @param slotID Is the gain schedule slot, the value is a number between 0 and 3. Each slot has
     *     its own set of gain values and can be changed in each control frame using SetReference().
     */
    public void configPIDF(double p, double i, double d, double f, int slotID) {
        getPIDController().setP(p, slotID);
        getPIDController().setI(i, slotID);
        getPIDController().setD(d, slotID);
        getPIDController().setFF(f, slotID);
    }

    public void configPIDF(double p, double i, double d, double f) {
        configPIDF(p, i, d, f, 0);
    }

    public void addFollower(int deviceNumber, boolean inverted) {
        CANSparkMax follower = new CANSparkMax(deviceNumber, MotorType.kBrushless);
        follower.follow(this, inverted);
        followers.add(follower); // add to the list to preserve CANSparkMax follower object
    }
}
