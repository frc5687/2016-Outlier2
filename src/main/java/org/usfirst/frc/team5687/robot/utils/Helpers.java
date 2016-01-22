package org.usfirst.frc.team5687.robot.utils;

import edu.wpi.first.wpilibj.DriverStation;

public class Helpers{
    /*
     * Logs an action to Drive Station
     * @param message the message to send to Drive Station
     */
    protected void LogAction(String message) {
        DriverStation.reportError(message + "\r\n", false);
    }

    /*
	 * This applies a deadband value
	 * @param input raw value from joystick
	 * @param deadband the deadband threshold
	 * @return double the adjusted value
	 */
    public static double applyDeadband(double input, double deadband){
        return (Math.abs(input) >= Math.abs(deadband)) ? input : 0;
    }
}
