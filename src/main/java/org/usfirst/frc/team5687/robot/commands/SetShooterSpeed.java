package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import static org.usfirst.frc.team5687.robot.Robot.shooter;
import static org.usfirst.frc.team5687.robot.Robot.intake;

/**
 * Command to set speed of shooter motor
 * @author wil
 */
public class SetShooterSpeed extends Command {
    private double speed;
    private long time;
    private long endTime;
    private boolean isToFire;

    /**
     * Constructor for SetShooterSpeed
     * @param speed motor speed to run the shooter
     * @param time duration of time to run the shooter at speed
     */
    public SetShooterSpeed(double speed, long time) {
        requires(shooter);
        this.speed = speed;
        this.time = time;
    }

    /**
     * Constructor SetShooterSpeed for Fire command
     * @param speed motor speed to run the shooter
     */
    public SetShooterSpeed(double speed) {
        requires(shooter);
        this.speed = speed;
        isToFire = true;
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
        if (isToFire) {
            return !intake.isPrimed();
        } else {
            return System.currentTimeMillis() > endTime;
        }
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }
}
