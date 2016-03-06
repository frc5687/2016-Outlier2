package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.Constants;

import static org.usfirst.frc.team5687.robot.Robot.shooter;

/**
 * Command to set speed of shooter motor
 * @author wil
 */
public class ToggleShooter extends Command {
    private boolean _on;
    private long endTime;

    public ToggleShooter(boolean on) {
        requires(shooter);
        _on = on;
    }


    @Override
    protected void initialize() {
        endTime = System.currentTimeMillis() + Constants.Shooter.PRIME_TIME;
    }

    @Override
    protected void execute() {
        shooter.toggle(_on);
    }

    @Override
    protected boolean isFinished() {
        return !_on || System.currentTimeMillis() > endTime;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }
}
