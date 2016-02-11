package org.usfirst.frc.team5687.robot.commands;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.Robot;
import org.usfirst.frc.team5687.robot.subsystems.DriveTrain;

/**
 * Created by Maya on 2/6/2016.
 */
public class AutoAlign extends Command implements PIDOutput{
    DriveTrain driveTrain = Robot.driveTrain;
    public static PIDController turnController;
    AHRS ahrs;
    RobotDrive myRobot;
    Joystick stick;
    RobotDrive rotate;

    rotate = new RobotDrive;//TODO: Fix this and see if you can use the rotateVector method to drive the robot.
    double rotateToAngleRate;

   /* public AutoAlign(double targetAngle, double angle) {
        this.targetAngle = targetAngle;
        this.angle = angle;
        DriverStation.reportError("Turning to Angle", false);
    }
*/

    static final double kP = 0.03;
    static final double kI = 0.00;
    static final double kD = 0.00;
    static final double kF = 0.00;

    static final double kToleranceDegrees = 2.0f;

    turnController = new PIDController(kP, kI, kD, kF, ahrs, this);



    protected void initialize(){

    }


    protected void execute(){driveTrain.autoAlign();}//Classname.methodName(use these to align robot);


    }
    protected boolean isFinished() {
        return true; //TODO: Just so it will compile

    }
    protected void end() {

    }
    protected void interrupted() {

    }

    @Override
    public void pidWrite(double output) {
        rotateToAngleRate = output;
    }
}
