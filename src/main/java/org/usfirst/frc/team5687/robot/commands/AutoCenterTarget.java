package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5687.robot.Constants;
import org.usfirst.frc.team5687.robot.utils.ManualPIDController;
import org.usfirst.frc.team5687.robot.utils.OutliersPose;
import org.usfirst.frc.team5687.robot.utils.PiTracker;
import org.usfirst.frc.team5687.robot.utils.SynchronousPIDController;

import static org.usfirst.frc.team5687.robot.Robot.*;

/**
 * Created by Ben Bernard on 4/12/2016.
 */
public class AutoCenterTarget extends Command {

    public static SynchronousPIDController turnController;
    private static final double kP = 0.3;
    private static final double kI = 0.05;
    private static final double kD = 0.1;
    private static final double kToleranceDegrees = Constants.Target.SHOOTING_ANGLE_DEADBAND;

    private Double targetAngle = null;
    private boolean isCentered = false;

    public AutoCenterTarget() {
        requires(driveTrain);
    }

    @Override
    synchronized protected void initialize() {
        lights.turnRingLightOn();
        DriverStation.reportError("Starting AutoCenterTarget", false);
        targetAngle = null;
        if (turnController!=null) {
            turnController = null;
        }
    }

    @Override
    synchronized protected void execute() {
        // If we have an existing pid cycle going, and it's reached its target, stop it!
        if (turnController!=null && turnController.onTarget(kToleranceDegrees)) {
            DriverStation.reportError("AutoCenterTarget reached target angle of " + targetAngle, false);

            turnController = null;
            targetAngle = null;
        }

        Double newAngle = null;
        // See what the piTracker says for angle...
        PiTracker.Frame frame = piTracker.getLatestFrame();
        if (frame!=null) {
            double offsetAngle = frame.getOffsetAngle();
            isCentered = Math.abs(offsetAngle) < kToleranceDegrees;

            OutliersPose pose = (OutliersPose)poseTracker.get(frame.getMillis());
            if (pose!=null) {
                double poseAngle = pose.getAngle();
                newAngle = poseAngle + offsetAngle;
            }
        }


        if (newAngle!=null) {
            if (targetAngle==null || targetAngle!=newAngle) {
                DriverStation.reportError("Setting target angle to " + newAngle, false);

                setTargetAngle(newAngle);
            }
        }

        if (turnController!=null) {
            double output = turnController.calculate(imu.getYaw());
            SmartDashboard.putNumber("AutoCenterTarget/PID Output", output);
            DriverStation.reportError("AutoCenterTarget/target " + targetAngle, false);
            DriverStation.reportError("AutoCenterTarget/Yaw " + imu.getYaw(), false);
            DriverStation.reportError("AutoCenterTarget/PID Output " + output, false);
            driveTrain.tankDrive(output, -1 * output, true);
        }


    }

    synchronized private void setTargetAngle(double angle) {
        this.targetAngle = angle;
        // Disable any existing controller
        if (turnController!=null) { turnController = null; }
        SmartDashboard.putNumber("AutoCenterTarget/Target Angle", targetAngle);
        imu.setPIDSourceType(PIDSourceType.kRate);
        turnController = new SynchronousPIDController(kP, kI, kD);
        turnController.setInputRange(-180.0f,  180.0f);
        turnController.setOutputRange(-0.6, 0.6);
        turnController.setContinuous(true);
        turnController.setSetpoint(targetAngle);
    }

    @Override
    synchronized protected boolean isFinished() {
        return (turnController!=null && turnController.onTarget(kToleranceDegrees)) ||  isCentered;
    }

    @Override
    synchronized protected void end() {
        if (turnController!=null) {
            turnController = null;
        }
    }

    @Override
    synchronized protected void interrupted() {
        end();
    }


}
