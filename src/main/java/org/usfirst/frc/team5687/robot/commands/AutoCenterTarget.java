package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static org.usfirst.frc.team5687.robot.Robot.*;

/**
 * Created by Ben Bernard on 4/12/2016.
 */
public class AutoCenterTarget extends Command implements PIDSource, PIDOutput {

    private double targetX = -70;

    private boolean centered = false;

    private double centerX = 0;
    private double twistSpeed = .3;

    private double twist = 0;

    private PIDController pidController = null;

    public AutoCenterTarget() {
        requires(driveTrain);
    }
    @Override
    protected void initialize() {
        centered = false;
        targetX = pitrackerInputs.getNumber("TARGET_X", targetX);
        pitrackerInputs.putNumber("TARGET_X", targetX);

        double centerX = pitracker.getNumber("centerX", 0);
        pitracker.putNumber("centerX", centerX);

        boolean sighted = pitracker.getBoolean("TargetSighted", false);
        pitracker.putBoolean("TargetSighted", sighted);

        DriverStation.reportError("Starting AutoCenterTarget to centerX=" + targetX, false);
        lights.turnRingLightOn();

        pidController = new PIDController(0,0,0,0,this,this);
        pidController.setSetpoint(0);
    }

    @Override
    protected void execute() {
        double leftSpeed = -1 * twistSpeed;
        double rightSpeed = twistSpeed;

        DriverStation.reportError("AutoCenterTarget: Center=" + centerX + " LeftSpeed=" + leftSpeed + " RightSpeed=" + rightSpeed, false);
        driveTrain.tankDrive(leftSpeed, rightSpeed);

    }

    @Override
    protected boolean isFinished() {
        return centered;
    }

    @Override
    protected void end() {
        driveTrain.tankDrive(0, 0);
    }

    @Override
    protected void interrupted() {

    }

    @Override
    public void pidWrite(double output) {
        twist = output;

        SmartDashboard.putNumber("AutoCenterTarget/twist", twist);
        SmartDashboard.putString("AutoCenterTarget/centering", twist < 0 ? "Turning left" : twist > 0 ? "Turning right" : "Centered");
    }

    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {

    }

    @Override
    public PIDSourceType getPIDSourceType() {
        return null;
    }

    @Override
    public double pidGet() {
        // read pitracker
        boolean sighted = pitracker.getBoolean("TargetSighted", false);
        centerX = pitracker.getNumber("centerX", 0);
        double offset = 0;

        if (sighted) {
            offset = centerX - targetX;
        } else {
            offset = 0;
        }

        centered = sighted && Math.abs(offset)<1;

        SmartDashboard.putNumber("AutoCenterTarget/offset", offset);
        SmartDashboard.putBoolean("AutoCenterTarget/centered", centered);
        return offset;
    }
}
