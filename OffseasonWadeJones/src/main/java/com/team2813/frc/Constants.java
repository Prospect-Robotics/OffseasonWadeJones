// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team2813.frc;

import edu.wpi.first.math.util.Units;



/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    // CAN IDs

    // Drive
    public static final int LEFT_DRIVE_MASTER_ID = 2;
    public static final int LEFT_DRIVE_FOLLOWER_ID = 3;
    public static final int RIGHT_DRIVE_MASTER_ID = 4;
    public static final int RIGHT_DRIVE_FOLLOWER_ID = 5;

    // Intake
    public static final int INTAKE_ID = 6;

    // Shooter
    public static final int FLYWHEEL_MASTER_ID = 7;
    public static final int FLYWHEEL_FOLLOWER_ID = 8;

    // Magazine
    public static final int MAGAZINE_MASTER_ID = 9;
    public static final int MAGAZINE_FOLLOWER_ID = 10;
    public static final int KICKER_ID = 11;

    // Climber
    public static final int CLIMBER_ID = 12;

    // Misc
    public static final int PIGEON_ID = 13;
    public static final int PCM_ID = 14;
    public static final int CANIFIER_ID = 15;

    // Drive Constants
    public static final double WHEEL_DIAMETER = Units.inchesToMeters(4);
    public static final double WHEEL_CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;
    public static final double DRIVE_GEAR_RATIO = 1 / 7.64;
    public static final double TRACKWIDTH = Units.inchesToMeters(28.5); // meters

    // Shooter Constants
    public static final double FLYWHEEL_UPDUCTION = 3.0 / 2.0;

    public static final double DEFAULT_SHOOT_DEMAND = 250; // RPM
    public static final double MANUAL_SHOOT_DEMAND = 1900; // RPM
    public static final double LOW_SHOOT_DEMAND = 1050; // RPM

    // Auto Constants
    public static final double AUTO_MAX_VEL = 1.5;
    public static final double AUTO_MAX_ACCEL = 4;
}
