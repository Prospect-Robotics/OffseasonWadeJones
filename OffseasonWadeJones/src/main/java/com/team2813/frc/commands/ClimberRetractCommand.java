package com.team2813.frc.commands;

import com.team2813.frc.subsystems.Climber;
import com.team2813.lib.motors.ControlMode;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ClimberRetractCommand extends CommandBase {

    private final Climber climberSubsystem;

    private double timeStart;

    public ClimberRetractCommand(Climber climberSubsystem) {
        this.climberSubsystem = climberSubsystem;
        addRequirements(climberSubsystem);
    }

    @Override
    public void initialize() {
        climberSubsystem.setMotorSpeed(ControlMode.DUTY_CYCLE, -0.98);
        timeStart = Timer.getFPGATimestamp();
    }

    @Override
    public boolean isFinished() {
        return ((Timer.getFPGATimestamp() - timeStart) > 0.25) && (climberSubsystem.getMotorVelocity() < 0.5);
    }

    @Override
    public void end(boolean interrupted) {
        climberSubsystem.setMotorSpeed(ControlMode.DUTY_CYCLE, 0);
        climberSubsystem.zeroSensors();
    }
}
