package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static org.usfirst.frc.team5687.robot.Robot.*;

/**
 * Created by Ben Bernard on 4/12/2016.
 */
public class AutoApproachTarget extends Command implements PIDSource, PIDOutput {

    private double targetWidth = 160;
    private double lowWidth = 40;
    private boolean inRange = false;


    private double width = 0;
    private double runSpeed = .6;

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

        pidController = new PIDController(0,0,0,0,this,this);
        pidController.setSetpoint(0);
    }

    @Override
    protected void execute() {
        double leftSpeed = speed;
        double rightSpeed = speed;

        DriverStation.reportError("AutoApproachTarget: Width=" + width + " LeftSpeed=" + leftSpeed + " RightSpeed=" + rightSpeed, false);
        driveTrain.tankDrive(leftSpeed, rightSpeed);

    }

    @Override
    protected boolean isFinished() {
        return inRange;
    }

    @Override
    protected void end() {
        driveTrain.tankDrive(0, 0);
    }

    @Override
    protected void interrupted() {
        end();
    }

    @Override
    public void pidWrite(double output) {

        speed = width<=lowWidth ? -1 * runSpeed : output;

        SmartDashboard.putNumber("AutoApproachTarget/speed", speed);
        SmartDashboard.putString("AutoApproachTarget/approaching", speed < 0 ? "Moving out" : speed > 0 ? "Moving ing" : "In Range");
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
        sighted = pitracker.getBoolean("TargetSighted", false);
        width = pitracker.getNumber("width", 0);
        double offset = 0;

        if (sighted) {
            offset = targetWidth - width;
        } else {
            offset = 0;
        }

        inRange = sighted && Math.abs(offset)<1;

        SmartDashboard.putNumber("AutoApproachTarget/offset", offset);
        SmartDashboard.putBoolean("AutoApproachTarget/inrange", inRange);
        return offset;
    }
}
