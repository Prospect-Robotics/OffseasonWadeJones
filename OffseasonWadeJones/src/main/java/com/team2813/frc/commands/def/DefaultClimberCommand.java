package com.team2813.frc.commands.def;

import com.team2813.frc.subsystems.Climber;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DefaultClimberCommand extends CommandBase {

    private final Climber climberSubsystem;

    public DefaultClimberCommand(Climber climberSubsystem) {
       this.climberSubsystem = climberSubsystem;
       addRequirements(climberSubsystem);
    }

    @Override
    public void execute() {
        climberSubsystem.brake();
    }
}
