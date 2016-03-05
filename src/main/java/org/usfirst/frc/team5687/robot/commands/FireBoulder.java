package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.Constants;

import static org.usfirst.frc.team5687.robot.Robot.intake;

/**
 * Command to fire the boulder from the intake to shooter wheels
 * @author wil
 */
public class FireBoulder extends Command {
    private long endTime;


    public FireBoulder() {
        requires(intake);
    }

    @Override
    protected void initialize() {
        endTime = System.currentTimeMillis() + Constants.Shooter.UNPRIME_TIME;
    }

    @Override
    protected void execute() {
        intake.setSpeed(Constants.Intake.FIRE_SPEED);
    }

    @Override
    protected boolean isFinished() {
        return !intake.isPrimed() && System.currentTimeMillis() > endTime;
    }

    @Override
    protected void end() {
        intake.stop();
    }

    @Override
    protected void interrupted() {
    }
}
