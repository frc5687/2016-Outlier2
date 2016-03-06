package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import static org.usfirst.frc.team5687.robot.Robot.shooter;

/**
 * Command to try to suckset speed of shooter motor
 * @author wil
 */
public class RecoverBoulder extends Command {
    private static final double speed = -1f;
    private static final long time = 40;
    private long endTime;
    private double previous;
    /**
     * Constructor for RecoverBoulder
     */
    public RecoverBoulder() {
        requires(shooter);
    }

    @Override
    protected void initialize() {
        previous = shooter.getSpeed();
        endTime = System.currentTimeMillis() + time;
        shooter.setSpeed(speed);
    }

    @Override
    protected void execute() {
        shooter.toggle(true);
    }

    @Override
    protected boolean isFinished() {
        return System.currentTimeMillis() > endTime;
    }

    @Override
    protected void end() {
        shooter.toggle(false);
        shooter.setSpeed(previous);
    }

    @Override
    protected void interrupted() {
        end();
    }
}
