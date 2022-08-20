package com.team2813.frc.commands.util;

import com.team2813.frc.subsystems.Climber;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class RetractCommand extends CommandBase {

    private final Climber climberSubsystem;

    public RetractCommand(Climber climberSubsystem) {
        this.climberSubsystem = climberSubsystem;
        addRequirements(climberSubsystem);
    }
}
