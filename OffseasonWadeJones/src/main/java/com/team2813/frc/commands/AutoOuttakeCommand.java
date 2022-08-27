package com.team2813.frc.commands;

import com.team2813.frc.subsystems.Intake;
import com.team2813.frc.subsystems.Magazine;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class AutoOuttakeCommand extends SequentialCommandGroup {

    public AutoOuttakeCommand(Intake intakeSubsystem, Magazine magazineSubsystem) {
        super(
                new InstantCommand(intakeSubsystem::deploy, intakeSubsystem),
                new WaitCommand(0.4),
                new ParallelCommandGroup(
                        new InstantCommand(intakeSubsystem::outtake, intakeSubsystem),
                        new InstantCommand(magazineSubsystem::outtake, magazineSubsystem)
                )
        );
    }
}
