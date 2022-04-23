package com.team2813.lib.drive;

public class VelocityDrive {
    private double maxVelocity;

    public VelocityDrive(double maxVelocity) {
        this.maxVelocity = maxVelocity;
    }

    public DriveDemand getVelocityDriveDemand(DriveDemand driveDemand) {
        return new DriveDemand(driveDemand.getLeft() * maxVelocity, driveDemand.getRight() * maxVelocity);
    }
}
