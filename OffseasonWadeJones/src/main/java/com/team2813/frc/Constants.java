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

    public static final class ShooterConstants{
        private static final double FLYWHEEL_DIAMETER = Units.inchesToMeters(4);
        public static final double FLYWHEEL_CIRCUMFERENCE = Math.PI * FLYWHEEL_DIAMETER;
        public static final double FLYWHEEL_UPDUCTION = 3.0 / 2.0;
        private final double spoolDemand = 3600;
        private final double lowDemand = 1500;
        
        // driver
        private static Controller driveJoystick = new Controller(0);
        private static Button pivotButton = driveJoystick.button(1);
        private static Axis driveX = driveJoystick.axis(0);
        private static Axis driveY = driveJoystick.axis(3);
        private static Axis driveSteer = driveJoystick.axis(0);
        private static Axis driveForward = driveJoystick.axis(3);
        private static Axis driveReverse = driveJoystick.axis(2);
        private static Button shooterButton = driveJoystick.button(4);
        private static Button manualShootButton = driveJoystick.button(2);
        private static Button lowShootButton = driveJoystick.button(3);
    
        // operator
        private static Controller operatorJoystick = new Controller(1);
        private static Button intakeInButton = operatorJoystick.button(6);
        private static Button intakeOutButton = operatorJoystick.button(5);
        private static Button spoolButton = operatorJoystick.button(7);
        private static Button extendButton = operatorJoystick.button(1);
        private static Button midClimbButton = operatorJoystick.button(2);
        private static Button riseButton = operatorJoystick.button(3);

        public static Button getPivotButton() {
            return pivotButton;
          }
      
        public static Axis getDriveX() {
            return driveX;
          }
      
        public static Axis getDriveY() {
            return driveY;
          }
      
        public static Axis getDriveSteer() {
            return driveSteer;
          }
      
        public static Axis getDriveForward() {
            return driveForward;
          }
      
        public static Axis getDriveReverse() {
            return driveReverse;
          }
      
        public static Button getIntakeInButton() {
            return intakeInButton;
          }
      
        public static Button getIntakeOutButton() {
            return intakeOutButton;
          }
      
        public static Button getShooterButton() {
              return shooterButton;
          }
      
        public static Button getManualShootButton() {
              return manualShootButton;
          }
      
        public static Button getSpoolButton() {
              return spoolButton;
          }
      
        public static Button getLowShootButton() {
              return lowShootButton;
          }
      
        public static Button getMidClimbButton() {
              return midClimbButton;
          }
      
        public static Button getRiseButton() {
              return riseButton;
          }
      
        public static Button getExtendButton() {
              return extendButton;
          }
    }




}
