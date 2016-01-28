package org.usfirst.frc.team5687.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
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
    private Encoder rightEncoder;
    private Encoder leftEncoder;

    public DriveTrain(){
        leftMotor = new VictorSP(RobotMap.leftDriveMotor);
        rightMotor = new VictorSP(RobotMap.rightDriveMotor);
        drive = new RobotDrive(leftMotor,rightMotor);
        rightEncoder = initializeEncoder(RobotMap.rightDriveEncoderChannelA, RobotMap.rightDriveEncoderChannelB, Constants.Encoders.RightDrive.REVERSED, Constants.Encoders.RightDrive.INCHES_PER_PULSE, Constants.Encoders.RightDrive.MAX_PERIOD);
        leftEncoder = initializeEncoder(RobotMap.leftDriveEncoderChannelA, RobotMap.leftDriveEncoderChannelB, Constants.Encoders.LeftDrive.REVERSED, Constants.Encoders.LeftDrive.INCHES_PER_PULSE, Constants.Encoders.LeftDrive.MAX_PERIOD);
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

    public double getRightDistance() {
        return rightEncoder.getDistance();
    }

    public double getRightTicks() {
        return rightEncoder.getRaw();
    }

    private Encoder initializeEncoder(int channelA, int channelB, boolean reversed, double distancePerPulse, double maxPeriod) {
        Encoder encoder = new Encoder(channelA, channelB, reversed, Encoder.EncodingType.k4X);
        encoder.setDistancePerPulse(distancePerPulse);
        DriverStation.reportError(String.format("Encoder initialized with channel A=%1$i, channel B==%2$i, reversed==%3$b, distancePerPulse=%4$f, and maxPeriod=%5$f", channelA, channelB, reversed, distancePerPulse, maxPeriod), false);
        encoder.reset();
        return encoder;
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
