package com.team2813.frc.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.team2813.lib.motors.ControlMode;
import com.team2813.lib.motors.TalonFXWrapper;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Magazine extends SubsystemBase {

    private final TalonFXWrapper magazineMotor = new TalonFXWrapper(9, TalonFXInvertType.Clockwise);
    private final TalonFXWrapper kickerMotor = new TalonFXWrapper(11, TalonFXInvertType.CounterClockwise);

    public Magazine () {
        magazineMotor.addFollower(10, TalonFXInvertType.OpposeMaster);
        magazineMotor.setNeutralMode(NeutralMode.Brake);
        kickerMotor.setNeutralMode(NeutralMode.Brake);
    }

    public void intake() {
        magazineMotor.set(ControlMode.DUTY_CYCLE, MagDemand.IN.percent);
        kickerMotor.set(ControlMode.DUTY_CYCLE, KickerDemand.OUT.percent);
    }

    public void outtake() {
        magazineMotor.set(ControlMode.DUTY_CYCLE, MagDemand.OUT.percent);
        kickerMotor.set(ControlMode.DUTY_CYCLE, KickerDemand.OUT.percent);
    }

    public void shoot() {
        magazineMotor.set(ControlMode.DUTY_CYCLE, MagDemand.SHOOT.percent);
        kickerMotor.set(ControlMode.DUTY_CYCLE, KickerDemand.IN.percent);
    }

    public void lowShoot() {
        magazineMotor.set(ControlMode.DUTY_CYCLE, MagDemand.LOW.percent);
        kickerMotor.set(ControlMode.DUTY_CYCLE, KickerDemand.IN.percent);
    }

    public void stop() {
        magazineMotor.set(ControlMode.DUTY_CYCLE, MagDemand.OFF.percent);
        kickerMotor.set(ControlMode.DUTY_CYCLE, KickerDemand.OFF.percent);
    }

    private enum MagDemand {
        IN(0.2), LOW(0.5), OFF(0), OUT(-0.2), SHOOT(0.125);

        double percent;

        MagDemand(double percent) {
            this.percent = percent;
        }
    }

    private enum KickerDemand {
        IN(0.3),  OFF(0), OUT(-0.4);

        double percent;

        KickerDemand(double percent) {
            this.percent = percent;
        }
    }
}
