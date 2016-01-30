package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import static org.usfirst.frc.team5687.robot.Robot.oi;

/**
 * Command for reversing the drive's direction
 * @author wil
 * @since 1/29/16
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
