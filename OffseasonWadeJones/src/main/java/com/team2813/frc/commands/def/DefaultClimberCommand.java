package com.team2813.frc.commands.def;

import com.team2813.frc.subsystems.Climber;
import com.team2813.lib.motors.ControlMode;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DefaultClimberCommand extends CommandBase {

    private final Climber climberSubsytem;

    public DefaultClimberCommand(Climber climberSubsytem) {
        this.climberSubsytem = climberSubsytem;
        addRequirements(climberSubsytem);
    }

    @Override
    public void initialize() {
        climberSubsytem.setMotorSpeed(ControlMode.DUTY_CYCLE, 0);
    }
}
