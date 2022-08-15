package com.team2813.frc.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.team2813.frc.subsystems.Shooter;


public class DefaultShooterCommand extends CommandBase{

    private final Shooter ShooterSubsystem;

    public DefaultShooterCommand(Shooter ShooterSubsystem)
    {
        this.ShooterSubsystem = ShooterSubsystem;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(ShooterSubsystem);
    }

    @Override
    public void initialize() {
        ShooterSubsystem.setShooter(250);
    }
}