package com.team2813.frc.subsystems;

import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.team2813.lib.motors.TalonFXWrapper;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.team2813.lib.solenoid.SolenoidGroup;

public class Climber extends Subsystem1d<Climber.Position> {

    private final SolenoidGroup pistons = new SolenoidGroup(14, PneumaticsModuleType.CTREPCM, 0, 1);

    public Climber() {
        super(new TalonFXWrapper(12, TalonFXInvertType.Clockwise));

        motor.configPID(0.4, 0, 0);
        motor.configMotionMagic(30000, 30000); // max vel and max accel in ticks/100ms
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

    public void setPistons(SolenoidGroup.PistonState pistonState) {
        pistons.set(pistonState);
    }

    public enum Position implements Subsystem1d.Position {
        RETRACTED(0),
        RISE_POS(80),
        NEXT_BAR(105),
        EXTENDED(125);

        int encoderTicks;

        Position(int encoderTicks) {
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
