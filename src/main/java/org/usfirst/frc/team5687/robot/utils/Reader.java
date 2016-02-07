package org.usfirst.frc.team5687.robot.utils;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.io.*;

/**
 * A file reader class to read Git information from code deploy
 * @author wilstenholme
 */
public class Reader {
    private final static String fileName = "deploy.txt";

    public static void sendGitInfo() {
        String content;
        File file;
        FileReader reader;

        try {
            file = new File(fileName);
            reader = new FileReader(file);

            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            content = new String(chars);

            reader.close();
            // Log the git information to the Smart Dashboard
            SmartDashboard.putString("GitInfo", content);
        } catch (FileNotFoundException ex) {
            // Report error to the Driver Station that log file is missing
            DriverStation.reportError("Error opening git log file", true);
        } catch (IOException ex) {
            // Report error to the Driver Station if log file is damaged or does not exist
            DriverStation.reportError("Error reading git log file", true);
        }
    }
}
