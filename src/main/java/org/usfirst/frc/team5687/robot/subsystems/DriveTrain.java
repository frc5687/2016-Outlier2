package org.usfirst.frc.team5687.robot.subsystems;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.RobotDrive;
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
//double timeOfAccel = 1;  Enter ideal time (in seconds) it should
        /*if (leftSpeed-leftMotor.get() > timeOfAccel/50){
            leftSpeed = (leftMotor.get() + timeOfAccel/50);
        }
        if (leftSpeed - leftMotor.get()< -timeOfAccel/50);{
            leftSpeed = (leftMotor.get()-timeOfAccel/50);
        }
        if (rightSpeed - rightMotor.get() > timeOfAccel/50);{ //Tests right motor forward
            rightSpeed = rightMotor.get() + timeOfAccel/50;
        }
        if(rightSpeed - rightMotor.get()< -timeOfAccel) { // Tests right motor for going too far backwards
        rightSpeed = rightMotor.get() -timeOfAccel;
        } */
        drive.tankDrive(leftSpeed,rightSpeed);
    };
}
