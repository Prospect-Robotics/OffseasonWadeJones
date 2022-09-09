package com.team2813.frc.util;

import com.team2813.lib.util.LimelightValues;
import edu.wpi.first.math.util.Units;

public class Limelight {

    private LimelightValues values = new LimelightValues();
    private static final double MOUNT_ANGLE = 38; // degrees (this is mount angle without washers)
    private static final double MOUNT_HEIGHT = 27; // inches
    private static final double TARGET_HEIGHT = 104; // inches

    private Limelight() {
        setStream(0);
    }

    private static Limelight instance = new Limelight();

    public static Limelight getInstance() {
        return instance;
    }

    public double calculateHorizontalDistance() {
        double angle = Math.toRadians(MOUNT_ANGLE + values.getTy() + 1); // adding offset for washers
        return Units.inchesToMeters(((TARGET_HEIGHT - MOUNT_HEIGHT) / Math.tan(angle)) + 26.5);
    }

    public double getFlywheelDemand() { // returns in rpm
        double distance = calculateHorizontalDistance();
        return -5444.444 + (6602.661 * distance) - (1606.313 * Math.pow(distance, 2)) + (134.8647 * Math.pow(distance, 3));
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
