package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.Constants;
import org.usfirst.frc.team5687.robot.utils.OutliersPose;
import org.usfirst.frc.team5687.robot.utils.PiTrackerProxy;

import static org.usfirst.frc.team5687.robot.Robot.*;

/**
 * Created by Ben Bernard on 4/12/2016.
 */
public class AutoApproachTarget extends Command {


    private static final double kPturn = 0.3;
    private static final double kIturn = 0.05;
    private static final double kDturn = 0.1;
    private static final double kToleranceDegrees = Constants.Target.SHOOTING_ANGLE_DEADBAND;

    private static final double kPdistance = 0.3;
    private static final double kIdistance = 0.05;
    private static final double kDdistance = 0.1;
    private static final double kToleranceInches = Constants.Target.SHOOTING_DISTANCE_DEADBAND;


    private static final double AUTO_APPROACH_SPEED = 0.6;

    private boolean inRange = false;
    private boolean isCentered = false;

    private Double rotateToAngleRate;
    private Double driveSpeed;

    private PIDController distanceController;
    private PIDController turnController;

    boolean sighted = false;
    private double distance = 0;
    private double angle = 0;



    public AutoApproachTarget() {


    }

    @Override
    protected void initialize() {
        lights.turnRingLightOn();
        inRange = false;
        isCentered = false;

        DriverStation.reportError("Starting AutoApproachTarget to distance=" + Constants.Target.SHOOTING_DISTANCE, false);

        // Initialize the alignment PID to make sure we keep going towards the target...


        turnController = new PIDController(kPturn, kIturn, kDturn, imu, this::angleWrite);
        turnController.setInputRange(-180.0f,  180.0f);
        turnController.setOutputRange(-0.3, 0.3);
        turnController.setContinuous(true);
        turnController.setAbsoluteTolerance(kToleranceDegrees);


        // Initialize the distance PID
        distanceController = new PIDController(kPdistance, kIdistance, kDdistance, driveTrain, this::distanceWrite);

        distanceController.setInputRange(-96.0f, 96.0f);
        distanceController.setOutputRange(-0.6, 0.6);
        distanceController.setAbsoluteTolerance(kToleranceInches);

    }

    @Override
    protected void execute() {
        synchronized (this) {

            Double newAngle = null;
            Double newDistance = null;

            // Get the latest input from the piTracker...
            PiTrackerProxy.Frame frame = piTracker.getLatestFrame();

            if (frame != null) {
                // If we have a new frame from the piTracker, find the closest pose entry from the poseTracker
                OutliersPose pose = (OutliersPose) poseTracker.get(frame.getMillis());
                if (pose != null) {
                    // If we found one, determine the angle we need to turn to and the encoder distance we need to move to
                    newAngle = pose.getAngle() + frame.getOffsetAngle();
                    newDistance = pose.getDistance() + (distance = frame.getDistance()) - Constants.Target.SHOOTING_DISTANCE;
                }
            }

            // If we have a new angle, and it's different from our old one, send it to the turning PIDController
            if (newAngle != null && turnController.getSetpoint() != newAngle) {
                DriverStation.reportError("Setting target angle to " + newAngle, false);
                turnController.setSetpoint(newAngle);
                turnController.enable();
            }


            if (newDistance != null && distanceController.getSetpoint() != newDistance) {
                DriverStation.reportError("AutoApproach distance set to " + newDistance, false);
                distanceController.setSetpoint(newDistance);
                distanceController.enable();
            }
        }

        synchronized (this) {
            double speed = driveSpeed;
            double twist = rotateToAngleRate;

            driveTrain.tankDrive(speed + twist, speed - twist, true);

            isCentered = turnController.onTarget();
            inRange = distanceController.onTarget();
        }
    }

    @Override
    protected boolean isFinished() {
        return inRange && isCentered;
    }

    @Override
    protected void end() {
        turnController.disable();
        distanceController.disable();
        DriverStation.reportError("AutoApproachTarget complete at distance " +  distance + " and angle " + angle, false);
    }

    @Override
    protected void interrupted() {
        end();
    }

    synchronized public void distanceWrite(double output) {
        driveSpeed = output;
    }

    synchronized public void angleWrite(double output) {
        rotateToAngleRate = output;
    }

}
