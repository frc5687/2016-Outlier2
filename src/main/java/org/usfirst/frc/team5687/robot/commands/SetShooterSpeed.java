package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.usfirst.frc.team5687.robot.Constants;

import static org.usfirst.frc.team5687.robot.Robot.shooter;

/**
 * Command to set speed of shooter motor
 * @author wil
 */
public class SetShooterSpeed extends Command {
    private double speed;
    private long endTime;

    public SetShooterSpeed(double speed) {
        requires(shooter);
        this.speed = speed;
    }

    @Override
    protected void initialize() {
        endTime = System.currentTimeMillis() + Constants.Shooter.SHOOT_TIME;
    }

    @Override
    protected void execute() {
        shooter.setSpeed(speed);
    }

    @Override
    protected boolean isFinished() {
        return System.currentTimeMillis() > endTime;
    }

    @Override
    protected void end() {
        shooter.stop();
    }

    @Override
    protected void interrupted() {
    }
}
