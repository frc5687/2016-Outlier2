package org.usfirst.frc.team5687.robot.commands;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5687.robot.OI;
import org.usfirst.frc.team5687.robot.Robot;
import org.usfirst.frc.team5687.robot.subsystems.DriveTrain;

import java.util.Date;

import static org.usfirst.frc.team5687.robot.Robot.imu;

/**
 * Autonomous command to run the drivetrain.
 * For now, runs at a preset speed for a preset time.
 * Eventually we will want to add distance-based options.
 */

public class AutoDrive extends Command implements PIDOutput{
    DriveTrain driveTrain = Robot.driveTrain;
    AHRS imu = Robot.imu;
    PIDController turnController = AutoAlign.turnController;
    OI oi = Robot.oi;
    private long end = 0;
    private int timeToDrive = 0;
    private double inchesToDrive = 0;
    private double rightSpeed = 0;
    private double leftSpeed = 0;
    private double inchesDriven = 0;
    private boolean driveByTime;

    private static final double kP = 0.3;
    private static final double kI = 0.05;
    private static final double kD = 0.1;
    private static final double kF = 0.1;//Q: What is this for?
    private static final double rotationDeadband = 0.01;
    private static final double kToleranceDegrees = 2.0f;
    private double rotateToAngleRate = 0; //Q: how does the PIDcontroller object know to use this variable?
    private double targetAngle = 0;
    private double currentAngle = 0;

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

    @Override
    protected void initialize() {
        DriverStation.reportError(String.format("Accelerating to %1$f\n", rightSpeed), false);
        end = (new Date()).getTime() + timeToDrive;
        targetAngle = imu.getYaw();
        driveTrain.resetDriveEncoders();
        turnController = new PIDController(kP, kI, kD, kF, imu, this);
        turnController.setInputRange(-0.1f, 0.1f);
        turnController.setOutputRange(-0.5, 0.5);
        turnController.setAbsoluteTolerance(kToleranceDegrees);
        turnController.setContinuous(true);
        turnController.setSetpoint(targetAngle);
        turnController.enable();
    }

    @Override
    protected void execute() {
//If the speed is faster than

        driveTrain.tankDrive(leftSpeed, rightSpeed);

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
            DriverStation.reportError("AutoAlign/PID Output " + output, false);
            rotateToAngleRate = output;
        }
    }
}
