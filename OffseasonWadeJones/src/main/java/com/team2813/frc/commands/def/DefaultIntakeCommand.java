package com.team2813.frc.commands.def;

import com.team2813.frc.subsystems.Drive;
import com.team2813.frc.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DefaultIntakeCommand extends CommandBase {

    private final Intake intakeSubsystem;
    private final Drive driveSubsystem;

    public DefaultIntakeCommand(Intake intakeSubsystem, Drive driveSubsystem) {
        this.intakeSubsystem = intakeSubsystem;
        this.driveSubsystem = driveSubsystem;

        addRequirements(intakeSubsystem, driveSubsystem);
    }

    @Override
    public void execute() {
        if (driveSubsystem.isRobotTipping()) {
            intakeSubsystem.deploy();
        }
    }
}
