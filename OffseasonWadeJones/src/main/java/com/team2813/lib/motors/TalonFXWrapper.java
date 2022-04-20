package com.team2813.lib.motors;

import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;

import java.util.ArrayList;
import java.util.List;

public class TalonFXWrapper extends TalonFX {
    List<TalonFX> followers = new ArrayList<>();

    public TalonFXWrapper(int deviceNumber, String canbus) {
        super(deviceNumber, canbus);

        configAllSettings(new TalonFXConfiguration());

        enableVoltageCompensation(true);
        configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 40, 40, 0.25));
        configVoltageCompSaturation(12);
    }

    public TalonFXWrapper(int deviceNumber) {
        super(deviceNumber);

        configAllSettings(new TalonFXConfiguration());

        enableVoltageCompensation(true);
        configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 40, 40, 0.25));
        configVoltageCompSaturation(12);
    }

    public void configPIDF(double p, double i, double d, double f, int slotID) {
        config_kP(slotID, p);
        config_kI(slotID, i);
        config_kD(slotID, d);
        config_kF(slotID, f);
    }

    public void addFollower(int deviceNumber, String canbus, TalonFXInvertType invertType) {
        TalonFX follower = new TalonFX(deviceNumber, canbus);
        follower.follow(this);
        follower.setInverted(invertType);
        followers.add(follower);
    }

    public void addFollower(int deviceNumber, TalonFXInvertType invertType) {
        TalonFX follower = new TalonFX(deviceNumber);
        follower.follow(this);
        follower.setInverted(invertType);
        followers.add(follower);
    }
}
