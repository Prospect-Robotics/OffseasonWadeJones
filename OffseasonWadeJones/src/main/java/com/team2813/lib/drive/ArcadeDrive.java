package com.team2813.lib.drive;

public class ArcadeDrive {

    public DriveDemand getDemand(double steer, double throttle) {
        return new DriveDemand(throttle + steer, throttle - steer);
    }
}
