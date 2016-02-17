package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5687.robot.Robot;

import static org.usfirst.frc.team5687.robot.Robot.imu;
import static org.usfirst.frc.team5687.robot.Robot.driveTrain;

/**
 * Created by Ben on 2/6/2016.
 */
public class AutoAlign extends Command implements PIDOutput{
    public static PIDController turnController;
    private static final double kP = 0.03;
    private static final double kI = 0.005;
    private static final double kD = 0.00;
    private static final double kF = 0.00;//Q: What is this for?
    private static final double rotationDeadband = 0.1;
    private static final double kToleranceDegrees = 2.0f;
    private double rotateToAngleRate; //Q: how does the PIDcontroller object know to use this variable?
    private double targetAngle;

    public AutoAlign(double targetAngle) {
        this.targetAngle = targetAngle;  //Q:What value does targetAngle hold? i.e., how does the robot know if it needs to stay straight without a numerical value?
        turnController = new PIDController(kP, kI, kD, imu, this);
        turnController.setInputRange(-180.0f,  180.0f);
        turnController.setOutputRange(-0.5, 0.5);
        turnController.setAbsoluteTolerance(kToleranceDegrees);
        turnController.setContinuous(true);
    }

    protected void initialize(){
        DriverStation.reportError("Starting autoalign", false);
        SmartDashboard.putNumber("AutoAlign/Target Angle", targetAngle);
        turnController.enable();
        }

    protected void execute(){
        // Base turning on the rotateToAngleRate...
        turnController.enable();
        SmartDashboard.putNumber("AutoAlign/Rotating Rate", rotateToAngleRate);
        driveTrain.tankDrive(rotateToAngleRate, -1*rotateToAngleRate);//Q: Doesn't this make the robot turn right? What if the distance to turn left is shorter?
    }

    protected boolean isFinished() {
        // Stop rotating when the PID speed drops below our deadband.
        boolean done = Math.abs(rotateToAngleRate)< rotationDeadband;
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
        SmartDashboard.putNumber("AutoAlign/PID Output", output);
        rotateToAngleRate = output;
    }

}
