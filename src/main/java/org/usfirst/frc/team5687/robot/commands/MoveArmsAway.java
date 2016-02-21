package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.Robot;
import org.usfirst.frc.team5687.robot.subsystems.Arms;

/**
 * Command to move arms to stored position
 * @author wil
 */
public class MoveArmsAway extends Command{
    Arms arms = Robot.arms;

    public MoveArmsAway() {
        requires(arms);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        arms.moveUp();
    }

    @Override
    protected boolean isFinished() {
        return arms.isAtUpperLimit();
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
