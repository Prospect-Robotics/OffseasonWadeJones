package com.team2813.frc.commands;

import com.team2813.frc.commands.util.LockFunctionCommand;
import com.team2813.frc.commands.util.RetractCommand;
import com.team2813.frc.subsystems.Climber;
import com.team2813.lib.solenoid.SolenoidGroup;
import edu.wpi.first.wpilibj2.command.*;

public class RiseUpCommand extends SequentialCommandGroup {

    public RiseUpCommand(Climber climberSubsystem)
    {
        super(
                new ParallelCommandGroup(
                        new LockFunctionCommand(climberSubsystem::positionReached, () -> climberSubsystem.setPosition(Climber.Position.RISE_POS), climberSubsystem),
                        new InstantCommand(() -> climberSubsystem.setPistons(SolenoidGroup.PistonState.EXTENDED))
                ),
                new LockFunctionCommand(climberSubsystem::positionReached, () -> climberSubsystem.setPosition(Climber.Position.NEXT_BAR), climberSubsystem),
                new InstantCommand(()-> climberSubsystem.setPistons(SolenoidGroup.PistonState.RETRACTED)),
                new WaitCommand(0.75),
                new RetractCommand(climberSubsystem)
        );
    }
}
