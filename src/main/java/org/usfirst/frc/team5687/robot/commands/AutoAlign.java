package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import static org.usfirst.frc.team5687.robot.Robot.imu;
import static org.usfirst.frc.team5687.robot.Robot.driveTrain;

/**
 * Created by Ben on 2/6/2016.
 */
public class AutoAlign extends Command implements PIDOutput{
    public static PIDController turnController;
    private static final double kP = 0.3;
    private static final double kI = 0.05;
    private static final double kD = 0.1;
    private static final double kF = 0.1;//Q: What is this for?
    private static final double rotationDeadband = 0.01;
    private static final double kToleranceDegrees = 2.0f;


    private double rotateToAngleRate = 0; //Q: how does the PIDcontroller object know to use this variable?
    private double targetAngle = 0;
    private double currentAngle = 0;
    public AutoAlign(double targetAngle) {
        requires(driveTrain);
        this.targetAngle = targetAngle;
        turnController = new PIDController(kP, kI, kD, imu, this);
        turnController.setInputRange(-180.0f,  180.0f);
        turnController.setOutputRange(-0.5, 0.5);
        turnController.setAbsoluteTolerance(kToleranceDegrees);
        turnController.setContinuous(true);
    }


    protected void initialize(){
        DriverStation.reportError("Starting autoalign", false);
        SmartDashboard.putNumber("AutoAlign/Target Angle", targetAngle);
        imu.setPIDSourceType(PIDSourceType.kRate);
        turnController = new PIDController(kP, kI, kD, kF, imu, this);
        turnController.setInputRange(-180.0f,  180.0f);
        turnController.setOutputRange(-0.5, 0.5);
        turnController.setAbsoluteTolerance(kToleranceDegrees);
        turnController.setContinuous(true);
        turnController.setSetpoint(targetAngle);
        turnController.enable();
    }

    protected void execute(){
    synchronized (this) {
        // Base turning on the rotateToAngleRate...
        turnController.enable();
        SmartDashboard.putNumber("AutoAlign/Rotating Rate", rotateToAngleRate);
        driveTrain.tankDrive(rotateToAngleRate, -1*rotateToAngleRate);
        currentAngle = imu.getYaw();
        SmartDashboard.putNumber("AutoAlign/CurrentAngle", currentAngle);
            // Base turning on the rotateToAngleRate...
            //turnController.enable();

        }

    }

    protected boolean isFinished() {
        // Stop rotating when the PID speed drops below our deadband.
        boolean done = Math.abs(targetAngle-currentAngle) < kToleranceDegrees;
        if (done) {
            SmartDashboard.putNumber("AutoAlign/Done at", rotateToAngleRate);
            DriverStation.reportError("Ending autoalign", false);
        }
        return done;
    }

    protected void end() {
        turnController.disable();
    }

    protected void interrupted() {
        turnController.disable();
    }

    @Override
    public void pidWrite(double output) {
        synchronized (this) {
            SmartDashboard.putNumber("AutoAlign/PID Output", output);
            DriverStation.reportError("AutoAlign/PID Output " + output, false);
            rotateToAngleRate = output;
            driveTrain.tankDrive(rotateToAngleRate, -1 * rotateToAngleRate, true);
        }
    }

}
