package com.team2813.frc.subsystems;

import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.team2813.lib.motors.ControlMode;
import com.team2813.lib.motors.TalonFXWrapper;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.team2813.lib.solenoid.SolenoidGroup;

import static com.team2813.frc.Constants.*;

public class Climber extends Subsystem1d<Climber.Position> {

    private final SolenoidGroup pistons = new SolenoidGroup(PCM_ID, PneumaticsModuleType.CTREPCM, 0, 1);

    public Climber() {
        super(new TalonFXWrapper(CLIMBER_ID, TalonFXInvertType.Clockwise));

        motor.configPID(0.4, 0, 0);
        motor.configMotionMagic(30000, 30000); // max vel in ticks/100ms

        setPosition(Position.RETRACTED);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Climber Encoder", motor.getEncoderPosition());
        SmartDashboard.putNumber("Climber Velocity", motor.getVelocity());
    }

    public double getMotorVelocity(){
        return motor.getVelocity();
    }

    public boolean positionReached() {
        return Math.abs(currentPosition.getPos() - motor.getEncoderPosition()) < 0.05;
    }

    public void extendPistons() {
        pistons.set(SolenoidGroup.PistonState.EXTENDED);
    }

    public void retractPistons() {
        pistons.set(SolenoidGroup.PistonState.RETRACTED);
    }

    public void startLoweringClimber() {
        motor.set(ControlMode.DUTY_CYCLE, -0.98);
    }

    public void brake() {
        motor.set(ControlMode.DUTY_CYCLE, 0);
    }

    public enum Position implements Subsystem1d.Position {
        RETRACTED(0),
        RISE_POS(100),
        NEXT_BAR(125),
        EXTENDED(145);

        final double encoderTicks;

        Position(double encoderTicks) {
            this.encoderTicks = encoderTicks;
        }

        @Override
        public double getPos() {
            return encoderTicks;
        }

        @Override
        public Position getMin() {
            return RETRACTED;
        }

        @Override
        public Position getMax() {
            return EXTENDED;
        }
    }
}
