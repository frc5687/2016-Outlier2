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
        wheelMotor.set(speed);

        if (speed > 0) {
            SmartDashboard.putNumber("Shooter Wheel Spin Time", SmartDashboard.getNumber("Shooter Wheel Spin Time", 0) + 20);
        } else {
            SmartDashboard.putNumber("Shooter Wheel Spin Time", 0);
            SmartDashboard.putBoolean("Shooter Wheel At Speed", false);
        }
        if (SmartDashboard.getNumber("Shooter Wheel Spin Time", 0) > Constants.Shooter.PRIME_TIME) {
            SmartDashboard.putBoolean("Shooter Wheel At Speed", true);
        }
    }

    /**
     * Stops the shooter motor
     */
    public void stop() {
        wheelMotor.set(0);

        SmartDashboard.putNumber("Shooter Wheel Spin Time", 0);
        SmartDashboard.putBoolean("Shooter Wheel At Speed", false);
    }

    /**
     * Set the default command for the shooter subsystem
     */
    public void initDefaultCommand() {
    }
}
