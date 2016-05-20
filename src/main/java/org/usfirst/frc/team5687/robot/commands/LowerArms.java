package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.Constants;

import static org.usfirst.frc.team5687.robot.Robot.arms;
import static org.usfirst.frc.team5687.robot.Robot.intake;

/**
 * Command to lower the arms
 * @author wil
 */
public class LowerArms extends Command {
    private long endTime;

    public LowerArms() {
        requires(intake);
    }

    @Override
    protected void initialize() {
        endTime = System.currentTimeMillis() + Constants.Arms.TIMEOUT;
    }

    @Override
    protected void execute() {
        arms.moveDown();
    }

    @Override
    protected boolean isFinished() {
        boolean hasTimedOut = System.currentTimeMillis() > endTime;
        return arms.isBelowLimit() || hasTimedOut;
    }

    @Override
    protected void end() {
        arms.stop();
    }

    @Override
    protected void interrupted() {
        end();
    }
}
