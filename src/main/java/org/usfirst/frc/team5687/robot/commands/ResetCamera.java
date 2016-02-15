package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5687.robot.OI;

import static org.usfirst.frc.team5687.robot.Robot.oi;
import static org.usfirst.frc.team5687.robot.Robot.robot;

/**
 * Command for reversing the drive's direction
 * @author wil
 */
public class ResetCamera extends Command{

    boolean executed = false;

    @Override
    protected void initialize() {
        executed = false;
    }

    @Override
    protected void execute() {
        robot.initializeCameras();
        executed = true;
    }

    @Override
    protected boolean isFinished() {
        return executed;
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {

    }
}
