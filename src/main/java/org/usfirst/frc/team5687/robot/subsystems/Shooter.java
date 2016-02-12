package org.usfirst.frc.team5687.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5687.robot.Constants;
import org.usfirst.frc.team5687.robot.RobotMap;
import org.usfirst.frc.team5687.robot.commands.RunShooterManually;

public class Shooter extends Subsystem {

    /**
     * Victor speed controller for the shooter motor
     */
    private VictorSP wheelMotor;
    private Double SpinTime;

    public Shooter() {
        wheelMotor = new VictorSP(RobotMap.shooterWheelMotor);
    }

    /**
     * Set the shooter wheel speed
     * @param speed the desired speed control value
     */
    public void setSpeed(double speed) {
        wheelMotor.set(speed);
        if (speed != 0) {
            SpinTime = SmartDashboard.getNumber("Shooter Spin Up Time", 0);
            SpinTime += 0.02;
            SmartDashboard.putNumber("Shooter Spin Up Time", SpinTime);
        } else {
            SmartDashboard.putNumber("Shooter Spin Up Time", 0);
        }
    }

    public void initDefaultCommand() {
        setDefaultCommand(new RunShooterManually());
    }

}
