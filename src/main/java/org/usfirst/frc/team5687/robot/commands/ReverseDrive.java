package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static org.usfirst.frc.team5687.robot.Robot.oi;
import static org.usfirst.frc.team5687.robot.Robot.robot;

/**
 * Command for reversing the drive's direction
 * @author wil
 * @since 1/29/16
 */
public class ReverseDrive extends Command{
    private int direction;
    private int prevDirection;
    private final int FORWARD_DIRECTION = 1;
    private final int REVERSE_DIRECTION = -1;

    @Override
    protected void initialize() {
        direction = oi.getDirection();
        prevDirection = direction;

        direction *= -1;
        oi.setDirection(direction);

        //disabling cameraSwitching for now
        //robot.switchCameras();

        SmartDashboard.putString("DriveIsFacing", (direction == FORWARD_DIRECTION) ? "electronics" : "intake");
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
