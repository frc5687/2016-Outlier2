package org.usfirst.frc.team5687.robot.commands;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Created by Maya on 2/6/2016.
 */
public class AutoAlign extends Command implements PIDOutput{

    //Drivetrain object to get the rotation value
    //A method that the code goes through to correct it for current location

    PIDController turnController;
    AHRS ahrs;
    static final double Kp = 0.03;
    static final double Ki = 0.00;
    static final double Kd = 0.00;
    //static final double kF = 0.00; //TODO: What is this for?
    boolean rotateToAngle = false;
    double rotateToAngleRate;


    protected void initialize(){

        turnController = new PIDController(Kp, Ki, Kd, ahrs, this);


    }
    protected void execute(){
        try {
          /* Communicate w/navX-MXP via the MXP SPI Bus.                                     */
          /* Alternatively:  I2C.Port.kMXP, SerialPort.Port.kMXP or SerialPort.Port.kUSB     */
          /* See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/ for details. */
            ahrs = new AHRS(SPI.Port.kMXP);
        } catch (RuntimeException ex ) {
            DriverStation.reportError("Error instantiating navX-MXP:  " + ex.getMessage(), true);
        }
        turnController.setSetpoint(0.0f); //TODO: What angle do we want the robot to rotate to?
        rotateToAngle = true;
        //TODO: Maybe add a DriverStation report to help diagnose potential problems?





    }
    protected boolean isFinished() {

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
