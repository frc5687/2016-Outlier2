package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.OI;
import org.usfirst.frc.team5687.robot.Robot;
import org.usfirst.frc.team5687.robot.subsystems.Boulder;

/**
 * Command for basic manual-control of the shooter wheels
 * Created by Ryan on 2/7/16.
 */
public class RunShooterManually extends Command {
    Boulder boulder = Robot.boulder;
    OI oi = Robot.oi;

    /**
     * Constructor
     */
    public RunShooterManually() {
        requires(boulder);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        boulder.setShooterSpeed(oi.getShooterSpeed());
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
