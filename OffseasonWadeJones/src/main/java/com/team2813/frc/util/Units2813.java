package com.team2813.frc.util;

import com.team2813.lib.drive.DriveDemand;

import static com.team2813.frc.Constants.GEAR_RATIO;
import static com.team2813.frc.Constants.WHEEL_CIRCUMFERENCE;

public class Units2813 {
    public static double ticksToMotorRevs(double ticks, int cpr) {
        return ticks / cpr;
    }

    public static int motorRevsToTicks(double revs, int cpr) {
        return (int) (revs * cpr);
    }

    public static double motorRevsToWheelRevs(double revolutions, double gearRatio) {
        return revolutions * gearRatio;
    }

    public static double wheelRevsToMotorRevs(double revs, double gearRatio) {
        return revs / gearRatio;
    }

    // drivetrain velocity to motor rpms
    public static double dtVelocityToMotorRpm(double speed) {
        return wheelRevsToMotorRevs(speed / WHEEL_CIRCUMFERENCE, GEAR_RATIO) * 60;
    }

    public static double motorRpmToDtVelocity(double rpm) {
        return motorRevsToWheelRevs(rpm, GEAR_RATIO) * WHEEL_CIRCUMFERENCE / 60;
    }

    public static DriveDemand dtDemandToMotorDemand(DriveDemand demand) { // input in m/s output in motor rpm
        return new DriveDemand(dtVelocityToMotorRpm(demand.getLeft()), dtVelocityToMotorRpm(demand.getRight()));
    }
}
