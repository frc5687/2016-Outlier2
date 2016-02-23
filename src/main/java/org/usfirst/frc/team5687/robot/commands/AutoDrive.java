package org.usfirst.frc.team5687.robot.commands;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.Constants;
import org.usfirst.frc.team5687.robot.OI;
import org.usfirst.frc.team5687.robot.Robot;
import org.usfirst.frc.team5687.robot.subsystems.DriveTrain;

import java.util.Date;

/**
 * Autonomous command to run the drivetrain.
 * For now, runs at a preset speed for a preset time.
 * Eventually we will want to add distance-based options.
 */

public class AutoDrive extends Command {
    DriveTrain driveTrain = Robot.driveTrain;
    AHRS imu = Robot.imu;
    OI oi = Robot.oi;
    DriveTrain stopMoving;
    AutoDrive driveForward;
    AutoDrive autoArmsLow;
    private long end = 0;
    private int timeToDrive = 0;
    private double inchesToDrive = 0;
    private double rightSpeed = 0;
    private double leftSpeed = 0;
    private double inchesDriven = 0;
    private boolean driveByTime;
    private float currentAngle = imu.getPitch();
    public boolean onRamp = false;
    public double centerChevalDistance = x; //TODO: add in x.

    //Drive based on time
    public AutoDrive(double speed, int timeToDrive) {
        requires(driveTrain);
        this.leftSpeed = speed;
        this.rightSpeed = speed;
        this.timeToDrive = timeToDrive;
        this.driveByTime = true;

        DriverStation.reportError("Driving by Time",false);
    }

    //Drive based on distance
    public AutoDrive(double speed, double inchesToDrive) {
        requires(driveTrain);
        this.leftSpeed = speed;
        this.rightSpeed = speed;
        this.inchesToDrive = inchesToDrive;
        this.driveByTime = false;

        DriverStation.reportError("Driving by Distance",false);
    }


    public AutoDrive(boolean onRamp){
        if(onRamp){
            stopMoving.tankDrive(0,0); //stop//TODO: reset encoder
            moveArmsDown(); //lower arms
            driveForward = new AutoDrive(0.5, centerChevalDistance); //drive forward
        }
    }

    private void moveArmsDown() {
        autoArmsLow = new AutoDrive(-0.5, Constants.Autonomous.ARMS_LOW);
    }

    @Override
    protected void initialize() {
        DriverStation.reportError(String.format("Accelerating to %1$f\n", rightSpeed), false);
        end = (new Date()).getTime() + timeToDrive;

        driveTrain.resetDriveEncoders();
    }

    @Override
    protected void execute() {
        driveTrain.tankDrive(leftSpeed, rightSpeed);
        public boolean isOnRamp() {
            if (currentAngle == desiredAngle) {
                onRamp = true;
            }
        }
    }

    @Override
    protected boolean isFinished() {
        if (!driveByTime) {
            inchesDriven = driveTrain.getRightDistance();
            return Math.abs(inchesDriven)>=Math.abs(inchesToDrive);
        }

        else if(driveByTime) {
            long now = (new Date()).getTime();
            return now > end;
        }
    return true;

        if(){

         }
    }



    @Override
    protected void end() {
        DriverStation.reportError(String.format("   Right speed: %1$f\n", driveTrain.getRightSpeed()), false);
        DriverStation.reportError(String.format("    Right rate: %1$f\n", driveTrain.getRightRate()), false);
        DriverStation.reportError(String.format("    Left speed: %1$f\n", driveTrain.getLeftSpeed()), false);
        DriverStation.reportError(String.format("     Left rate: %1$f\n", driveTrain.getLeftRate()), false);
        DriverStation.reportError("====================\n", false);
    }

    @Override
    protected void interrupted() {

    }
}
