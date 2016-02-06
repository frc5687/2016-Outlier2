package org.usfirst.frc.team5687.robot.commands;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Created by Baxter on 2/6/2016.
 */
public class AutoAlign extends Command {

    //Drivetrain object to get the rotation value
    //A method that the code goes through to correct it for current location

    PIDController turnController;
    AHRS ahrs;
    static final double Kp = 0.03;
    static final double Ki = 0.00;
    static final double Kd = 0.00;
    //static final double kF = 0.00; //TODO: What is this for?



    protected void initialize(){

        turnController = new PIDController(Kp, Ki, Kd, ahrs, this);//TODO: why do they send values to the PIDcontroller object if it doesn't impliment PIDOutput?


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

    }
    protected boolean isFinished() {

    }
    protected void end() {

    }
    protected void interrupted() {

    }
}
