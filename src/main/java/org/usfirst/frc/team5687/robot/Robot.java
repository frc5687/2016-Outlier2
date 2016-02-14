package org.usfirst.frc.team5687.robot;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.USBCamera;
import org.usfirst.frc.team5687.robot.commands.AutoChaseTarget;
import org.usfirst.frc.team5687.robot.commands.AutonomousDoNothing;
import org.usfirst.frc.team5687.robot.commands.AutonomousTestCVT;
import org.usfirst.frc.team5687.robot.subsystems.Arms;
import org.usfirst.frc.team5687.robot.subsystems.Intake;
import org.usfirst.frc.team5687.robot.subsystems.Shooter;
import org.usfirst.frc.team5687.robot.subsystems.DriveTrain;
import org.usfirst.frc.team5687.robot.utils.CustomCameraServer;
import org.usfirst.frc.team5687.robot.utils.Reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/*
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

    /**
     * Represents the navX inertial measurement unit, used for measuring robot movement and orientation.
     */
    public static AHRS imu;

    /**
     * Represents the robot's drive chassis
     */
    public static DriveTrain driveTrain;

    /**
     * Represents the operator interface/ controls
     */
    public static OI oi;

    public static Shooter shooter;

    /**
     * Represents the robot's boulder intake
     */
    public static Intake intake;

    /**
     * Represents the robot's arm
     */
    public static Arms arms;

    /**
     * Represents the power distribution panel
     */
    public static PowerDistributionPanel powerDistributionPanel;

    /**
     * Provides static access to the singleton Robot instance
     */
    public static Robot robot;

    Command autonomousCommand;
    SendableChooser autoChooser;

    CustomCameraServer cameraServer;

    USBCamera hornsCamera = new USBCamera(RobotMap.Cameras.hornsEnd);
    USBCamera intakeCamera = new USBCamera(RobotMap.Cameras.intakeEnd);

    String camera = RobotMap.Cameras.hornsEnd;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        oi = new OI();
        robot = this;
        driveTrain = new DriveTrain();
        shooter = new Shooter();
        intake = new Intake();
        arms = new Arms();
        autoChooser = new SendableChooser();
        powerDistributionPanel = new PowerDistributionPanel();
        //TODO: new object(); DriveTrain

        autoChooser.addDefault("Do Nothing At All", new AutonomousDoNothing());
        autoChooser.addObject("Calibrate CVT", new AutonomousTestCVT());
        autoChooser.addObject("Chase Target", new AutoChaseTarget());
        SmartDashboard.putData("Autonomous mode", autoChooser);

        // Report git info to the dashboard
        SmartDashboard.putString("Git Info", Reader.gitInfo);

        //Setup Camera Code
        cameraServer = CustomCameraServer.getInstance();
        cameraServer.setQuality(50);
        cameraServer.startAutomaticCapture(hornsCamera);

        try {
            // Try to connect to the navX imu.
            imu = new AHRS(SPI.Port.kMXP);

            // Report to both the logs and the dashboard.  We may not want to keep this permanently, but it's helpful for our initial testing.
            DriverStation.reportError(String.format("Connected to navX MXP with FirmwareVersion %1$s", imu.getFirmwareVersion()), false);
            SmartDashboard.putString("FirmwareVersion", imu.getFirmwareVersion());
        } catch (Exception ex) {
            // If there are any errors, null out the imu reference and report the error both to the logs and the dashboard.
            SmartDashboard.putString("FirmwareVersion", "navX not connected");
            DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
            imu = null;
        }
    }

	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
     * the robot is disabled.
     */
    public void disabledInit() {
    }

    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    /**
     * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
     * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
     * Dashboard, remove all of the chooser code and uncomment the getString code to get the auto name from the text box
     * below the Gyro
     * You can add additional auto modes by adding additional commands to the chooser code above (like the commented example)
     * or additional comparisons to the switch structure below with additional strings and commands.
     */
    public void autonomousInit() {
        // schedule the autonomous command (example)
        autonomousCommand = (Command)autoChooser.getSelected();
        if (autonomousCommand!=null) {
            autonomousCommand.start();
        }
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
    public void teleopPeriodic() {
        sendIMUData();
        driveTrain.sendAmpDraw();
        Scheduler.getInstance().run();
        intake.SendDashboardData();
    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }

    /**
     * This function will switch the camera currently streaming to the DriverStation
     */
    public void switchCameras() {
        //cameraServer.stopAutomaticCapture();
        if (camera.equals(RobotMap.Cameras.hornsEnd)) {
            camera = RobotMap.Cameras.intakeEnd;
            cameraServer.startAutomaticCapture(intakeCamera);
        } else {
            camera = RobotMap.Cameras.hornsEnd;
            cameraServer.startAutomaticCapture(hornsCamera);
        }
        DriverStation.reportError("Camera now streaming: "+camera,false);
    }

    protected void sendIMUData() {
        if (imu==null) {
            // If we can't find the imu, report that to the dashboard and return.
            SmartDashboard.putString("FirmwareVersion",      "navX not connected");
            return;
        }

        // Display 6-axis Processed Angle Data
        SmartDashboard.putBoolean(  "IMU_Connected",        imu.isConnected());
        SmartDashboard.putBoolean(  "IMU_IsCalibrating",    imu.isCalibrating());
        SmartDashboard.putNumber(   "IMU_Yaw",              imu.getYaw());
        SmartDashboard.putNumber(   "IMU_Pitch",            imu.getPitch());
        SmartDashboard.putNumber(   "IMU_Roll",             imu.getRoll());

        // Display tilt-corrected, Magnetometer-based heading (requires magnetometer calibration to be useful)
        SmartDashboard.putNumber(   "IMU_CompassHeading",   imu.getCompassHeading());

        // Display 9-axis Heading (requires magnetometer calibration to be useful)
        SmartDashboard.putNumber(   "IMU_FusedHeading",     imu.getFusedHeading());


        // These functions are compatible w/the WPI Gyro Class, providing a simple
        // path for upgrading from the Kit-of-Parts gyro to the navx MXP
        SmartDashboard.putNumber(   "IMU_TotalYaw",         imu.getAngle());
        SmartDashboard.putNumber(   "IMU_YawRateDPS",       imu.getRate());

        // Display Processed Acceleration Data (Linear Acceleration, Motion Detect)
        SmartDashboard.putNumber(   "IMU_Accel_X",          imu.getWorldLinearAccelX());
        SmartDashboard.putNumber(   "IMU_Accel_Y",          imu.getWorldLinearAccelY());
        SmartDashboard.putBoolean(  "IMU_IsMoving",         imu.isMoving());
        SmartDashboard.putBoolean(  "IMU_IsRotating",       imu.isRotating());

        // Display estimates of velocity/displacement.  Note that these values are not expected to be accurate enough
        // for estimating robot position on a FIRST FRC Robotics Field, due to accelerometer noise and the compounding
        // of these errors due to single (velocity) integration and especially double (displacement) integration.
        SmartDashboard.putNumber(   "Velocity_X",           imu.getVelocityX());
        SmartDashboard.putNumber(   "Velocity_Y",           imu.getVelocityY());
        SmartDashboard.putNumber(   "Displacement_X",       imu.getDisplacementX());
        SmartDashboard.putNumber(   "Displacement_Y",       imu.getDisplacementY());

        // Connectivity Debugging Support
        SmartDashboard.putNumber(   "IMU_Byte_Count",       imu.getByteCount());
        SmartDashboard.putNumber(   "IMU_Update_Count",     imu.getUpdateCount());
    }
}
