package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5687.robot.Constants;
import org.usfirst.frc.team5687.robot.utils.OutliersPose;
import org.usfirst.frc.team5687.robot.utils.PiTracker;
import org.usfirst.frc.team5687.robot.utils.SynchronousPIDController;

import static org.usfirst.frc.team5687.robot.Robot.*;

/**
 * Created by Ben Bernard on 4/12/2016.
 */
public class AutoApproachTarget extends Command {


    private static final double kPturn = 0.3;
    private static final double kIturn = 0.05;
    private static final double kDturn = 0.1;
    private static final double kToleranceDegrees = Constants.Target.SHOOTING_ANGLE_DEADBAND;

    private static final double kPgap = 0.3;
    private static final double kIgap = 0.05;
    private static final double kDgap = 0.1;
    private static final double kToleranceInches = Constants.Target.SHOOTING_DISTANCE_DEADBAND;


    private static final double AUTO_APPROACH_SPEED = 0.6;

    private boolean inRange = false;
    private boolean isCentered = false;
    private Double gap;

    private SynchronousPIDController gapController;
    private SynchronousPIDController turnController;

    boolean sighted = false;

    public AutoApproachTarget() {


    }

    @Override
    protected void initialize() {
        lights.turnRingLightOn();
        inRange = false;
        isCentered = false;

        DriverStation.reportError("Starting AutoApproachTarget to distance=" + Constants.Target.SHOOTING_DISTANCE, false);

        // Initialize the alignment PID to make sure we keep going towards the target...
        gapController = null;
        turnController = null;

        // Initialize the gap PID

    }

    @Override
    protected void execute() {
        synchronized (this) {

            Double newAngle = null;
            Double newGap = null;

            // See what the piTracker says for angle...
            PiTracker.Frame frame = piTracker.getLatestFrame();
            if (frame!=null) {
                double offsetAngle = frame.getOffsetAngle();

                OutliersPose pose = (OutliersPose)poseTracker.get(frame.getMillis());
                if (pose!=null) {
                    double poseAngle = pose.getAngle();
                    newAngle = poseAngle + offsetAngle;
                    newGap = (pose.getDistance() + (frame.getDistance() - Constants.Target.SHOOTING_DISTANCE)) - driveTrain.getDistance();
                }
            }

            if (newAngle!=null) {
                if (turnController==null) {
                    turnController = new SynchronousPIDController(kPturn, kIturn, kDturn);
                    imu.setPIDSourceType(PIDSourceType.kRate);

                    turnController.setInputRange(-180.0f,  180.0f);
                    turnController.setOutputRange(-0.3, 0.3);
                    turnController.setContinuous(true);
                }

                if (turnController.getSetpoint() != newAngle) {
                    DriverStation.reportError("Setting target angle to " + newAngle, false);
                    turnController.setSetpoint(newAngle);
                }
            }

            double twist = 0;
            if (turnController!=null) {
                twist = turnController.calculate(imu.getYaw());
                // driveTrain.tankDrive(output, -1 * output, true);
            }


            // See how far the piTracker says we need to drive...

            if (newGap!=null) {
                if (gapController==null) {
                    gapController = new SynchronousPIDController(kPgap, kIgap, kDgap);

                    gapController.setInputRange(-48.0f, 48.0f);
                    gapController.setOutputRange(-0.6, 0.6);
                }

                if (gapController.getSetpoint() != newGap) {
                    DriverStation.reportError("AutoApproach gap set to " + newGap, false);
                    gapController.setSetpoint(newGap);
                }
            }

            double speed = 0;
            if (gapController!=null) {
                speed = gapController.calculate(driveTrain.getDistance());
            }

            driveTrain.tankDrive(speed + twist, speed - twist, true);

            isCentered = turnController!=null && turnController.onTarget(kToleranceDegrees);
            inRange = gapController!=null && gapController.onTarget(kToleranceInches);

        }
    }

    @Override
    protected boolean isFinished() {
        return inRange && isCentered;
    }

    @Override
    protected void end() {
        DriverStation.reportError("AutoApproachTarget complete at gap " +  gap, false);
    }

    @Override
    protected void interrupted() {
        end();
    }
}
