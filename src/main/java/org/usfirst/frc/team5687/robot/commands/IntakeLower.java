package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.Constants;
import org.usfirst.frc.team5687.robot.Robot;
import org.usfirst.frc.team5687.robot.subsystems.IntakeLifter;

/**
 * Command to lower intake extension
 * @author wil
 */
public class IntakeLower extends Command {
    IntakeLifter lifter = Robot.intakeLifter;
    private long endTime;

    public IntakeLower() {
        requires(lifter);
    }

    @Override
    protected void initialize() {
        endTime = System.currentTimeMillis() + Constants.IntakeLifter.LIFT_TIME;
    }

    @Override
    protected void execute() {
        lifter.lower();
    }

    @Override
    protected boolean isFinished() {
        boolean isTimeOut = System.currentTimeMillis() > endTime;
        return lifter.isAtLowerLimit() || isTimeOut;
    }

    @Override
    protected void end() {
        lifter.stop();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
