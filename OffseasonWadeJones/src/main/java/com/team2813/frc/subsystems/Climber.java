package com.team2813.frc.subsystems;

import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.team2813.lib.motors.ControlMode;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.team2813.lib.motors.TalonFXWrapper;
import com.team2813.frc.util.Limelight;
import com.team2813.frc.util.Units2813;
import com.team2813.lib.solenoid.SolenoidGroup;
import static com.team2813.frc.subsystems.Subsystem1d.*;

import static com.team2813.frc.Constants.*;

public class Climber extends SubsystemBase
{
    private final SolenoidGroup PISTONS;

    private Position currentPosition = Position.RETRACTED;
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

}
