package com.team2813.frc.subsystems;

import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.team2813.lib.motors.ControlMode;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.team2813.lib.solenoid.SolenoidGroup;
import static com.team2813.frc.subsystems.Subsystem1d.*;

import static com.team2813.frc.Constants.*;

public class Climber extends SubsystemBase
{
    private final SolenoidGroup PISTONS;

    private Position currentPosition;
    public Climber() {
        PISTONS = new SolenoidGroup(14, PneumaticsModuleType.CTREPCM, 0, 1);
    }




    @Override
    public void periodic() {
        SmartDashboard.putNumber("Climber Encoder", getMotor().getEncoderPosition());
        SmartDashboard.putNumber("Climber Velocity", getMotor().getVelocity());
    }

    @Override
    public void onEnabledStop(double timestamp) {
        PISTONS.set(SolenoidGroup.PistonState.RETRACTED);
    }

    public boolean positionReached() {
        return Math.abs(currentPosition.getPos() - getMotor().getEncoderPosition()) < 0.05;
    }

    @Override
    public void setNextPosition(boolean clockwise) {
        currentPosition = (Position) currentPosition.getClock(clockwise);
        Subsystem1d.setPosition(currentPosition);
        Subsystem1d.enableMotionMagic(true);
    }

    @Override
    public void setNextPosition(Position position) {
        currentPosition = position;
        Subsystem1d.setPosition(currentPosition);
        Subsystem1d.enableMotionMagic(true);
    }

    public void retract() {
        Subsystem1d.enableMotionMagic(false);
        Subsystem1d.getMotor().set(ControlMode.DUTY_CYCLE, -0.98);
        double timeStart = Timer.getFPGATimestamp();
        double dt = Timer.getFPGATimestamp() - timeStart;
        while ((dt < 0.25) || (Math.abs(getMotor().getVelocity()) > 0.5)) {
            dt = Timer.getFPGATimestamp() - timeStart;
            // wait...
        }
        Subsystem1d.getMotor().set(ControlMode.DUTY_CYCLE, 0);
        Subsystem1d.zeroSensors();
    }

}
