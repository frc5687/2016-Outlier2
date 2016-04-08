package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.Robot;
import org.usfirst.frc.team5687.robot.subsystems.IntakeLifter;

/**
 * Manual command to lower intake arm while held
 * @author wil
 */
public class IntakeManualLower extends Command{
    IntakeLifter intakeLifter = Robot.intakeLifter;

    public IntakeManualLower() {
        requires(intakeLifter);
    }

    @Override
    protected void initialize() {
        intakeLifter.initLowerCounter();
    }

    @Override
    protected void execute() {
        intakeLifter.lower();
    }

    @Override
    protected boolean isFinished() {
        return intakeLifter.isLowerCounterSet() || intakeLifter.isAtLowerLimit();
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
