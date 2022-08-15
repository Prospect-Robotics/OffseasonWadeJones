package com.team2813.lib.imu;

import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.sensors.PigeonIMUConfiguration;
import com.ctre.phoenix.sensors.PigeonIMU_StatusFrame;

public class PigeonWrapper extends PigeonIMU {

    public PigeonWrapper(int deviceNumber) {
        super(deviceNumber);

        configAllSettings(new PigeonIMUConfiguration());
        setStatusFramePeriod(PigeonIMU_StatusFrame.CondStatus_9_SixDeg_YPR, 20);
    }

    public double getHeading() {
        return getYaw();
    }

    public void setHeading(double angle) {
        setYaw(angle);
        setAccumZAngle(0);
    }
}