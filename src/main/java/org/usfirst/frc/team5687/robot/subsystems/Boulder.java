package org.usfirst.frc.team5687.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team5687.robot.RobotMap;

public class Boulder extends Subsystem {
    
    VictorSP shooterWheel;
    Encoder shooterRotation;


    public void  Boulder() {
        shooterWheel = new VictorSP(RobotMap.shooterWheelMotor);
    }

    public void setShooterSpeed(double speed) {
        shooterWheel.set(speed);
    }

    public void initDefaultCommand() {
        setDefaultCommand(new RunShooterManually());
    }
}
