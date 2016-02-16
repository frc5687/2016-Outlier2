package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Command;
import static org.usfirst.frc.team5687.robot.Robot.imu;
import static org.usfirst.frc.team5687.robot.Robot.driveTrain;

/**
 * Created by Ben on 2/6/2016.
 */
public class AutoAlign extends Command implements PIDOutput{
    public static PIDController turnController;
    private static final double kP = 0.03;
    private static final double kI = 0.00;
    private static final double kD = 0.00;
    private static final double kF = 0.00;//Q: What is this for?
    private static final double rotationDeadband = 0.1;
    private static final double kToleranceDegrees = 2.0f;
    private double rotateToAngleRate; //Q: how does the PIDcontroller object know to use this variable?
    private double targetAngle;

    public AutoAlign(double targetAngle) {
        this.targetAngle = targetAngle;  //Q:What value does targetAngle hold? i.e., how does the robot know if it needs to stay straight without a numerical value?
        DriverStation.reportError("Turning to Angle", false);

        turnController = new PIDController(kP, kI, kD, kF, imu, this);
        turnController.setInputRange(-180.0f,  180.0f);
        turnController.setOutputRange(-1.0, 1.0);
        turnController.setAbsoluteTolerance(kToleranceDegrees);
        turnController.setContinuous(true);
    }

    protected void initialize(){
            turnController.enable();
        }

    protected void execute(){
        // Base turning on the rotateToAngleRate...
        turnController.enable();
        driveTrain.tankDrive(rotateToAngleRate, -1*rotateToAngleRate);//Q: Doesn't this make the robot turn right? What if the distance to turn left is shorter?
    }

    protected boolean isFinished() {
        // Stop rotating when the PID speed drops below our deadband.
        return Math.abs(rotateToAngleRate)< rotationDeadband;
    }

    protected void end() {
        turnController.disable();
    }

    protected void interrupted() {
        turnController.disable();
    }

    @Override
    public void pidWrite(double output) {
        rotateToAngleRate = output;
    }

}
