package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5687.robot.Constants;

import static org.usfirst.frc.team5687.robot.Robot.*;

/**
 * Created by Ben Bernard on 4/12/2016.
 */
public class AutoCenterTarget extends Command implements PIDSource, PIDOutput {
    private static final double kP = 0.03f;
    private static final double kI = 0.001f;
    private static final double kD = 0.02f;
    private static final double kF = 0.0f;
    private static final double kTolerance = 2.0f;


    private double targetX = Constants.Target.X;

    private boolean centered = false;

    private double centerX = 0;
    private double twistSpeed = .5;

    private double twist = 0;

    private PIDController pidController = null;

    public AutoCenterTarget() {
        requires(driveTrain);
    }
    @Override
    protected void initialize() {
        centered = false;

        double centerX = pitracker.getNumber("centerX", 0);

        boolean sighted = pitracker.getBoolean("TargetSighted", false);

        DriverStation.reportError("Starting AutoCenterTarget to centerX=" + targetX, false);
        lights.turnRingLightOn();

        pidController = new PIDController(kP, kI, kD, kF, this, this);
        pidController.setInputRange(-200.0f,  200.0f);
        pidController.setOutputRange(-1*twistSpeed, twistSpeed);
        pidController.setAbsoluteTolerance(kTolerance);
        pidController.setContinuous(false);
        pidController.setSetpoint(0);
        pidController.enable();
        SmartDashboard.putData("CenterPID", pidController);
    }

    @Override
    protected void execute() {
        synchronized (this) {
            double leftSpeed = -1 * twist;
            double rightSpeed = twist;
            driveTrain.tankDrive(leftSpeed, rightSpeed, true);
            DriverStation.reportError("AutoCenterTarget driving " + leftSpeed + ", " + rightSpeed, false);
        }
    }

    @Override
    protected boolean isFinished() {
        return centered;
    }

    @Override
    protected void end() {
        DriverStation.reportError("AutoCenterTarget complete at " + centerX, false);
        pidController.disable();
        driveTrain.tankDrive(0, 0);
    }

    @Override
    protected void interrupted() {
        end();
    }

    @Override
    public void pidWrite(double output) {
        DriverStation.reportError("AutoCenterTarget pidWrite " + output, false);
        synchronized (this) {
            twist = -1 * output;

            SmartDashboard.putNumber("AutoCenterTarget/twist", twist);
            SmartDashboard.putString("AutoCenterTarget/centering", twist < 0 ? "Turning left" : twist > 0 ? "Turning right" : "Centered");
        }
    }

    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {

    }

    @Override
    public PIDSourceType getPIDSourceType() {
        return PIDSourceType.kDisplacement;
    }

    @Override
    public double pidGet() {
        synchronized (this) {
            // read pitracker
            boolean sighted = pitracker.getBoolean("TargetSighted", true);
            centerX = pitracker.getNumber("centerX", 0);
            double offset = 0;

            if (sighted) {
                offset = centerX - targetX;
            } else {
                offset = 0;
            }

            centered = sighted && pidController.onTarget();

            SmartDashboard.putNumber("AutoCenterTarget/offset", offset);
            SmartDashboard.putBoolean("AutoCenterTarget/centered", centered);

            DriverStation.reportError("AutoCenterTarget pidGet " + offset, false);

            return offset;
        }
    }
}
