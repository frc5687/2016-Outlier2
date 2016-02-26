package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.Constants;

import static org.usfirst.frc.team5687.robot.Robot.camera;
import static org.usfirst.frc.team5687.robot.Robot.intake;

/**
 * Command to fire the boulder from the intake to shooter wheels
 * @author wil
 */
public class FireBoulder extends Command {

    public FireBoulder() {
        requires(intake);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        intake.setSpeed(Constants.Intake.FIRE_SPEED);
    }

    @Override
    protected boolean isFinished() {
        return !intake.isPrimed();
    }

    @Override
    protected void end() {
        intake.stop();
        camera.setTrack(false);
    }

    @Override
    protected void interrupted() {
    }
}
