package com.team2813.frc.commands;
import com.team2813.frc.subsystems.Climber;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class RiseUpCommand extends CommandBase {
    private final Climber ClimberSubsystem;

    public RiseUpCommand(Climber ClimberSubsystem)
    {
        this.ClimberSubsystem = ClimberSubsystem;
        addRequirements(ClimberSubsystem);
    }

    @Override
    public void initialize() {
        ClimberSubsystem.setNextPosition(Position.RISE_POS), this::positionReached, true),
        new FunctionalCommand(PISTONS::toggle, true)
        ClimberSubsystem.setNextPosition(Position.NEXT_BAR), this::positionReached, true),
        new FunctionalCommand(PISTONS::toggle, true)
        new WaitCommand(0.75);
        new FunctionalCommand(this::retract, true);
    }
}
