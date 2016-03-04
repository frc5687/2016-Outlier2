package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import static org.usfirst.frc.team5687.robot.Robot.intake;
import static org.usfirst.frc.team5687.robot.Robot.shooter;

/**
 * Command to try to suckset speed of shooter motor
 * @author wil
 */
public class RecoverBoulder extends Command {
    private static final double speed = -0.8f;
    private static final long time = 5000;
    private long endTime;

    /**
     * Constructor for RecoverBoulder
     */
    public RecoverBoulder() {
        requires(shooter);
    }

    @Override
    protected void initialize() {
        endTime = System.currentTimeMillis() + time;
    }

    @Override
    protected void execute() {
        shooter.setSpeed(speed);
    }

    @Override
    protected boolean isFinished() {
        return /*intake.isCaptured() ||*/ System.currentTimeMillis() > endTime;
    }

    @Override
    protected void end() {
        shooter.stop();
    }

    @Override
    protected void interrupted() {
    }
}
