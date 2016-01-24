
package org.usfirst.frc.team5687.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.usfirst.frc.team5687.robot.commands.ExampleCommand;
import org.usfirst.frc.team5687.robot.subsystems.DriveTrain;
import org.usfirst.frc.team5687.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.kauailabs.navx.frc.AHRS;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	// public static OI oi;
    /***
     * Represents the navX inertial measurement unit, used for measuring robot movement and orientation.
     */
    public static AHRS imu;

    Command autonomousCommand;
    SendableChooser chooser;
    public static DriveTrain driveTrain;
    public static OI oi;
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
		// oi = new OI();
        oi = new OI();
        driveTrain = new DriveTrain();
        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", new ExampleCommand());
//        chooser.addObject("My Auto", new MyAutoCommand());
        SmartDashboard.putData("Auto mode", chooser);

        try {
            // Try to connect to the navX imu.
            imu = new AHRS(SPI.Port.kMXP);

            // Report to both the logs and the dashboard.  We may not want to keep this permanently, but it's helpful for our initial testing.
            DriverStation.reportError(String.format("Connected to navX MXP with FirmwareVersion %1$s", imu.getFirmwareVersion()), false);
            SmartDashboard.putString(   "FirmwareVersion",      imu.getFirmwareVersion());
        } catch (Exception ex) {
            // If there are any errors, null out the imu reference and report the error both to the logs and the dashboard.
            SmartDashboard.putString(   "FirmwareVersion",      "navX not connected");
            DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
            imu = null;
        }
    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    public void disabledInit(){

    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString code to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the chooser code above (like the commented example)
	 * or additional comparisons to the switch structure below with additional strings and commands.
	 */
    public void autonomousInit() {
        autonomousCommand = (Command) chooser.getSelected();
        
		/* String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		switch(autoSelected) {
		case "My Auto":
			autonomousCommand = new MyAutoCommand();
			break;
		case "Default Auto":
		default:
			autonomousCommand = new ExampleCommand();
			break;
		} */
    	
    	// schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic()
    {
        sendIMUData();
        Scheduler.getInstance().run();
    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }

    protected void sendIMUData() {

        if (imu==null) {
            // If we can't find the imu, report that to the dashboard and return.
            SmartDashboard.putString(   "FirmwareVersion",      "navX not connected");
            return;
        }

           /* Display 6-axis Processed Angle Data                                      */
        SmartDashboard.putBoolean(  "IMU_Connected",        imu.isConnected());
        SmartDashboard.putBoolean(  "IMU_IsCalibrating",    imu.isCalibrating());
        SmartDashboard.putNumber(   "IMU_Yaw",              imu.getYaw());
        SmartDashboard.putNumber(   "IMU_Pitch",            imu.getPitch());
        SmartDashboard.putNumber(   "IMU_Roll",             imu.getRoll());

           /* Display tilt-corrected, Magnetometer-based heading (requires             */
           /* magnetometer calibration to be useful)                                   */

        SmartDashboard.putNumber(   "IMU_CompassHeading",   imu.getCompassHeading());

           /* Display 9-axis Heading (requires magnetometer calibration to be useful)  */
        SmartDashboard.putNumber(   "IMU_FusedHeading",     imu.getFusedHeading());

           /* These functions are compatible w/the WPI Gyro Class, providing a simple  */
           /* path for upgrading from the Kit-of-Parts gyro to the navx MXP            */

        SmartDashboard.putNumber(   "IMU_TotalYaw",         imu.getAngle());
        SmartDashboard.putNumber(   "IMU_YawRateDPS",       imu.getRate());

           /* Display Processed Acceleration Data (Linear Acceleration, Motion Detect) */

        SmartDashboard.putNumber(   "IMU_Accel_X",          imu.getWorldLinearAccelX());
        SmartDashboard.putNumber(   "IMU_Accel_Y",          imu.getWorldLinearAccelY());
        SmartDashboard.putBoolean(  "IMU_IsMoving",         imu.isMoving());
        SmartDashboard.putBoolean(  "IMU_IsRotating",       imu.isRotating());

           /* Display estimates of velocity/displacement.  Note that these values are  */
           /* not expected to be accurate enough for estimating robot position on a    */
           /* FIRST FRC Robotics Field, due to accelerometer noise and the compounding */
           /* of these errors due to single (velocity) integration and especially      */
           /* double (displacement) integration.                                       */

        SmartDashboard.putNumber(   "Velocity_X",           imu.getVelocityX());
        SmartDashboard.putNumber(   "Velocity_Y",           imu.getVelocityY());
        SmartDashboard.putNumber(   "Displacement_X",       imu.getDisplacementX());
        SmartDashboard.putNumber(   "Displacement_Y",       imu.getDisplacementY());




           /* Connectivity Debugging Support                                           */
        SmartDashboard.putNumber(   "IMU_Byte_Count",       imu.getByteCount());
        SmartDashboard.putNumber(   "IMU_Update_Count",     imu.getUpdateCount());

    }
}
