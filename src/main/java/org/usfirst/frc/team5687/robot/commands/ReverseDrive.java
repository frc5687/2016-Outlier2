package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static org.usfirst.frc.team5687.robot.Robot.oi;
import static org.usfirst.frc.team5687.robot.Robot.robot;
import static org.usfirst.frc.team5687.robot.Robot.camera;

/**
 * Command for reversing the drive's direction
 * @author wil
 */
public class ReverseDrive extends Command{
    private int direction;
    private int prevDirection;

    @Override
    protected void initialize() {
        direction = oi.getDirection();
        prevDirection = direction;

        direction *= -1;
        oi.setDirection(direction);

        //disabling cameraSwitching for now
        camera.switchCameras(direction);

        SmartDashboard.putString("Drive Facing", (oi.getDirection()==-1) ? "Horns" : "Intake");
    }

    @Override
    protected void execute() {

    }

    @Override
    protected boolean isFinished() {
        return direction != prevDirection;
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {

    }
}
