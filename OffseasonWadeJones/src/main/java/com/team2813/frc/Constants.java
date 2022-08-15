// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team2813.frc;

import edu.wpi.first.math.util.Units;
import com.team2813.lib.controls.Controller;
import com.team2813.lib.controls.Axis;
import com.team2813.lib.controls.Button;



/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    // drive
    public static final double WHEEL_DIAMETER = Units.inchesToMeters(4);
    public static final double WHEEL_CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;
    public static final double GEAR_RATIO = 1 / 7.64;

    public static final double FLYWHEEL_DIAMETER = Units.inchesToMeters(4);
    public static final double FLYWHEEL_CIRCUMFERENCE = Math.PI * FLYWHEEL_DIAMETER;
    public static final double FLYWHEEL_UPDUCTION = 3.0 / 2.0;
    public static final double spoolDemand = 3600;
    public final double lowDemand = 1500;
}
