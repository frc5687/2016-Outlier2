package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5687.robot.Constants;

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
    private static final double deadbandWidth = 5;
    private static final double deadbandX = 5;

    private static final double runSpeed = 0.4;
    private static final double runTwist = 0.2;

    private static final double twistSpeed = 0.3;

    private boolean centered = false;
    private boolean inRange = false;

    private double lowWidth = Constants.Target.LOW_WIDTH;

    private double targetWidth = Constants.Target.WIDTH;
    private double targetX = Constants.Target.X;

    public AutoChaseTarget() {
        requires(driveTrain);
    }

    @Override
    protected void initialize() {
        centered = false;
        inRange = false;
        targetWidth = pitrackerInputs.getNumber("TARGET_WIDTH", targetWidth);
        targetX = pitrackerInputs.getNumber("TARGET_X", targetX);
        pitrackerInputs.putNumber("TARGET_WIDTH", targetWidth);
        pitrackerInputs.putNumber("TARGET_X", targetX);

        double width = pitracker.getNumber("width", 0);
        double centerX = pitracker.getNumber("centerX", 0);
        pitracker.putNumber("width", width);
        pitracker.putNumber("centerX", centerX);

        boolean sighted = pitracker.getBoolean("TargetSighted", false);
        pitracker.putBoolean("TargetSighted", sighted);

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

        if (true) {

            if (centerX > targetX + deadbandX) {
                DriverStation.reportError("Turning right " + centerX + ">" + (targetX + deadbandX), false);
                offset = -1 * (speed==0 ? twistSpeed : runTwist);
            } else if (centerX < targetX - deadbandX) {
                DriverStation.reportError("Turning left " + centerX + "<" + (targetX - deadbandX), false);
                offset = +1 * (speed==0 ? twistSpeed : runTwist);
            } else {
                DriverStation.reportError("Centered " + centerX + " + between " + (targetX - deadbandX) + " and " + (targetX + deadbandX), false);
                offset = 0;
                centered = true;
            }

            if (width == 0) {
                speed = 0;
            } else if (width < lowWidth) {
                DriverStation.reportError("Backing up " + width + " < " + lowWidth, false);
                speed = 0;
            } else if (width < targetWidth - deadbandWidth) {
                // set motor speed
                DriverStation.reportError("Moving in " + width +" < " + (targetWidth - deadbandWidth), false);
                speed = runSpeed;
            } else if (width > targetWidth + deadbandWidth) {
                DriverStation.reportError("Backing up " + width + " > " + (targetWidth + deadbandWidth), false);
                speed = -1 * runSpeed;
            } else {
                DriverStation.reportError("In range " + width + " + between " + (targetWidth - deadbandWidth) + " and " + (targetWidth + deadbandWidth), false);
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

        driveTrain.tankDrive(speed - offset, speed + offset, true);
    }

    @Override
    protected boolean isFinished() {
        return centered && inRange;
    }

    @Override
    protected void end() {
        lights.turnRingLightOff();
        DriverStation.reportError(centered && inRange ? "Found target" : "Ran out of time", false);
    }

    @Override
    protected void interrupted() {
        end();
    }
}
