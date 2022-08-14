package com.team2813.frc.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.util.Units;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.team2813.lib.motors.TalonFXWrapper;
import com.team2813.frc.util.Limelight;
import com.team2813.frc.util.Units2813;
import com.team2813.frc.Constants.ShooterConstants;
import com.team2813.lib.motors.interfaces.ControlMode;


import com.team2813.lib.controls.Controller;
import com.team2813.lib.controls.Axis;
import com.team2813.lib.controls.Button;

public class Shooter extends SubsystemBase 
{
    private final TalonFXWrapper FLYWHEEL;

    private Limelight limelight = Limelight.getInstance();

    private double demand = 0;
    //private final double spoolDemand = 0.435;

    private static final Button SHOOTER_BUTTON = ShooterConstants.getShooterButton();
    private static final Button MANUAL_SHOOT_BUTTON = ShooterConstants.getManualShootButton();
    private static final Button LOW_SHOOT_BUTTON = ShooterConstants.getLowShootButton();
    private static final Button SPOOL_BUTTON = ShooterConstants.getSpoolButton();

    private boolean isFullyRevvedUp;

    public Shooter() {
        FLYWHEEL = (TalonFXWrapper) MotorConfigs.talons.get("flywheel");
        FLYWHEEL.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 200);
    }

    public boolean isFlywheelReady() {
        return Math.abs(Units2813.motorRevsToWheelRevs(FLYWHEEL.getVelocity(), FLYWHEEL_UPDUCTION) - demand) < 175;
    }

    boolean isFullyRevvedUp() {
        return isFullyRevvedUp;
    }

    @Override
    public void outputTelemetry() {
        double flywheelVelocity = Units2813.motorRevsToWheelRevs(FLYWHEEL.getVelocity(), FLYWHEEL_UPDUCTION);
        double error = demand - flywheelVelocity;
        SmartDashboard.putNumber("Flywheel Demand", demand);
        SmartDashboard.putNumber("Flywheel Velocity", flywheelVelocity);
        SmartDashboard.putNumber("Flywheel Error", error);
        SmartDashboard.putNumber("Flywheel Encoder", FLYWHEEL.getEncoderPosition());
        SmartDashboard.putNumber("Distance to Target", limelight.calculateHorizontalDistance());
    }

    public void teleopControls() {
        SPOOL_BUTTON.whenPressed(() -> setShooter(ShooterConstants.spoolDemand));

        if (SHOOTER_BUTTON.get()) {
            if (DRIVE.getIsAimed()) {
                setShooter(limelight.getShooterDemand());
                if (isFlywheelReady()) {
                    MAGAZINE.setMagDemand(Magazine.MagDemand.SHOOT);
                    MAGAZINE.setKickerDemand(Magazine.KickerDemand.IN);
                }
                else {
                    MAGAZINE.setMagDemand(Magazine.MagDemand.OFF);
                    MAGAZINE.setKickerDemand(Magazine.KickerDemand.OFF);
                }
            }
        }

        SHOOTER_BUTTON.whenReleased(() -> {
            setShooter(0);
            MAGAZINE.setMagDemand(Magazine.MagDemand.OFF);
            MAGAZINE.setKickerDemand(Magazine.KickerDemand.OFF);
        });

        if (MANUAL_SHOOT_BUTTON.get()) {
            //setShooter(limelight.getShooterDemand());
            if (isFlywheelReady()) {
                MAGAZINE.setMagDemand(Magazine.MagDemand.SHOOT);
                MAGAZINE.setKickerDemand(Magazine.KickerDemand.IN);
            }
            else {
                MAGAZINE.setMagDemand(Magazine.MagDemand.OFF);
                MAGAZINE.setKickerDemand(Magazine.KickerDemand.OFF);
            }
        }

        MANUAL_SHOOT_BUTTON.whenReleased(() -> {
            setShooter(0);
            MAGAZINE.setMagDemand(Magazine.MagDemand.OFF);
            MAGAZINE.setKickerDemand(Magazine.KickerDemand.OFF);
        });

        if (LOW_SHOOT_BUTTON.get()) {
            setShooter(ShooterConstants.lowDemand);
            if (isFlywheelReady()) {
                MAGAZINE.setMagDemand(Magazine.MagDemand.SHOOT);
                MAGAZINE.setKickerDemand(Magazine.KickerDemand.LOW);
            }
            else {
                MAGAZINE.setMagDemand(Magazine.MagDemand.OFF);
                MAGAZINE.setKickerDemand(Magazine.KickerDemand.OFF);
            }
        }

        LOW_SHOOT_BUTTON.whenReleased(() -> {
            setShooter(0);
            MAGAZINE.setMagDemand(Magazine.MagDemand.OFF);
            MAGAZINE.setKickerDemand(Magazine.KickerDemand.OFF);
        });

        isFullyRevvedUp = FLYWHEEL.getVelocity() >= Units2813.wheelRevsToMotorRevs(demand, FLYWHEEL_UPDUCTION);
    }

    @Override
    protected void writePeriodicOutputs() {
        if (demand == 0) {
            double error = Math.abs(demand - Units2813.motorRevsToWheelRevs(FLYWHEEL.getVelocity(), FLYWHEEL_UPDUCTION));
            if (error <= 10) {
                FLYWHEEL.set(ControlMode.DUTY_CYCLE, 0);
            }
            else {
                FLYWHEEL.set(ControlMode.VELOCITY, 0);
            }
        }
        else {
            double motorDemand = Units2813.wheelRevsToMotorRevs(demand, FLYWHEEL_UPDUCTION);
            FLYWHEEL.set(ControlMode.VELOCITY, motorDemand);
        }
    }

    public void setShooter(double demand) {
        this.demand = demand;
    }

}
