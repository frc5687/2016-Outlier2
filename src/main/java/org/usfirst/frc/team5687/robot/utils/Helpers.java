package org.usfirst.frc.team5687.robot.utils;

import edu.wpi.first.wpilibj.DriverStation;
import org.usfirst.frc.team5687.robot.Constants;

/**
 * A collection of helper/ math methods used throughout the robot code
 */
public class Helpers{
    /**
     * Logs an action to Drive Station
     * @param message the message to send to Drive Station
     */
    protected void LogAction(String message) {
        DriverStation.reportError(message + "\r\n", false);
    }



    /**
	 * Applies a deadband threshold to a given value
	 * @param input raw value from joystick
	 * @param deadband the deadband threshold
	 * @return double the adjusted value
	 */
    public static double applyDeadband(double input, double deadband){
        return (Math.abs(input) >= Math.abs(deadband)) ? input : 0;
    }

    /**
     * Applies a transform to the input to provide better sensitivity at low speeds.
     * @param input the raw input value from a joystick
     * @return the adjusted control value
     */
    public static double applySensitivityTransform(double input) {
        // See http://www.chiefdelphi.com/forums/showthread.php?p=921992

        // The transform can only work on values between -1 and 1.
        if (input>1) { return 1; }
        if (input <-1) { return -1; }

        // THe sensitivity factor MUST be between 0 and 1!
        double factor = Math.max(Math.min(Constants.Calibration.SENSITIVITY_FACTOR, 1),0);

        return factor*input*input*input + (1-factor)*input;
    }
}
