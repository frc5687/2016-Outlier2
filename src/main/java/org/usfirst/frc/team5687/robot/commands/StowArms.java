package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.Constants;

import static org.usfirst.frc.team5687.robot.Robot.arms;
import static org.usfirst.frc.team5687.robot.Robot.intake;

/**
 * Command to retract the arms into stow position
 * @author wil
 */
public class StowArms extends Command {
    private long endTime;

    public StowArms() {
        requires(intake);
    }

    @Override
    protected void initialize() {
        endTime = System.currentTimeMillis() + Constants.Arms.TIMEOUT;
    }

    @Override
    protected void execute() {
        arms.moveUp();
    }

    @Override
    protected boolean isFinished() {
        boolean hasTimedOut = System.currentTimeMillis() > endTime;
        return arms.isAboveLimit() || hasTimedOut;
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
