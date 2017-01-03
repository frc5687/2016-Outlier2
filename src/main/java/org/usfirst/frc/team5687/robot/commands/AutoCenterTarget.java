package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5687.robot.Constants;
import org.usfirst.frc.team5687.robot.utils.OutliersPose;
import org.usfirst.frc.team5687.robot.utils.PiTrackerProxy;
import org.usfirst.frc.team5687.robot.utils.SynchronousPIDController;

import static org.usfirst.frc.team5687.robot.Robot.*;

/**
 * Created by Ben Bernard on 4/12/2016.
 */
public class AutoCenterTarget extends Command {

    private PIDController turnController;
    private static final double kPturn = 0.3;
    private static final double kIturn = 0.05;
    private static final double kDturn = 0.1;
    private static final double kToleranceDegrees = Constants.Target.SHOOTING_ANGLE_DEADBAND;

    private boolean isCentered = false;

    private double rotateToAngleRate = 0;

    public AutoCenterTarget() {
        requires(driveTrain);
    }

    @Override
    synchronized protected void initialize() {
        lights.turnRingLightOn();
        DriverStation.reportError("Starting AutoCenterTarget", false);
        turnController = new PIDController(kPturn, kIturn, kDturn, imu, this::angleWrite);
        turnController.setInputRange(-180.0f,  180.0f);
        turnController.setOutputRange(-0.3, 0.3);
        turnController.setContinuous(true);
        turnController.setAbsoluteTolerance(kToleranceDegrees);
    }

    @Override
    synchronized protected void execute() {
        synchronized (this) {

            Double newAngle = null;

            // Get the latest input from the piTracker...
            PiTrackerProxy.Frame frame = piTracker.getLatestFrame();

            if (frame != null) {
                // If we have a new frame from the piTracker, find the closest pose entry from the poseTracker
                OutliersPose pose = (OutliersPose) poseTracker.get(frame.getMillis());
                if (pose != null) {
                    // If we found one, determine the angle we need to turn to and the encoder distance we need to move to
                    newAngle = pose.getAngle() + frame.getOffsetAngle();
                }
            }

            // If we have a new angle, and it's different from our old one, send it to the turning PIDController
            if (newAngle != null && turnController.getSetpoint() != newAngle) {
                DriverStation.reportError("Setting target angle to " + newAngle, false);
                turnController.setSetpoint(newAngle);
                turnController.enable();
            }

        }

        synchronized (this) {
            double twist = rotateToAngleRate;

            driveTrain.tankDrive(twist, 0 - twist, true);

            isCentered = turnController.onTarget();
        }
    }

    @Override
    synchronized protected boolean isFinished() {
        return isCentered;
    }

    @Override
    synchronized protected void end() {
        turnController.disable();
        DriverStation.reportError("AutoCenterTarget completed at " + imu.getYaw(), false);
    }

    @Override
    synchronized protected void interrupted() {
        end();
    }

    synchronized public void angleWrite(double output) {
        rotateToAngleRate = output;
    }

}
