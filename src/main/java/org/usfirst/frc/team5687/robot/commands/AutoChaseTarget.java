package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5687.robot.Robot;
import static org.usfirst.frc.team5687.robot.Robot.driveTrain;

/**
 * Autonomous command for chasing a predefined target using input from a vision source via NetworkTables.
 * This will server as the basis for our eventual tower target tracker, and can also be a good way to "demo" the robot.
 * Created by Ben Bernard on 1/31/2016.
 */
public class AutoChaseTarget extends Command {

    // Keeping these constants here rather than in Constants because they ar peripheral to the robot's main function.
    private static final double targetWidth = 162;
    private static final double deadbandWidth = 10;
    private static final double speed = 0.3;
    private static final double twist = .2;
    private static final double targetX = -106;
    private static final double deadbandX = 10;

    private double offset = 0;
    private double distance = 0;

    private NetworkTable tracking = null;
    @Override
    protected void initialize() {
        driveTrain = Robot.driveTrain;
        NetworkTable.setServerMode();
        tracking = NetworkTable.getTable("/PITracker");

        DriverStation.reportError("Starting autochasetarget", false);
    }

    @Override
    protected void execute() {

        // read network tables
        // double[] defaultvalue = new double[0];
        double width = tracking.getDouble("width");
        // double width = widths.length==0?0:widths[0];

        double centerX = tracking.getDouble("centerX");
        // double centerX = centerXs.length==0?0:centerXs[0];

        DriverStation.reportError("CenterX " + Double.toString(centerX), false);
        DriverStation.reportError("Width " + Double.toString(width), false);

        offset = 0;

        if (centerX > targetX + deadbandX) {
            offset = -1*twist;
        } else if (centerX < targetX - deadbandX) {
            offset = +1*twist;
        }
        DriverStation.reportError("Offset " + Double.toString(offset), false);

        if (width==0) {
            driveTrain.tankDrive(0,0);
        } else if (width<targetWidth - deadbandWidth) {
            // set motor speed
            DriverStation.reportError("Move forward " + Double.toString(speed), false);
            driveTrain.tankDrive(-1*speed + offset, -1*speed - offset);
            distance = 1;
        } else if (width>targetWidth + deadbandWidth) {
            DriverStation.reportError("Move back " + Double.toString(speed), false);
            driveTrain.tankDrive(speed, speed);
            distance = -1;
        } else {
            driveTrain.tankDrive(offset, -1*offset);
            distance = 0;

        }
    }

    @Override
    protected boolean isFinished() {
        DriverStation.reportError("Found target.", false);
        return distance == 0 && offset == 0;
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {

    }
}
