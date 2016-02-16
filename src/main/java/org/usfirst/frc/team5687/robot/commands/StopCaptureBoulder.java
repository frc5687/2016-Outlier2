package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import static org.usfirst.frc.team5687.robot.Robot.intake;

/**
 * Command to abort running the intake motor
 */
public class StopCaptureBoulder extends Command {

    public StopCaptureBoulder() {
        super("StopIntake");
        requires(intake);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        intake.stop();
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
