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

    private static final double ANGLE_DEADBAND = 0.5;
    private static final double DISTANCE_DEADBAND = 4.0;


    private double targetDistance = Constants.Target.WIDTH;

    private boolean centered = false;
    private boolean inRange = false;

    boolean sighted = false;

    public AutoDetectTarget() {
    }

    @Override
    protected void initialize() {
        centered = false;
        inRange = false;

        DriverStation.reportError("Starting AutoDetectTarget to offsetAngle between " + (0-ANGLE_DEADBAND) + " and " + (ANGLE_DEADBAND) + ",  distance between " + (Constants.Target.SHOOTING_DISTANCE - DISTANCE_DEADBAND) + " and " + (Constants.Target.SHOOTING_DISTANCE + DISTANCE_DEADBAND) , false);
        lights.turnRingLightOn();
    }

    @Override
    protected void execute() {
        sighted = pitracker.getBoolean("TargetSighted", false);
        double offsetAngle = pitracker.getNumber("offsetAngle", 0);
        double distance = pitracker.getNumber("distance",0);

        centered = sighted && Math.abs(offsetAngle) < ANGLE_DEADBAND;

        inRange = sighted && Math.abs(distance - Constants.Target.SHOOTING_DISTANCE) < DISTANCE_DEADBAND;

        SmartDashboard.putNumber("AutoDetectTarget/offsetAngle", offsetAngle);
        SmartDashboard.putBoolean("AutoDetectTarget/centered", centered);

        SmartDashboard.putNumber("AutoDetectTarget/distance", distance);
        SmartDashboard.putBoolean("AutoDetectTarget/inrange", inRange);

        DriverStation.reportError("AutoDetectTarget: offsetAngle=" + offsetAngle + " distance=" + distance, false);
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
