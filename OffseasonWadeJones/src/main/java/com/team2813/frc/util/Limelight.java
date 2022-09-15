package com.team2813.frc.util;

import com.team2813.lib.util.LimelightValues;
import edu.wpi.first.math.util.Units;

public class Limelight {

    private LimelightValues values = new LimelightValues();
    private static final double MOUNT_ANGLE = 38; // degrees (this is mount angle without washers)
    private static final double MOUNT_HEIGHT = 27; // inches
    private static final double TARGET_HEIGHT = 104; // inches

    private Limelight() {
        setStream(2);
    }

    private static final Limelight instance = new Limelight();

    public static Limelight getInstance() {
        return instance;
    }

    public double calculateHorizontalDistance() {
        double angle = Math.toRadians(MOUNT_ANGLE + values.getTy() + 1); // adding offset for washers
        return Units.inchesToMeters(((TARGET_HEIGHT - MOUNT_HEIGHT) / Math.tan(angle)) + 26.5);
    }

    public double getFlywheelDemand() { // returns in rpm
        double distance = calculateHorizontalDistance();
        return -414835.9 + (523050.4 * distance) - (260813.7 * Math.pow(distance, 2)) + (64555.86 * Math.pow(distance, 3)) -
                (7930.151 * Math.pow(distance, 4)) + (386.8367 * Math.pow(distance, 5));
    }

    public void setLights(boolean enable) {
        values.getLedMode().setNumber(enable ? 0 : 1);
    }

    public LimelightValues getValues() {
        return values;
    }

    public void setStream(int stream) {
        values.getStream().setNumber(stream);
    }
}
