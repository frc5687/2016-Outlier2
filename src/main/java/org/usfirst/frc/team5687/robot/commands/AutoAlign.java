package org.usfirst.frc.team5687.robot.commands;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Created by Maya on 2/6/2016.
 */
public class AutoAlign extends Command implements PIDOutput {

    public AutoAlign(double targetAngle, double angle) {
        this.targetAngle = targetAngle;
        this.angle = angle;
        DriverStation.reportError("Turning to Angle", false);
    }
    //Drivetrain object to get the rotation value
    //A method that the code goes through to correct it for current location

    PIDController turnController;
    AHRS ahrs;
    static final double Kp = 0.03;
    static final double Ki = 0.00;
    static final double Kd = 0.00;
    //static final double kF = 0.00; //TODO: What is this for?
    double rotateToAngleRate;
    boolean rotateToAngle = false;
    boolean isForward = true;
    double angle;
    double targetAngle; //TODO: What is the target angle?

    protected void initialize(){

        turnController = new PIDController(Kp, Ki, Kd, ahrs, this);


    }
    protected void execute(){   //Classname.methodName(use these to align robot);

        



        try {
          /* Communicate w/navX-MXP via the MXP SPI Bus.                                     */
          /* Alternatively:  I2C.Port.kMXP, SerialPort.Port.kMXP or SerialPort.Port.kUSB     */
          /* See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/ for details. */
            ahrs = new AHRS(SPI.Port.kMXP);
        } catch (RuntimeException ex ) {
            DriverStation.reportError("Error instantiating navX-MXP:  " + ex.getMessage(), true);
        }



        if (isForward) {//TODO:Method to set boolean isForward equal to false if the robot is backwards.

            turnController.setSetpoint(targetAngle); //TODO: What angle do we want the robot to rotate to?
            rotateToAngle = true;
            //TODO: Maybe add a DriverStation report to help diagnose potential problems?
        } else if (!isForward){
            turnController.setSetpoint(-targetAngle); //TODO: What angle do we want the robot to rotate to?
            rotateToAngle = true;
        }




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
