package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.OI;
import org.usfirst.frc.team5687.robot.Robot;
import org.usfirst.frc.team5687.robot.subsystems.Shooter;

/**
 * Command for basic manual-control of the shooter wheels
 * Created by Ryan on 2/7/16.
 */
public class RunShooterManually extends Command {
    Shooter shooter = Robot.shooter;
    OI oi = Robot.oi;

    /**
     * Constructor
     */
    public RunShooterManually() {
        requires(shooter);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        shooter.setSpeed(oi.getShooterSpeed());
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
