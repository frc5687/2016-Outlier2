package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.OI;
import org.usfirst.frc.team5687.robot.Robot;
import org.usfirst.frc.team5687.robot.subsystems.Climber;

/**
 * Command to extend the tape measure up for climbing
 * @author wil
 */
public class ClimbManually extends Command {
    Climber climber = Robot.climber;
    OI oi = Robot.oi;

    public ClimbManually() {
        requires(climber);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        climber.setSpeed(oi.getClimberSpeed());
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
