package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5687.robot.Constants;

import static org.usfirst.frc.team5687.robot.Robot.*;

/**
 * Created by Ben Bernard on 4/12/2016.
 */
public class AutoDetectTarget extends Command {

    private double deadbandX = 5;
    private double deadbandWidth = 5;


    private double targetX = Constants.Target.X;
    private double targetWidth = Constants.Target.WIDTH;

    private double minX;
    private double maxX;

    private double minWidth;
    private double maxWidth;

    private boolean centered = false;
    private boolean inRange = false;

    private double centerX = 0;
    private double width = 0;

    boolean sighted = false;

    public AutoDetectTarget() {
    }

    @Override
    protected void initialize() {
        centered = false;
        inRange = false;

        minX = targetX - deadbandX;
        maxX = targetX + deadbandX;

        minWidth = width - deadbandWidth;
        maxWidth = width + deadbandWidth;

        DriverStation.reportError("Starting AutoDetectTarget to centerX between " + minX + " and " + maxX + ",  width between " + minWidth + " and " + maxWidth , false);
        lights.turnRingLightOn();
    }

    @Override
    protected void execute() {
        sighted = pitracker.getBoolean("TargetSighted", false);
        centerX = pitracker.getNumber("centerX", 0);
        width = pitracker.getNumber("width", 0);

        centered = sighted && centerX >= minX && centerX <=maxX;
        inRange = sighted && width >= minWidth && width <= maxWidth;

        SmartDashboard.putNumber("AutoDetectTarget/centerX", centerX);
        SmartDashboard.putBoolean("AutoDetectTarget/centered", centered);

        SmartDashboard.putNumber("AutoDetectTarget/width", width);
        SmartDashboard.putBoolean("AutoDetectTarget/inrange", inRange);

        DriverStation.reportError("AutoDetctTarget: Center=" + centerX + " Width=" + width + " (looking for " + minX + " to " + maxX + " and " + minWidth + " to " + maxWidth + ")", false);
    }

    @Override
    protected boolean isFinished() {

        return centered && inRange;
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {
        end();
    }

}
