package org.usfirst.frc.team5687.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5687.robot.Constants;
import org.usfirst.frc.team5687.robot.RobotMap;

public class Shooter extends Subsystem {

    /**
     * Victor speed controller for the shooter motor
     */
    private VictorSP wheelMotor;

    private double shooterSpeed = Constants.Shooter.SHOOTER_SPEED;

    /**
     * Constructor
     */
    public Shooter() {
        wheelMotor = new VictorSP(RobotMap.Shooter.WHEEL_MOTOR);
        wheelMotor.setInverted(true);
    }

    /**
     * Set the shooter wheel speed
     * @param speed the desired speed control value
     */
    public void setSpeed(double speed) {
        shooterSpeed = speed;
        SmartDashboard.putNumber("Shooter Wheel Set Speed", speed);
    }

    public void setRawSpeed(double speed) {
        wheelMotor.set(speed);
    }

    public double getSpeed() {
        return  shooterSpeed;
    }

    public void toggle(boolean on) {
        if (on) {
            wheelMotor.set(shooterSpeed);
        } else {
            wheelMotor.set(0);
        }

        SmartDashboard.putBoolean("Shooter Wheel Running", on);
    }


    /**
     * Set the default command for the shooter subsystem
     */
    public void initDefaultCommand() {
    }
}
