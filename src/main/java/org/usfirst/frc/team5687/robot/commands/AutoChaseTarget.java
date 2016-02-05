package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import org.usfirst.frc.team5687.robot.Robot;
import org.usfirst.frc.team5687.robot.subsystems.DriveTrain;

/**
 * Autonomous command for chasing a predefined target using input from a vision source via NetworkTables.
 * This will server as the basis for our eventual tower target tracker, and can also be a good way to "demo" the robot.
 * Created by Ben Bernard on 1/31/2016.
 */
public class AutoChaseTarget extends Command {
    DriveTrain driveTrain;
    NetworkTable tracking;


    // Keeping these constants here rather than in Constants because they ar peripheral to the robot's main function.
    private static final double targetWidth = 110;
    private static final double speed = 0.4;
    private static final double twist = .2;
    private static final double deadband = 30;

    @Override
    protected void initialize() {
        driveTrain = Robot.driveTrain;
        tracking = NetworkTable.getTable("/GRIP/tracking");
        DriverStation.reportError("Starting autochasetarget", false);
    }

    @Override
    protected void execute() {

        // read network tables
        double[] defaultvalue = new double[0];
        double[] widths = tracking.getNumberArray("width", defaultvalue);
        double width = widths.length==0?0:widths[0];

        double[] centerXs = tracking.getNumberArray("centerX", defaultvalue);
        double centerX = centerXs.length==0?0:centerXs[0];

        DriverStation.reportError("Cycle" + Double.toString(width), false);

        double offset = 0;

        if (centerX > 300 + deadband) {
            offset = -1*twist;
        } else if (centerX < 300 - deadband) {
            offset = +1*twist;
        }
        DriverStation.reportError("Offset " + Double.toString(offset), false);

        if (width==0) {
            driveTrain.tankDrive(0,0);
        } else if (width<targetWidth - 10) {
            // set motor speed
            DriverStation.reportError("Move forward " + Double.toString(speed), false);
            driveTrain.tankDrive(-1*speed + offset, -1*speed - offset);
        } else if (width>targetWidth + 10) {
            DriverStation.reportError("Move back " + Double.toString(speed), false);
            driveTrain.tankDrive(speed, speed);
        } else {
            driveTrain.tankDrive(offset, -1*offset);

        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {

    }
}
