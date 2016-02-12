package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.OI;
import org.usfirst.frc.team5687.robot.Robot;
import org.usfirst.frc.team5687.robot.subsystems.Arms;

/**
 * Created by John on 2/10/2016.
 */
public class RunArmsManually extends Command {
    Arms arms = Robot.arms;
    OI oi = Robot.oi;

    public RunArmsManually() {
        requires(arms);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        arms.setSpeed(oi.getArmSpeed());
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }
}
