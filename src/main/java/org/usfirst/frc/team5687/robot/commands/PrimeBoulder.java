package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.Constants;

import static org.usfirst.frc.team5687.robot.Robot.camera;
import static org.usfirst.frc.team5687.robot.Robot.intake;

/**
 * Command to prime the intake or move the boulder back to primed position
 * @author wil
 */
public class PrimeBoulder extends Command {

    public PrimeBoulder() {
        requires(intake);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        intake.setSpeed(Constants.Intake.PRIME_SPEED);
        camera.setTrack(true);
    }

    @Override
    protected boolean isFinished() {
        return intake.isPrimed();
    }

    @Override
    protected void end() {
        intake.stop();
    }

    @Override
    protected void interrupted() {
    }
}
