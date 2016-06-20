package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5687.robot.Constants;
import org.usfirst.frc.team5687.robot.utils.OutliersPose;
import org.usfirst.frc.team5687.robot.utils.PiTracker;

import static org.usfirst.frc.team5687.robot.Robot.*;

/**
 * Created by Ben Bernard on 4/12/2016.
 */
public class AutoApproachTarget extends AutoDrive {
    private static final double AUTO_APPROACH_SPEED = 0.6;

    private boolean inRange = false;
    private long lastMills = 0;
    private Double gap;

    boolean sighted = false;

    public AutoApproachTarget() {
        super(AUTO_APPROACH_SPEED);
    }

    @Override
    protected void initialize() {
        lights.turnRingLightOn();
        inRange = false;

        DriverStation.reportError("Starting AutoApproachTarget to distance=" + Constants.Target.SHOOTING_DISTANCE, false);
        super.initialize();
    }

    @Override
    protected void execute() {
        synchronized (this) {

            Double newGap = null;

            // See how far the piTracker says we need to drive...
            PiTracker.Frame frame = piTracker.getLatestFrame();
            if (frame!=null) {
                inRange = Math.abs(frame.getDistance() - Constants.Target.SHOOTING_DISTANCE ) < Constants.Target.SHOOTING_DISTANCE_DEADBAND;

                OutliersPose pose = (OutliersPose) poseTracker.get(frame.getMillis());
                if (pose != null) {
                    newGap = (pose.getDistance() + (frame.getDistance() - Constants.Target.SHOOTING_DISTANCE)) - driveTrain.getDistance();
                }
            }

            if (newGap!=null && (gap==null || gap!=newGap)) {
                gap = newGap;
                DriverStation.reportError("AutoApproach gap set to " + gap, false);

                setDistance(gap);
            }

            SmartDashboard.putBoolean("AutoApproachTarget/inRange", inRange);

            super.execute();
        }
    }

    @Override
    protected boolean isFinished() {
        return inRange;
    }

    @Override
    protected void end() {
        super.end();
        DriverStation.reportError("AutoApproachTarget complete at gap " +  gap, false);
    }

    @Override
    protected void interrupted() {
        end();
    }
}
