package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import static org.usfirst.frc.team5687.robot.Robot.intake;

/**
 * Command to abort a running command requiring the intake subsystem,
 * including the CaptureBoulder command.
 */
public class StopCaptureBoulder extends Command {

    private Boolean finished = false;

    public StopCaptureBoulder() {
        requires(intake);
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        finished = true;
    }

    @Override
    protected boolean isFinished() {
        return finished;
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {
        finished = true;
    }
}
