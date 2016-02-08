package org.usfirst.frc.team5687.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team5687.robot.RobotMap;
import org.usfirst.frc.team5687.robot.commands.RunShooterManually;

public class Boulder extends Subsystem {

    /**
     * Victor speed controller for the shooter motor
     */
    private VictorSP shooterWheel;

    /**
     * Constructor
     */
    public  Boulder() {
        shooterWheel = new VictorSP(RobotMap.shooterWheelMotor);
    }

    /**
     * Set the shooter wheel speed
     * @param speed the desired speed control value
     */
    public void setShooterSpeed(double speed) {
        shooterWheel.set(speed);
    }

    /**
     * Set the default command for the shooter subsystem
     */
    public void initDefaultCommand() {
        setDefaultCommand(new RunShooterManually());
    }
}
