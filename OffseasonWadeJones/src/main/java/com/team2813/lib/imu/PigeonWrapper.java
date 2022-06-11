package com.team2813.lib.imu;

import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.sensors.PigeonIMUConfiguration;
import com.ctre.phoenix.sensors.PigeonIMU_StatusFrame;

public class PigeonWrapper extends PigeonIMU {

    public PigeonWrapper(int deviceNumber) {
        super(deviceNumber);

        configAllSettings(new PigeonIMUConfiguration());
        setStatusFramePeriod(PigeonIMU_StatusFrame.CondStatus_9_SixDeg_YPR, 10);
    }

    public double getHeading() {
        if (getState() == PigeonState.Ready) {
            return getYaw();
        }
        return 0;
    }

    public void setHeading(double angle) {
        setYaw(angle);
        setAccumZAngle(0);
    }
}
