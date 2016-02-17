package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.Constants;

import static org.usfirst.frc.team5687.robot.Robot.intake;

/**
 * Command to set speed of intake motor
 * @author wil
 */
public class SetIntakeSpeed extends Command {
    private double speed;
    private long time;
    private long endTime;

    /**
     * Constructor for SetIntakeSpeed
     * @param speed motor speed to run the intake
     * @param time duration of time to run the intake
     */
    public SetIntakeSpeed(double speed, long time) {
        requires(intake);
        this.speed = speed;
        this.time = time;
    }

    @Override
    protected void initialize() {
        endTime = System.currentTimeMillis() + time;
    }

    @Override
    protected void execute() {
        intake.setSpeed(speed);
    }

    @Override
    protected boolean isFinished() {
        return System.currentTimeMillis() > endTime;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }
}
