package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import static org.usfirst.frc.team5687.robot.Robot.driveTrain;
import static org.usfirst.frc.team5687.robot.Robot.pitracker;
import static org.usfirst.frc.team5687.robot.Robot.pitrackerInputs;
import static org.usfirst.frc.team5687.robot.Robot.lights;

/**
 * Autonomous command for chasing a predefined target using input from a vision source via NetworkTables.
 * This will server as the basis for our eventual tower target tracker, and can also be a good way to "demo" the robot.
 * Created by Ben Bernard on 1/31/2016.
 */
public class AutoChaseTarget extends Command {

    // Keeping these constants here rather than in Constants because they ar peripheral to the robot's main function.
    private static final double deadbandWidth = 15;
    private static final double deadbandX = 10;

    private static final double runSpeed = 0.3;
    private static final double runTwist = 0.2;

    private static final double twistSpeed = 0.3;

    private boolean centered = false;
    private boolean inRange = false;

    private double lowWidth = 40;

    private double targetWidth = 160;
    private double targetX = -70;

    @Override
    protected void initialize() {
        centered = false;
        inRange = false;
        targetWidth = pitrackerInputs.getNumber("TARGET_WIDTH", targetWidth);
        targetX = pitrackerInputs.getNumber("TARGET_X", targetX);
        DriverStation.reportError("Starting autochasetarget to width=" + targetWidth + " and X=" + targetX, false);
        lights.turnRingLightOn();
    }

    @Override
    protected void execute() {

        // read pitracker
        boolean sighted = pitracker.getBoolean("TargetSighted", false);
        double width = pitracker.getNumber("width", 0);
        double centerX = pitracker.getNumber("centerX", 0);
        double offset = 0;
        double speed = 0;
        centered = false;
        inRange = false;

        if (sighted) {


            if (centerX > targetX + deadbandX) {
                offset = -1 * (speed==0 ? twistSpeed : runTwist);
            } else if (centerX < targetX - deadbandX) {
                offset = +1 * (speed==0 ? twistSpeed : runTwist);
            } else {
                offset = 0;
                centered = true;
            }

            if (width == 0) {
                speed = 0;
            } else if (width < lowWidth) {
                speed = -1 * runSpeed;
            } else if (width < targetWidth - deadbandWidth) {
                // set motor speed
                speed = runSpeed;
            } else if (width > targetWidth + deadbandWidth) {
                speed = -1 * runSpeed;
            } else {
                inRange = true;
                speed = 0;
            }

        } else {
            offset = 0;
            speed = 0;
        }
        SmartDashboard.putNumber("autochase/offset", offset);
        SmartDashboard.putNumber("autochase/speed", speed);

        SmartDashboard.putBoolean("autochase/centered", centered);
        SmartDashboard.putBoolean("autochase/inRange", inRange);

        SmartDashboard.putString("autochase/centering", offset < 0 ? "Turning left" : offset > 0 ? "Turning right" : "Centered");
        SmartDashboard.putString("autochase/range", speed < 0 ? "Moving back" : speed > 0 ? "Moving in" : "In Range");

        DriverStation.reportError("Chase: Center=" + centerX + " Width=" + width + " LeftSpeed=" + (speed - offset) + " RightSpeed=" + (speed + offset), false);

        driveTrain.tankDrive(speed - offset, speed + offset);
    }

    @Override
    protected boolean isFinished() {
        return centered && inRange;
    }

    @Override
    protected void end() {
        lights.turnVisionLightOff();
        DriverStation.reportError(centered && inRange ? "Found target" : "Ran out of time", false);
    }

    @Override
    protected void interrupted() {

    }
}
