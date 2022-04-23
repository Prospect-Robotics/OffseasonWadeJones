package com.team2813.lib.drive;

import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;

public class DriveDemand {
    private double left;
    private double right;

    public DriveDemand(double left, double right) {
        this.left = left;
        this.right = right;
    }

    public DriveDemand(DifferentialDriveWheelSpeeds wheelSpeeds) {
        left = wheelSpeeds.leftMetersPerSecond;
        right = wheelSpeeds.rightMetersPerSecond;
    }

    public DriveDemand reverse() {
        double temp = left;
        left = -right;
        right = -temp;
        return this;
    }

    public DriveDemand flip() {
        double temp = left;
        left = right;
        right = temp;
        return this;
    }

    public double getLeft() {
        return left;
    }

    public double getRight() {
        return right;
    }

    @Override
    public String toString() {
        return "(" + left + ", " + right + ")";
    }

    public boolean equals(DriveDemand obj) {
        return obj.left == left && obj.right == right;
    }
}
