package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.OI;
import org.usfirst.frc.team5687.robot.Robot;
import org.usfirst.frc.team5687.robot.subsystems.IntakeLifter;

/**
 * Command to control intake lifter
 * @author wil
 */
public class PositionIntake extends Command {
    IntakeLifter intakeLifter = Robot.intakeLifter;
    OI oi = Robot.oi;

    public PositionIntake() {
        requires(intakeLifter);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        intakeLifter.setSpeed(oi.getIntakeLifterSpeed());
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }
}
