package org.usfirst.frc.team5687.robot.utils;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.io.*;

/**
 * A file reader class to read Git information from code deploy
 * @author wilstenholme
 */
public class Reader {
    private final static String gitLogName = "deploy.txt";
    private String content;

    public Reader() {
        File file;
        FileReader reader;

        try {
            file = new File(gitLogName);
            reader = new FileReader(file);

            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            content = new String(chars);

            reader.close();
        } catch (FileNotFoundException ex) {
            // Report error to the Driver Station that log file is missing
            DriverStation.reportError("Error opening git log file", true);
            //System.out.println("Error opening git log file");
        } catch (IOException ex) {
            // Report error to the Driver Station if log file is damaged or does not exist
            DriverStation.reportError("Error reading git log file", true);
            //System.out.println("Error reading git log file");
        }
    }

    public String getGitInfo() {
        return content;
    }
}
