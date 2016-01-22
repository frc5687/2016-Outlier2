package org.usfirst.frc.team5687.robot.utils;

import edu.wpi.first.wpilibj.DriverStation;

public class Helpers{
    protected void LogAction(String message) {
        // Logs the action to Drive Station
        DriverStation.reportError(message + "\r\n", false);
    }
}
