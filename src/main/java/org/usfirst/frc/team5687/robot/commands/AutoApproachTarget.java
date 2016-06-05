package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5687.robot.Constants;

import static org.usfirst.frc.team5687.robot.Robot.*;

/**
 * Created by Ben Bernard on 4/12/2016.
 */
public class AutoApproachTarget extends Command {
    private static final double SHOOTING_DISTANCE_DEADBAND = 5;
    private static final double AUTO_APPROACH_SPEED = 0.6;

    private boolean inRange = false;
    private long lastMills = 0;
    private double distance;

    boolean sighted = false;

    public AutoApproachTarget() {
    }

    @Override
    protected void initialize() {
        inRange = false;

        DriverStation.reportError("Starting AutoApproachTarget to distance=" + Constants.Target.SHOOTING_DISTANCE, false);
        lights.turnRingLightOn();
    }

    @Override
    protected void execute() {
        synchronized (this) {

            // Read the offsetAngle from networktables...
            long mills = (long)pitracker.getNumber("Mills", 0);
            boolean sighted = pitracker.getBoolean("TargetSighted", true);
            double newDistance = pitracker.getNumber("distance", 0);

            // If we have no new data, don't change our plan!
            if (mills!=lastMills) {
                lastMills = mills;
                // If we see the target, go ahead and process it
                if (sighted && newDistance!=distance) {
                    distance = newDistance;

                    // Get the gap
                    double gap = distance - Constants.Target.SHOOTING_DISTANCE;

                    inRange = Math.abs(gap) < SHOOTING_DISTANCE_DEADBAND;
                    if (!inRange) {
                        Scheduler.getInstance().add(new AutoDrive(AUTO_APPROACH_SPEED, gap));
                    }
                }
            }

            SmartDashboard.putBoolean("AutoApproachTarget/inRange", inRange);
        }
    }

    @Override
    protected boolean isFinished() {
        return inRange;
    }

    @Override
    protected void end() {
        DriverStation.reportError("AutoApproachTarget complete at distance " +  distance, false);
    }

    @Override
    protected void interrupted() {
        end();
    }
}
