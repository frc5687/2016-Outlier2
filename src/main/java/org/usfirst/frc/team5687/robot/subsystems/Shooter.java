package org.usfirst.frc.team5687.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5687.robot.RobotMap;
import org.usfirst.frc.team5687.robot.commands.RunShooterManually;

public class Shooter extends Subsystem {

    /**
     * Victor speed controller for the shooter motor
     */
    private VictorSP wheelMotor;

    /**
     * Constructor
     */
    public Shooter() {
        wheelMotor = new VictorSP(RobotMap.shooterWheelMotor);
    }

    /**
     * Set the shooter wheel speed
     * @param speed the desired speed control value
     */
    public void setSpeed(double speed) {
        wheelMotor.set(speed);
        SmartDashboard.putNumber("ShooterWheel speed", speed);
    }

    /**
     * Set the default command for the shooter subsystem
     */
    public void initDefaultCommand() {
        setDefaultCommand(new RunShooterManually());
    }
}
