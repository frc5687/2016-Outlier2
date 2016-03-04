package org.usfirst.frc.team5687.robot.commands;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.OI;
import org.usfirst.frc.team5687.robot.Robot;
import org.usfirst.frc.team5687.robot.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.Date;

/**
 * Autonomous command to run the drivetrain.
 * For now, runs at a preset speed for a preset time.
 * Eventually we will want to add distance-based options.
 */


public class AutoDrive extends Command implements PIDOutput{
    PIDController turnController = null;
    AHRS imu = Robot.imu;
    OI oi = Robot.oi;
    DriveTrain driveTrain = Robot.driveTrain;
    private float desiredAngle = 1/8; //TODO: Check that is is the correct angle
    private long end = 0;
    private long endTime = 0;
    private int timeToDrive = 0;
    private double inchesToDrive = 0;
    private double speed = 0;
    private double inchesDriven = 0;
    private double inchesAtStart = 0;
    private boolean driveByTime;
    private float currentAngle = imu.getPitch();
    public boolean isOnRamp = false;
    public boolean ramp;
    public double leftSpeed;
    public double rightSpeed;

    private static final double kP = 0.3;
    private static final double kI = 0.05;
    private static final double kD = 0.1;
    private static final double kF = 0.0;
    private static final double kToleranceDegrees = 0.0f;
    private double rotateToAngleRate = 0;
    private double targetAngle = 0;

    /**
     * Drive at a specified speed for a time specified in milliseconds.
     *
     * @param speed Speed to drive (range -1 to +1
     * @param millisToDrive Milliseconds to drive
     */
    public AutoDrive(double speed, int millisToDrive) {
        requires(driveTrain);
        this.speed = speed;
        this.timeToDrive = millisToDrive;
        this.driveByTime = true;

        DriverStation.reportError("Driving by Time", false);
    }

    /**
     * Drive at a specified speed for a distance specified in inches.
     *
     * @param speed Speed to drive (range 0 to +1
     * @param inchesToDrive Inches to drive (negative for reverse)
     */
    public AutoDrive(double speed, double inchesToDrive) {
        requires(driveTrain);//TODO: Why can't requires be applied to driveTrain?
        this.speed = speed;
        this.inchesToDrive = inchesToDrive;
        this.driveByTime = false;

        DriverStation.reportError("Driving by Distance", false);
    }

    public AutoDrive(double speed, boolean ramp) {
        this.leftSpeed = speed;
        this.rightSpeed = speed;
        this.ramp = ramp;
        isOnRamp = true;

    }

    @Override
    protected void initialize() {
        if (driveByTime) {
            DriverStation.reportError("Driving at " + speed + " for " + timeToDrive + " milliseconds.\n", false);
            endTime = (new Date()).getTime() + timeToDrive;
        } else {
            DriverStation.reportError("Driving at " + speed + " for " + inchesToDrive + " inchres.\n", false);
            driveTrain.resetDriveEncoders();
        }
        inchesAtStart = driveTrain.getRightDistance();
        targetAngle = imu.getYaw();
        turnController = new PIDController(kP, kI, kD, kF, imu, this);
        turnController.setInputRange(-180f, 180f);
        turnController.setOutputRange(-0.1, 0.1);
        turnController.setAbsoluteTolerance(kToleranceDegrees);
        turnController.setContinuous(true);
        turnController.setSetpoint(targetAngle);
        turnController.enable();
    }

    @Override
    protected void execute() {
        int directionFactor = driveByTime || (inchesToDrive>=0) ? 1 : -1;
        driveTrain.tankDrive(directionFactor * speed + rotateToAngleRate, directionFactor * speed - rotateToAngleRate);
    }

    @Override
    protected boolean isFinished() {

        if (driveByTime) {
            long now = (new Date()).getTime();
            return now > endTime;
        } else if (inchesToDrive<0){
            inchesDriven = driveTrain.getRightDistance() - inchesAtStart;
            return inchesDriven <= inchesToDrive;
        } else if (inchesToDrive>0) {
            inchesDriven = driveTrain.getRightDistance() - inchesAtStart;
            return  inchesDriven >= inchesToDrive;
        } else if (isOnRamp){
            return currentAngle == desiredAngle;
        }

        else {
            return true;
        }

    }


    @Override
    protected void end() {
        DriverStation.reportError("AutoDrive done.\n", false);
        driveTrain.tankDrive(0,0);

    }

    @Override
    protected void interrupted() {

    }

    @Override
    public void pidWrite(double output) {
        synchronized (this) {
            SmartDashboard.putNumber("AutoAlign/PID Output", output);
            rotateToAngleRate = output;
        }
    }
}

