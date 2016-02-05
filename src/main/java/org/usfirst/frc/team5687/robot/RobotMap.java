package org.usfirst.frc.team5687.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    
    // Drive train ports

    public static int leftMotors = 0;
    public static int rightMotors = 3;
    public static int pdpAddress = 5;
    public static int Leftmotor1 = 1; //TODO: Where on the robot is leftmotor1?
    public static int Leftmotor2 = 2;
    public static int Rightmotor1 = 4;
    public static int Rightmotor2 = 5;

    public static int leftDriveMotors = 0;
    public static int rightDriveMotors = 3;

    public static int leftDriveEncoderChannelA = 8;
    public static int leftDriveEncoderChannelB = 9;

    public static int rightDriveEncoderChannelA = 6;
    public static int rightDriveEncoderChannelB = 7;

}
