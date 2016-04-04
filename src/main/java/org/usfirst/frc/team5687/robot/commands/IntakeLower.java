package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.Robot;
import org.usfirst.frc.team5687.robot.subsystems.IntakeLifter;

/**
 * Command to lower intake extension
 * @author wil
 */
public class IntakeLower extends Command {
    IntakeLifter lifter = Robot.intakeLifter;

    public IntakeLower() {
        requires(lifter);
    }

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
