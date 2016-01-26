package org.usfirst.frc.team5687.robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static int leftDriveMotor = 1;
    // public static int rightDriveMotor = 2;
    public static int leftDriveMotor = 0;
    public static int rightDriveMotor = 3;

    public static int leftDriveEncoderChannelA = 6;
    public static int leftDriveEncoderChannelB = 7;

    public static int rightDriveEncoderChannelA = 8;
    public static int rightDriveEncoderChannelB = 9;
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;
}
