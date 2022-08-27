package com.team2813.frc.commands;

import com.team2813.frc.subsystems.Intake;
import com.team2813.frc.subsystems.Magazine;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AutoStopIntakeCommand extends SequentialCommandGroup {

    public AutoStopIntakeCommand(Intake intakeSubsystem, Magazine magazineSubsystem) {
        super(
                new ParallelCommandGroup(
                        new InstantCommand(intakeSubsystem::stop, intakeSubsystem),
                        new InstantCommand(magazineSubsystem::stop, magazineSubsystem)
                ),
                new InstantCommand(intakeSubsystem::retract, intakeSubsystem)
        );
    }
}
