package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.Constants;
import org.usfirst.frc.team5687.robot.utils.PiTrackerProxy;

import static org.usfirst.frc.team5687.robot.Robot.*;

/**
 * Created by Ben Bernard on 4/12/2016.
 */
public class AutoDetectTarget extends Command {

    private double targetDistance = Constants.Target.WIDTH;

    boolean isCentered = false;
    boolean isInRange = false;

    public AutoDetectTarget() {
    }

    @Override
    protected void initialize() {

        DriverStation.reportError("Starting AutoDetectTarget to offsetAngle between " + (0-Constants.Target.SHOOTING_ANGLE_DEADBAND) + " and " + (Constants.Target.SHOOTING_ANGLE_DEADBAND) + ",  distance between " + (Constants.Target.SHOOTING_DISTANCE - Constants.Target.SHOOTING_DISTANCE_DEADBAND) + " and " + (Constants.Target.SHOOTING_DISTANCE + Constants.Target.SHOOTING_DISTANCE_DEADBAND) , false);
        lights.turnRingLightOn();
    }

    @Override
    protected void execute() {

        PiTrackerProxy.Frame frame = piTracker.getLatestFrame();

        if (frame!=null) {
            isCentered = Math.abs(frame.getOffsetAngle()) < Constants.Target.SHOOTING_ANGLE_DEADBAND;
            isInRange = Math.abs(frame.getDistance() - Constants.Target.SHOOTING_DISTANCE ) < Constants.Target.SHOOTING_DISTANCE_DEADBAND;
        }


    }

    @Override
    protected boolean isFinished() {
        return isCentered && isInRange;
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {
        end();
    }

}
