package org.usfirst.frc.team5687.robot.commands;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Command;
import static org.usfirst.frc.team5687.robot.Robot.imu;
import static org.usfirst.frc.team5687.robot.Robot.driveTrain;
/**
 * Created by Ben on 2/6/2016.
 */
public class AutoAlign extends Command implements PIDOutput{
    public static PIDController turnController;
    public static final double rotationDeadband = 0.1;
    double rotateToAngleRate; //Q: how does the PIDcontroller object know to use this variable?
    double targetAngle;
    public AutoAlign(double targetAngle) {
        this.targetAngle = targetAngle;//Q:What value does targetAngle hold? i.e., how does the robot know if it needs to stay straight without a numerical value?
        DriverStation.reportError("Turning to Angle", false);

        turnController = new PIDController(kP, kI, kD, kF, imu, this);//Q: Why doesn't the open source code have imu.getYaw?
        /*Q:If imu is the the AHRS object, this means that the PIDController object is getting information from the gyroscope, which makes sense.
        In the constructor, why would this take an output value? If it's to have a place to put the output motor speed, isn't that taken care of
        with the abstract method we fill in at the bottom, pidWrite?

        1) So then what is the role of the PIDoutput parameter?
        2) Also, if 'this' takes in all of the values in the class and gives them to the instantiation of the class, is that done in the place where PIDoutput
        goes so that the output knows to 'use' the variable rotateToAngleRate?

        */
        turnController.setInputRange(-180.0f,  180.0f);
        turnController.setOutputRange(-1.0, 1.0);
        turnController.setAbsoluteTolerance(kToleranceDegrees);
        turnController.setContinuous(true);

    }

    static final double kP = 0.03;
    static final double kI = 0.00;
    static final double kD = 0.00;
    static final double kF = 0.00;//Q: What is this for?
    static final double kToleranceDegrees = 2.0f;


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
