package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5687.robot.Constants;
import org.usfirst.frc.team5687.robot.subsystems.DriveTrain;

import static org.usfirst.frc.team5687.robot.Robot.*;

/**
 * Created by Ben Bernard on 4/12/2016.
 */
public class AutoCenterTarget extends AutoAlign {

    private static final double ANGLE_DEADBAND = 0.5;
    private boolean centered = false;
    private long lastMills = 0;
    private double offsetAngle=-999;

    public AutoCenterTarget() {
        super(0);
    }

    @Override
    protected void initialize() {
        centered = false;

        // Tell the PID to do nothing to start
        setTargetAngle(imu.getYaw());
        DriverStation.reportError("Starting AutoCenterTarget", false);
        lights.turnRingLightOn();
        super.initialize();
    }

    @Override
    protected void execute() {
        synchronized (this) {

            // Read the offsetAngle from networktables...
            long mills = (long)pitracker.getNumber("Mills", 0);
            boolean sighted = pitracker.getBoolean("TargetSighted", true);
            double newOffsetAngle = pitracker.getNumber("offsetAngle", 0);

            // If we have no new data, don't change our plan!
            if (mills!=lastMills) {
                lastMills = mills;
                // If we see the target, go ahead and process it
                if (sighted && newOffsetAngle!=offsetAngle) {
                    offsetAngle = newOffsetAngle;
                    // Get our current heading
                    double angle = imu.getYaw();

                    // Add the new offset angle
                    double targetAngle = angle + offsetAngle;

                    // Now tell the PID where to turn!
                    setTargetAngle(targetAngle);

                    centered = Math.abs(newOffsetAngle) < ANGLE_DEADBAND;
                }
            }

            SmartDashboard.putBoolean("AutoCenterTarget/centered", centered);
        }
        super.execute();
    }

    @Override
    protected boolean isFinished() {
        return centered;
    }

    @Override
    protected void end() {
        super.end();
        DriverStation.reportError("AutoCenterTarget complete at " + offsetAngle, false);
    }

    @Override
    protected void interrupted() {
        end();
    }
}
