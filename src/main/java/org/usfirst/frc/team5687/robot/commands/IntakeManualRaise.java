package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.Robot;
import org.usfirst.frc.team5687.robot.subsystems.IntakeLifter;

/**
 * Manual command to raise intake arm while held
 * @author wil
 */
public class IntakeManualRaise extends Command{
    IntakeLifter intakeLifter = Robot.intakeLifter;

    public IntakeManualRaise() {
        requires(intakeLifter);
    }

    @Override
    protected void initialize() {
        intakeLifter.initUpperCounter();
    }

    @Override
    protected void execute() {
        intakeLifter.raise();
    }

    @Override
    protected boolean isFinished() {
        return intakeLifter.isUpperCounterSet() || intakeLifter.isAtUpperLimit();
    }

    @Override
    protected void end() {
        intakeLifter.stop();
    }

    @Override
    protected void interrupted() {
        end();
    }
}
