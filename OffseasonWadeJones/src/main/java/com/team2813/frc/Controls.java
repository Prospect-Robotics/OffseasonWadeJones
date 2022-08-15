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
}
