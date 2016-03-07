package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import static org.usfirst.frc.team5687.robot.Robot.shooter;

/**
 * Command to set speed of shooter motor
 * @author wil
 */
public class SetShooterSpeed extends Command {
    private double _speed;

    public SetShooterSpeed(double speed) {
        requires(shooter);
        _speed = speed;
    }


    @Override
    protected void initialize() {
        shooter.setSpeed(_speed);
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }
}
