package org.usfirst.frc.team5687.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.RobotDrive;
import org.usfirst.frc.team5687.robot.Constants;
import org.usfirst.frc.team5687.robot.RobotMap;
import org.usfirst.frc.team5687.robot.commands.DriveWith2Joysticks;

/**
 * Created by Caleb on 1/22/2016.
 */
public class DriveTrain extends Subsystem {

    private RobotDrive drive;
    VictorSP leftMotor;
    VictorSP rightMotor;
    public Encoder rightEnc;
    boolean reverse_direction;

    public DriveTrain(){
        leftMotor = new VictorSP(RobotMap.leftMotor);
        rightMotor = new VictorSP(RobotMap.rightMotor);
        drive = new RobotDrive(leftMotor,rightMotor);
        reverse_direction = false;
        rightEnc = new Encoder(RobotMap.rightMotorEncoderChannelA, RobotMap.rightMotorEncoderChannelB, reverse_direction, Encoder.EncodingType.k4X);
        rightEnc.setMinRate(10);
        rightEnc.setDistancePerPulse(5);
        rightEnc.setReverseDirection(reverse_direction);
        rightEnc.setSamplesToAverage(7);
    }


    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new DriveWith2Joysticks());

    }


    public void tankDrive(double leftSpeed, double rightSpeed){

        // Limit change in leftSpeed to +/- ACCELERATION_CAP
        leftSpeed = Math.min(leftSpeed, leftMotor.get() + Constants.Limits.ACCELERATION_CAP);
        leftSpeed = Math.max(leftSpeed, leftMotor.get() - Constants.Limits.ACCELERATION_CAP);

        // Limit change in rightSpeed to +/- ACCELERATION_CAP
        rightSpeed = Math.min(rightSpeed, rightMotor.get() + Constants.Limits.ACCELERATION_CAP);
        rightSpeed = Math.max(rightSpeed, rightMotor.get() - Constants.Limits.ACCELERATION_CAP);

        drive.tankDrive(leftSpeed, rightSpeed, false);
    }
}
