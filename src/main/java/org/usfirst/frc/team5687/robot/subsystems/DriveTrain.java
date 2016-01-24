package org.usfirst.frc.team5687.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.RobotDrive;
import org.usfirst.frc.team5687.robot.Constants;
import org.usfirst.frc.team5687.robot.RobotMap;

/**
 * Created by Admin on 1/22/2016.
 */
public class DriveTrain extends Subsystem {
    private RobotDrive drive;
    VictorSP leftMotor;
    VictorSP rightMotor;
    public DriveTrain(){
        leftMotor = new VictorSP(RobotMap.leftMotor);
        rightMotor = new VictorSP(RobotMap.rightMotor);
        drive = new RobotDrive(leftMotor,rightMotor);

    };


    @Override
    protected void initDefaultCommand() {

    }
    public void tankdrive(double leftSpeed, double rightSpeed){

        // Limit change in leftSpeed to +/- ACCELERATION_CAP
        leftSpeed = Math.min(leftSpeed, leftMotor.get() + Constants.Limits.ACCELERATION_CAP);
        leftSpeed = Math.max(leftSpeed, leftMotor.get() - Constants.Limits.ACCELERATION_CAP);

        // Limit change in rightSpeed to +/- ACCELERATION_CAP
        rightSpeed = Math.min(rightSpeed, rightMotor.get() + Constants.Limits.ACCELERATION_CAP);
        rightSpeed = Math.max(rightSpeed, rightMotor.get() - Constants.Limits.ACCELERATION_CAP);

        drive.tankDrive(leftSpeed, rightSpeed, false);
    };
}
