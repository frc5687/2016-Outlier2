package org.usfirst.frc.team5687.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5687.robot.Constants;
import org.usfirst.frc.team5687.robot.Robot;
import org.usfirst.frc.team5687.robot.RobotMap;
import org.usfirst.frc.team5687.robot.commands.DriveWith2Joysticks;

public class DriveTrain extends Subsystem {

    private RobotDrive drive;
    private VictorSP leftMotor;
    private VictorSP rightMotor;
    private Encoder rightEncoder;
    private Encoder leftEncoder;

    public DriveTrain(){
        leftMotor = new VictorSP(RobotMap.Drive.LEFT_MOTORS);
        rightMotor = new VictorSP(RobotMap.Drive.RIGHT_MOTORS);
        drive = new RobotDrive(leftMotor, rightMotor);
        rightEncoder = initializeEncoder(RobotMap.Drive.RIGHT_ENCODER_CHANNEL_A, RobotMap.Drive.RIGHT_ENCODER_CHANNEL_B, Constants.Encoders.RightDrive.REVERSED, Constants.Encoders.RightDrive.INCHES_PER_PULSE);
        leftEncoder = initializeEncoder(RobotMap.Drive.LEFT_ENCODER_CHANNEL_A, RobotMap.Drive.LEFT_ENCODER_CHANNEL_B, Constants.Encoders.LeftDrive.REVERSED, Constants.Encoders.LeftDrive.INCHES_PER_PULSE);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new DriveWith2Joysticks());
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

    public double getLeftRPS() {
        return getLeftRate() / (Constants.Encoders.Defaults.PULSES_PER_ROTATION * Constants.Encoders.Defaults.INCHES_PER_PULSE);
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

    public double getRightRPS() {
        return getRightRate() / (Constants.Encoders.Defaults.PULSES_PER_ROTATION * Constants.Encoders.Defaults.INCHES_PER_PULSE);
    }

    private Encoder initializeEncoder(int channelA, int channelB, boolean reversed, double distancePerPulse) {
        Encoder encoder = new Encoder(channelA, channelB, reversed, Encoder.EncodingType.k4X);
        encoder.setDistancePerPulse(distancePerPulse);
        encoder.setMaxPeriod(Constants.Encoders.Defaults.MAX_PERIOD);
        encoder.setSamplesToAverage(Constants.Encoders.Defaults.SAMPLES_TO_AVERAGE);
        encoder.reset();
        return encoder;
    }

    /**
     * @return average of leftDistance and rightDistance
     */
    public double getDistance() { return (Robot.driveTrain.getLeftDistance()+Robot.driveTrain.getRightDistance()/2);}
    public void sendAmpDraw() {
        SmartDashboard.putNumber("Current Draw/LeftMotor1", Robot.powerDistributionPanel.getCurrent(RobotMap.Drive.PDP_LEFT_MOTOR1)); //TODO: is this really where I'm getting current from? Check for all 4
        SmartDashboard.putNumber("Current Draw/LeftMotor2", Robot.powerDistributionPanel.getCurrent(RobotMap.Drive.PDP_LEFT_MOTOR2));
        SmartDashboard.putNumber("Current Draw/RightMotor1", Robot.powerDistributionPanel.getCurrent(RobotMap.Drive.PDP_RIGHT_MOTOR1));
        SmartDashboard.putNumber("Current Draw/RightMotor2", Robot.powerDistributionPanel.getCurrent(RobotMap.Drive.PDP_RIGHT_MOTOR2));
    }

    /**
     * Run drive motors at specified speeds
     * @param leftSpeed desired speed for left motors
     * @param rightSpeed desired speed for right motors
     */
    public void tankDrive(double leftSpeed, double rightSpeed){
        // Limit change in leftSpeed to +/- ACCELERATION_CAP
        leftSpeed = Math.min(leftSpeed, leftMotor.get() + Constants.Limits.ACCELERATION_CAP);
        leftSpeed = Math.max(leftSpeed, leftMotor.get() - Constants.Limits.ACCELERATION_CAP);

        // Limit change in rightSpeed to +/- ACCELERATION_CAP
        rightSpeed = Math.min(rightSpeed, rightMotor.get() + Constants.Limits.ACCELERATION_CAP);
        rightSpeed = Math.max(rightSpeed, rightMotor.get() - Constants.Limits.ACCELERATION_CAP);

        drive.tankDrive(leftSpeed, rightSpeed, false);


        SmartDashboard.putNumber("Right distance" , getRightDistance());
        SmartDashboard.putNumber("Left distance" , getLeftDistance());

        SmartDashboard.putNumber("Right ticks" , getRightTicks());
        SmartDashboard.putNumber("Left ticks" , getLeftTicks());

        SmartDashboard.putNumber("Right rate" , getRightRate());
        SmartDashboard.putNumber("Left rate" , getLeftRate());

        SmartDashboard.putNumber("Right speed" , getRightSpeed());
        SmartDashboard.putNumber("Left speed" , getLeftSpeed());

        SmartDashboard.putNumber("Right RPS" , getRightRPS());
        SmartDashboard.putNumber("Left RPS" , getLeftRPS());
    }
}
