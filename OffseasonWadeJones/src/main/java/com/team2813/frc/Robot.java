// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team2813.frc;

import com.team2813.frc.util.Lightshow;
import com.team2813.frc.util.Limelight;
import com.team2813.frc.util.ShuffleboardData;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import static com.team2813.frc.Constants.*;



/**
 * The VM is configured to automatically run this class, and to call the methods corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot
{
    private final Limelight limelight = Limelight.getInstance();

    private Command autonomousCommand;
    
    public static RobotContainer ROBOT_CONTAINER;

    public static final Lightshow LIGHTSHOW = new Lightshow(CANIFIER_ID);

    public static boolean isAuto = false;
    
    
    /**
     * This method is run when the robot is first started up and should be used for any
     * initialization code.
     */
    @Override
    public void robotInit()
    {
        limelight.setLights(false);

        // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
        // autonomous chooser on the dashboard.
        ROBOT_CONTAINER = new RobotContainer();

        ShuffleboardData.init();
        ROBOT_CONTAINER.addAutoRoutines();
    }
    
    
    /**
     * This method is called every robot packet, no matter the mode. Use this for items like
     * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
     *
     * <p>This runs after the mode specific periodic methods, but before LiveWindow and
     * SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic()
    {
        // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
        // commands, running already-scheduled commands, removing finished or interrupted commands,
        // and running subsystem periodic() methods.  This must be called from the robot's periodic
        // block in order for anything in the Command-based framework to work.
        CommandScheduler.getInstance().run();
    }
    
    
    /** This method is called once each time the robot enters Disabled mode. */
    @Override
    public void disabledInit() {
        LIGHTSHOW.setLight(Lightshow.Light.DISABLED);
        limelight.setLights(false);
    }
    
    
    @Override
    public void disabledPeriodic() {}
    
    
    /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
    @Override
    public void autonomousInit()
    {
        isAuto = true;

        LIGHTSHOW.setLight(Lightshow.Light.AUTONOMOUS);
        limelight.setLights(true);

        autonomousCommand = ROBOT_CONTAINER.getAutonomousCommand();
        
        // schedule the autonomous command (example)
        if (autonomousCommand != null)
        {
            autonomousCommand.schedule();
        }
    }
    
    
    /** This method is called periodically during autonomous. */
    @Override
    public void autonomousPeriodic() {}
    
    
    @Override
    public void teleopInit()
    {
        isAuto = false;

        LIGHTSHOW.setLight(Lightshow.Light.ENABLED);
        limelight.setLights(true);

        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null)
        {
            autonomousCommand.cancel();
        }
    }
    
    
    /** This method is called periodically during operator control. */
    @Override
    public void teleopPeriodic() {}
    
    
    @Override
    public void testInit()
    {
        // Cancels all running commands at the start of test mode.
        CommandScheduler.getInstance().cancelAll();
        limelight.setLights(true);
    }
    
    
    /** This method is called periodically during test mode. */
    @Override
    public void testPeriodic() {}
}
