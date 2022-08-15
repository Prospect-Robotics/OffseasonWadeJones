package com.team2813.frc.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.team2813.frc.subsystems.Shooter;

import static com.team2813.frc.Constants.*;

public class DefaultShooterCommand extends CommandBase{

    private final Shooter ShooterSubsystem;

    public DefaultShooterCommand(Shooter ShooterSubsystem)
    {
        this.ShooterSubsystem = ShooterSubsystem;
        addRequirements(ShooterSubsystem);
    }

    @Override
    public void initialize() {
        ShooterSubsystem.setFlywheelRPM(DEFAULT_SHOOT_DEMAND);
    }
}