package com.team2813.frc.commands.def;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.team2813.frc.subsystems.Shooter;

import static com.team2813.frc.Constants.*;

public class DefaultShooterCommand extends CommandBase{

    private final Shooter shooterSubsystem;

    public DefaultShooterCommand(Shooter ShooterSubsystem)
    {
        this.shooterSubsystem = ShooterSubsystem;
        addRequirements(ShooterSubsystem);
    }

    @Override
    public void initialize() {
        shooterSubsystem.setFlywheelRPM(DEFAULT_SHOOT_DEMAND);
    }
}