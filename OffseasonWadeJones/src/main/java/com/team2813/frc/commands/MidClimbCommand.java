package com.team2813.frc.commands;
import com.team2813.frc.subsystems.Climber;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class MidClimbCommand extends CommandBase {
    private final Climber ClimberSubsystem;

    public MidClimbCommand(Climber ClimberSubsystem)
    {
        this.ClimberSubsystem = ClimberSubsystem;
        addRequirements(ClimberSubsystem);
    }

    @Override
    public void initialize() {
        // Set intake thing here whoever is creating it
        ClimberSubsystem.retract();
    }
}
