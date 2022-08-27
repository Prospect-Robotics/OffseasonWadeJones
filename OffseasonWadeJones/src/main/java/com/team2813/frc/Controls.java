package com.team2813.frc;

import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public final class Controls {
    // driver controls
    private static final PS4Controller DRIVER = new PS4Controller(0);
    public static final JoystickButton MANUAL_SHOOTER_BUTTON = new JoystickButton(DRIVER, 2);
    public static final JoystickButton LOW_SHOOTER_BUTTON = new JoystickButton(DRIVER, 3);

    // operator controls
    private static final PS4Controller OPERATOR = new PS4Controller(1);
    public static final JoystickButton INTAKE_PISTONS_BUTTON = new JoystickButton(OPERATOR, 4);
    public static final JoystickButton INTAKE_BUTTON = new JoystickButton(OPERATOR, 6);
    public static final JoystickButton OUTTAKE_BUTTON = new JoystickButton(OPERATOR, 5);
    public static final JoystickButton SPOOL_BUTTON = new JoystickButton(OPERATOR, 7);
    public static final JoystickButton EXTEND_BUTTON = new JoystickButton(OPERATOR, 1);
    public static final JoystickButton MID_CLIMB_BUTTON = new JoystickButton(OPERATOR, 2);
    public static final JoystickButton RISE_UP_BUTTON = new JoystickButton(OPERATOR, 3);
}
