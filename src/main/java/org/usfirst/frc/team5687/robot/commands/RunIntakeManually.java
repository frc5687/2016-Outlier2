package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.OI;
import org.usfirst.frc.team5687.robot.Robot;
import org.usfirst.frc.team5687.robot.subsystems.Intake;

/**
 * Command for basic manual control of the boulder intake
 * @author wil
 */
public class RunIntakeManually extends Command {
    Intake intake = Robot.intake;
    OI oi = Robot.oi;

    public RunIntakeManually() {
        requires(intake);
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        intake.setSpeed(oi.getIntakeSpeed());
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
