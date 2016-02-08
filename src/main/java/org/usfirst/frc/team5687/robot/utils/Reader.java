package org.usfirst.frc.team5687.robot.utils;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.io.*;
import java.nio.Buffer;

/**
 * A file reader class to read Git information from code deploy
 * @author wilstenholme
 */
public class Reader {
    private final static String gitLogName = "deploy.txt";
    private String content;

    public Reader() {
        File file;
        BufferedReader br;

        try {
            br = new BufferedReader(new FileReader(gitLogName));

            // Log git info to a string
            content = br.readLine();
            br.close();
        } catch (IOException ex) {
            // Report error to the Driver Station if log file does not exist
            String errorMessage = getStackTrace(ex);
            DriverStation.reportError("Error reading git log file", true);
            DriverStation.reportError(errorMessage, true);
        }
    }

    public static String getStackTrace (Throwable t) {
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    public String getGitInfo() {
        return content;
    }
}
