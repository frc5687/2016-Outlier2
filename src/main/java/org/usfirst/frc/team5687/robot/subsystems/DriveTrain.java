package org.usfirst.frc.team5687.robot.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5687.robot.Constants;
import org.usfirst.frc.team5687.robot.Robot;
import org.usfirst.frc.team5687.robot.RobotMap;
import org.usfirst.frc.team5687.robot.commands.AutoAlign;
import org.usfirst.frc.team5687.robot.commands.DriveWith2Joysticks;

public class DriveTrain extends Subsystem {
    private RobotDrive drive;
    private VictorSP leftMotor;
    private VictorSP rightMotor;
    private Encoder rightEncoder;
    private Encoder leftEncoder;
    private Joystick stick;


    public DriveTrain() {
        leftMotor = new VictorSP(RobotMap.leftDriveMotors);
        rightMotor = new VictorSP(RobotMap.rightDriveMotors);
        drive = new RobotDrive(leftMotor, rightMotor);//TODO: Can I trust this object to take care of left vs. right turn, given setContinuous?
        rightEncoder = initializeEncoder(RobotMap.rightDriveEncoderChannelA, RobotMap.rightDriveEncoderChannelB, Constants.Encoders.RightDrive.REVERSED, Constants.Encoders.RightDrive.INCHES_PER_PULSE);
        leftEncoder = initializeEncoder(RobotMap.leftDriveEncoderChannelA, RobotMap.leftDriveEncoderChannelB, Constants.Encoders.LeftDrive.REVERSED, Constants.Encoders.LeftDrive.INCHES_PER_PULSE);

    }

    public void resetDriveEncoders() {
        rightEncoder.reset();
        leftEncoder.reset();
    }

    public double getLeftDistance() {
        return leftEncoder.getDistance();
    }

    public double getLeftTicks() {
        return leftEncoder.getRaw();
    }

    public double getLeftRate() {
        return leftEncoder.getRate();
    }

    public double getLeftSpeed() {
        return leftMotor.getSpeed();
    }

    public double getRightDistance() {
        return rightEncoder.getDistance();
    }

    public double getRightTicks() {
        return rightEncoder.getRaw();
    }

    public double getRightRate() {
        return rightEncoder.getRate();
    }

    public double getRightSpeed() {
        return rightMotor.getSpeed();
    }


    private Encoder initializeEncoder(int channelA, int channelB, boolean reversed, double distancePerPulse) {
        Encoder encoder = new Encoder(channelA, channelB, reversed, Encoder.EncodingType.k4X);
        encoder.setDistancePerPulse(distancePerPulse);
        encoder.setMaxPeriod(Constants.Encoders.Defaults.MAX_PERIOD);
        encoder.setSamplesToAverage(Constants.Encoders.Defaults.SAMPLES_TO_AVERAGE);
        encoder.reset();
        return encoder;
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new DriveWith2Joysticks());
    }

    public void sendAmpDraw() {
        SmartDashboard.putNumber("Current Draw/LeftMotor1", Robot.powerDistributionPanel.getCurrent(RobotMap.PDP_LEFT_MOTOR1)); //TODO: is this really where I'm getting current from? Check for all 4
        SmartDashboard.putNumber("Current Draw/LeftMotor2", Robot.powerDistributionPanel.getCurrent(RobotMap.PDP_LEFT_MOTOR2));
        SmartDashboard.putNumber("Current Draw/RightMotor1", Robot.powerDistributionPanel.getCurrent(RobotMap.PDP_RIGHT_MOTOR1));
        SmartDashboard.putNumber("Current Draw/RightMotor2", Robot.powerDistributionPanel.getCurrent(RobotMap.PDP_RIGHT_MOTOR2));
    }


    /**
     * Run drive motors at specified speeds
     *
     * @param leftSpeed  desired speed for left motors
     * @param rightSpeed desired speed for right motors
     */
    public void tankDrive(double leftSpeed, double rightSpeed) {
        // Limit change in leftSpeed to +/- ACCELERATION_CAP
        leftSpeed = Math.min(leftSpeed, leftMotor.get() + Constants.Limits.ACCELERATION_CAP);
        leftSpeed = Math.max(leftSpeed, leftMotor.get() - Constants.Limits.ACCELERATION_CAP);

        // Limit change in rightSpeed to +/- ACCELERATION_CAP
        rightSpeed = Math.min(rightSpeed, rightMotor.get() + Constants.Limits.ACCELERATION_CAP);
        rightSpeed = Math.max(rightSpeed, rightMotor.get() - Constants.Limits.ACCELERATION_CAP);

        drive.tankDrive(leftSpeed, rightSpeed, false);


        SmartDashboard.putNumber("Right distance", getRightDistance());
        SmartDashboard.putNumber("Left distance", getLeftDistance());

        SmartDashboard.putNumber("Right ticks", getRightTicks());
        SmartDashboard.putNumber("Left ticks", getLeftTicks());

        SmartDashboard.putNumber("Right rate", getRightRate());
        SmartDashboard.putNumber("Left rate", getLeftRate());

        SmartDashboard.putNumber("Right speed", getRightSpeed());
        SmartDashboard.putNumber("Left speed", getLeftSpeed());

    }
}
