package org.usfirst.frc.team5687.robot;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.USBCamera;
import org.usfirst.frc.team5687.robot.commands.*;
import org.usfirst.frc.team5687.robot.subsystems.*;
import org.usfirst.frc.team5687.robot.utils.CustomCameraServer;
import org.usfirst.frc.team5687.robot.utils.Reader;

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


    public static Shooter shooter;

    /**
     * Represents the robot's boulder intake
     */
    public static Intake intake;

    /**
     * Represents the robot's intake lifter
     */
    public static IntakeLifter intakeLifter;

    /**
     * Represents the robot's arm
     */
    public static Arms arms;

    /**
     * Represents the robot's lights
     */
    public static Lights lights;

    /**
     * Represents the operator interface/ controls
     */
    public static OI oi;

    /**
     * Represents the power distribution panel
     */
    public static PowerDistributionPanel powerDistributionPanel;

    /**
     * Provides static access to the singleton Robot instance
     */
    public static Robot robot;

    Command autonomousCommand;
    private SendableChooser autoChooser;

    public SendableChooser defenseChooser;
    public SendableChooser positionChooser;

    CustomCameraServer cameraServer;

    USBCamera hornsCamera = null;
    USBCamera intakeCamera = null;

    String camera = RobotMap.Cameras.hornsEnd;
    public static NetworkTable pitracker = null;
    public static NetworkTable pitrackerInputs = null;

    public Robot() {
        pitracker = NetworkTable.getTable("PITracker/tracking");
        pitrackerInputs = NetworkTable.getTable("PITracker/inputs");
    }
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        robot = this;
        driveTrain = new DriveTrain();
        shooter = new Shooter();
        intake = new Intake();
        intakeLifter = new IntakeLifter();
        arms = new Arms();
        lights = new Lights();
        autoChooser = new SendableChooser();
        defenseChooser = new SendableChooser();
        positionChooser = new SendableChooser();

        powerDistributionPanel = new PowerDistributionPanel();

        // Report commit info to dashboard and driver station
        SmartDashboard.putString("Git Info", Reader.gitInfo);
        DriverStation.reportError("Deployed commit: " + Reader.gitInfo , false);

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

        // Commands need to be instantiated AFTER the subsystems.  Since the OI constructor instantiates several commands, we need it to be instantiated last.
        oi = new OI();

        defenseChooser.addDefault("Low Bar", "LowBar");
        defenseChooser.addObject("Moat", "Moat");
        defenseChooser.addObject("Rock Wall","RockWall");
        defenseChooser.addObject("Rough Terrain","RoughTerrain");
        defenseChooser.addObject("Rampart","Rampart");
        SmartDashboard.putData("Defense to Cross", defenseChooser);
        initializeCameras();

        positionChooser.addDefault("1 (Low Bar)","1");
        positionChooser.addObject("2","2");
        positionChooser.addObject("3","3");
        positionChooser.addObject("4","4");
        positionChooser.addObject("5","5");
        SmartDashboard.putData("Start Position", positionChooser);

        autoChooser.addDefault("Do Nothing At All", new AutonomousDoNothing());
        autoChooser.addObject("Traverse Defense", new AutoTraverseBuilder());
        autoChooser.addObject("Traverse And Shoot", new AutoTraverseAndShootBuilder());
        autoChooser.addDefault("---Below are for Testing---", new AutonomousDoNothing());
        autoChooser.addObject("Turn, Target and Shoot", new AutoTurnAndShootBuilder());
        autoChooser.addObject("Target and Shoot", new AutoShootOnly());
        autoChooser.addObject("Chase Target", new AutoChaseTarget());
        autoChooser.addObject("Calibrate CVT", new AutonomousTestCVT());
        autoChooser.addObject("Left 90", new AutoAlign(-90));
        autoChooser.addObject("Right 90", new AutoAlign(90));
        autoChooser.addObject("Drive 12", new AutoDrive(0.4, 12f));
        autoChooser.addObject("Drive 24", new AutoDrive(0.4, 24f));
        autoChooser.addObject("Drive 48", new AutoDrive(0.4, 48f));
        autoChooser.addObject("Drive 96", new AutoDrive(0.4, 96f));
        autoChooser.addObject("Calibrate CVT", new AutonomousTestCVT());
        SmartDashboard.putData("Autonomous mode", autoChooser);


        //Setup Camera Code
        cameraServer = CustomCameraServer.getInstance();
        cameraServer.setQuality(50);
        cameraServer.startAutomaticCapture(hornsCamera);
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
        driveTrain.setSafeMode(false);
        autonomousCommand = (Command) autoChooser.getSelected();
        if (autonomousCommand!=null) {
            autonomousCommand.start();
        }
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        sendIMUData();
        driveTrain.sendAmpDraw();
        intake.updateDashboard();
        arms.updateDashboard();
        lights.updateDashboard();
    }

    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
        driveTrain.setSafeMode(true);
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        sendIMUData();
        driveTrain.sendAmpDraw();
        Scheduler.getInstance().run();
        intake.updateDashboard();
        intakeLifter.updateDashboard();
        arms.updateDashboard();
        lights.updateDashboard();
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
        if (oi.getDirection()==-1) {
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
        SmartDashboard.putBoolean(  "IMU/Connected",        imu.isConnected());
        SmartDashboard.putBoolean(  "IMU/IsCalibrating",    imu.isCalibrating());
        SmartDashboard.putNumber(   "IMU/Yaw",              imu.getYaw());
        SmartDashboard.putNumber(   "IMU/Pitch",            imu.getPitch());
        SmartDashboard.putNumber(   "IMU/Roll",             imu.getRoll());

        // Display tilt-corrected, Magnetometer-based heading (requires magnetometer calibration to be useful)
        SmartDashboard.putNumber(   "IMU/CompassHeading",   imu.getCompassHeading());

        // Display 9-axis Heading (requires magnetometer calibration to be useful)
        SmartDashboard.putNumber(   "IMU/FusedHeading",     imu.getFusedHeading());


        // These functions are compatible w/the WPI Gyro Class, providing a simple
        // path for upgrading from the Kit-of-Parts gyro to the navx MXP
        SmartDashboard.putNumber(   "IMU/TotalYaw",         imu.getAngle());
        SmartDashboard.putNumber(   "IMU/YawRateDPS",       imu.getRate());

        // Display Processed Acceleration Data (Linear Acceleration, Motion Detect)
        SmartDashboard.putNumber(   "IMU/Accel_X",          imu.getWorldLinearAccelX());
        SmartDashboard.putNumber(   "IMU/Accel_Y",          imu.getWorldLinearAccelY());
        SmartDashboard.putBoolean(  "IMU/IsMoving",         imu.isMoving());
        SmartDashboard.putBoolean(  "IMU/IsRotating",       imu.isRotating());

        // Display estimates of velocity/displacement.  Note that these values are not expected to be accurate enough
        // for estimating robot position on a FIRST FRC Robotics Field, due to accelerometer noise and the compounding
        // of these errors due to single (velocity) integration and especially double (displacement) integration.
        SmartDashboard.putNumber(   "IMU/Velocity_X",           imu.getVelocityX());
        SmartDashboard.putNumber(   "IMU/Velocity_Y",           imu.getVelocityY());
        SmartDashboard.putNumber(   "IMU/Displacement_X",       imu.getDisplacementX());
        SmartDashboard.putNumber(   "IMU/Displacement_Y",       imu.getDisplacementY());

        // Connectivity Debugging Support
        SmartDashboard.putNumber(   "IMU/Byte_Count",       imu.getByteCount());
        SmartDashboard.putNumber(   "IMU/Update_Count",     imu.getUpdateCount());
    }

    public String getSelectedDefense() {
        return (String) defenseChooser.getSelected();
    }

    public String getSelectedPosition() {
        return (String) positionChooser.getSelected();
    }



    public void initializeCameras() {
        if (hornsCamera!=null) {
            hornsCamera.closeCamera();
            hornsCamera = null;
        }
        if (intakeCamera!=null) {
            intakeCamera.closeCamera();
            intakeCamera = null;
        }

        try {
            hornsCamera = new USBCamera(RobotMap.Cameras.hornsEnd);
        } catch (Exception e) {
            hornsCamera = null;
        }

        try {
            intakeCamera = new USBCamera(RobotMap.Cameras.intakeEnd);
        } catch (Exception e) {
            intakeCamera = null;
        }

       if (cameraServer==null){
        //Setup Camera Code
            cameraServer = CustomCameraServer.getInstance();
           cameraServer.setQuality(50);
       }

        if (camera.equals(RobotMap.Cameras.hornsEnd)) {
            camera = RobotMap.Cameras.intakeEnd;
            cameraServer.startAutomaticCapture(intakeCamera);
        }else {
            camera = RobotMap.Cameras.hornsEnd;
            cameraServer.startAutomaticCapture(hornsCamera);
        }



    }
}
