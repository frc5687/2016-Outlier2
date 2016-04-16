package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5687.robot.Constants;

import static org.usfirst.frc.team5687.robot.Robot.*;

/**
 * Created by Ben Bernard on 4/12/2016.
 */
public class AutoApproachTarget extends Command implements PIDSource, PIDOutput {
    private static final double kP = 0.03;
    private static final double kI = 0.001;
    private static final double kD = 0.02;
    private static final double kF = 0.0;
    private static final double kTolerance = 2.0f;

    private double targetWidth = Constants.Target.WIDTH;
    private double lowWidth = Constants.Target.LOW_WIDTH;
    private boolean inRange = false;

    private double targetX = Constants.Target.X;
    private double centerX = 0;
    private double offsetX = 0;
    private double deadbandX = 2;
    private double centerTwist = 0.1;

    private double width = 0;
    private double runSpeed = .4;

    private double speed = 0;

    boolean sighted = false;

    private PIDController pidController = null;

    public AutoApproachTarget() {
        requires(driveTrain);
    }
    @Override
    protected void initialize() {
        inRange = false;
        targetWidth = pitrackerInputs.getNumber("TARGET_WIDTH", targetWidth);
        pitrackerInputs.putNumber("TARGET_WIDTH", targetWidth);

        double width = pitracker.getNumber("width", 0);
        pitracker.putNumber("width", width);

        boolean sighted = pitracker.getBoolean("TargetSighted", false);
        pitracker.putBoolean("TargetSighted", sighted);

        DriverStation.reportError("Starting AutoApproachTarget to width=" + targetWidth, false);
        lights.turnRingLightOn();

        pidController = new PIDController(kP, kI, kD, kF, this, this);
        pidController.setInputRange(-100.0f,  100.0f);
        pidController.setOutputRange(-1*runSpeed, runSpeed);
        pidController.setAbsoluteTolerance(kTolerance);
        pidController.setContinuous(false);
        pidController.setSetpoint(0);
        pidController.enable();
        SmartDashboard.putData("ApproachPID", pidController);
    }

    @Override
    protected void execute() {
        synchronized (this) {
            double twist = 0;
            if (centerX < targetX - deadbandX) {
                twist = centerTwist;
            } else if (centerX > targetX + deadbandX) {
                twist = -1* centerTwist;
            }
            SmartDashboard.putNumber("AutoApproachTarget/twist", twist);

            double leftSpeed = speed + twist*speed;
            double rightSpeed = speed - twist*speed;

            SmartDashboard.putNumber("AutoApproachTarget/leftSpeed", leftSpeed);
            SmartDashboard.putNumber("AutoApproachTarget/rightSpeed", rightSpeed);
            DriverStation.reportError("AutoApproachTarget driving " + leftSpeed + ", " + rightSpeed, false);
            driveTrain.tankDrive(leftSpeed, rightSpeed, true);
        }

    }

    @Override
    protected boolean isFinished() {
        return inRange;
    }

    @Override
    protected void end() {
        DriverStation.reportError("AutoApproachTarget complete at " + width + " and " + centerX, false);
        pidController.disable();
        driveTrain.tankDrive(0, 0);
    }

    @Override
    protected void interrupted() {
        end();
    }

    @Override
    public void pidWrite(double output) {
        DriverStation.reportError("AutoApproachTarget pidWrite " + output, false);
        synchronized (this) {
            speed = width <= lowWidth ? -1 * runSpeed : output;

            SmartDashboard.putNumber("AutoApproachTarget/speed", speed);
            SmartDashboard.putString("AutoApproachTarget/approaching", speed < 0 ? "Moving out" : speed > 0 ? "Moving ing" : "In Range");
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
        // read pitracker
        synchronized (this) {
            sighted = true; // pitracker.getBoolean("TargetSighted", false);
            width = pitracker.getNumber("width", 0);
            centerX = pitracker.getNumber("centerX", 0);
            offsetX = targetX - centerX;

            double offsetWidth = 0;

            if (sighted) {
                offsetWidth = width-targetWidth;
            } else {
                offsetWidth = 0;
            }

            inRange = sighted && pidController.onTarget();

            SmartDashboard.putNumber("AutoApproachTarget/offset", offsetWidth);
            SmartDashboard.putBoolean("AutoApproachTarget/inrange", inRange);
            SmartDashboard.putNumber("AutoApproachTarget/offsetX", offsetX);

            DriverStation.reportError("AutoApproachTarget pidget " + offsetWidth, false);
            return offsetWidth;
        }
    }
}
