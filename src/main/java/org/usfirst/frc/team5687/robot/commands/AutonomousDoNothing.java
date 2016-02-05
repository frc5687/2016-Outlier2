package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This class provides a degenerate autonomous mode during which we can still log and track data by adding to the execute() method
 *
 * Created by Ben Bernard on 2/5/2016.
 */
public class AutonomousDoNothing extends Command {

    @Override
    protected void initialize() {

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
