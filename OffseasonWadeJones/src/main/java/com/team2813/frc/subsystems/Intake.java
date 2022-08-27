package com.team2813.frc.subsystems;

import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.team2813.lib.motors.ControlMode;
import com.team2813.lib.motors.TalonFXWrapper;
import com.team2813.lib.solenoid.SolenoidGroup;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static com.team2813.frc.Constants.*;

public class Intake extends SubsystemBase {

    private final TalonFXWrapper intakeMotor = new TalonFXWrapper(INTAKE_ID, TalonFXInvertType.CounterClockwise);

    private final SolenoidGroup pistons = new SolenoidGroup(PCM_ID, PneumaticsModuleType.CTREPCM, 2, 3);

    private boolean deployed;

    public Intake() {

    }

    @Override
    public void periodic() {
        deployed = pistons.get().getAsBoolean();
        SmartDashboard.putBoolean("Intake Deployed", deployed);
    }

    private enum IntakeDemand {
        IN(0.85), OFF(0), OUT(-0.85);

        final double percent;

        IntakeDemand(double percent) {
            this.percent = percent;
        }
    }

    public void deploy() {
        pistons.set(SolenoidGroup.PistonState.EXTENDED);
    }

    public void retract() {
        pistons.set(SolenoidGroup.PistonState.RETRACTED);
    }

    public void intake() {
        intakeMotor.set(ControlMode.DUTY_CYCLE, IntakeDemand.IN.percent);
    }

    public void outtake() {
        intakeMotor.set(ControlMode.DUTY_CYCLE, IntakeDemand.OUT.percent);
    }

    public void stop() {
        intakeMotor.set(ControlMode.DUTY_CYCLE, IntakeDemand.OFF.percent);
    }
}
