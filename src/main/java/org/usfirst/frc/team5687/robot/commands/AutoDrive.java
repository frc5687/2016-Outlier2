package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import static org.usfirst.frc.team5687.robot.Robot.driveTrain;
import static org.usfirst.frc.team5687.robot.Robot.imu;

import java.util.Date;

/**
 * Autonomous command to run the drivetrain.
 * For now, runs at a preset speed for a preset time.
 * Eventually we will want to add distance-based options.
 */

public class AutoDrive extends Command implements PIDOutput{
    PIDController turnController = null;

    private long endTime = 0;
    private int timeToDrive = 0;
    private double inchesToDrive = 0;
    private double speed = 0;
    private double inchesDriven = 0;
    private double inchesAtStart = 0;
    private boolean driveByTime;

    private static final double kP = 0.3;
    private static final double kI = 0.05;
    private static final double kD = 0.1;
    private static final double kF = 0.0;
    private static final double kToleranceDegrees = 0.0f;
    private double rotateToAngleRate = 0;
    private double targetAngle = 0;

    /**
     * Drive at a specified speed for a time specified in milliseconds.
     */
    public AutoDrive(double speed, int millisToDrive) {
        requires(driveTrain);
        this.speed = speed;
        this.timeToDrive = millisToDrive;
        this.driveByTime = true;
    }

    /**
     * Drive at a specified speed for a distance specified in inches.
     */
    public AutoDrive(double speed, double inchesToDrive) {
        requires(driveTrain);
        this.speed = speed;
        this.inchesToDrive = inchesToDrive;
        this.driveByTime = false;
    }

    @Override
    protected void initialize() {
        if (driveByTime) {
            DriverStation.reportError(String.format("Driving at %1$f for %2$i milliseconds.\n", speed, timeToDrive), false);
            endTime = (new Date()).getTime() + timeToDrive;
        } else {
            DriverStation.reportError(String.format("Driving at %1$f for %2$f inches.\n", speed, timeToDrive), false);
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
            // Start at 100...
            // Get -10...
            // Move to 95
            // Inches driven should be -5
            inchesDriven = driveTrain.getRightDistance() - inchesAtStart;
            return inchesDriven <= inchesToDrive;
        } else if (inchesToDrive>0) {
            inchesDriven = driveTrain.getRightDistance() - inchesAtStart;
            return  inchesDriven >= inchesToDrive;
        } else {
            return true;
        }
    }

    @Override
    protected void end() {
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
