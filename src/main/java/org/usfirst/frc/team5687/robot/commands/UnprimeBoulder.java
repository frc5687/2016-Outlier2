package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.Constants;

import static org.usfirst.frc.team5687.robot.Robot.intake;

/**
 * Command to unprime the intake or move the boulder back to captured position
 * @author wil
 */
public class UnprimeBoulder extends Command{

    public UnprimeBoulder() {
        requires(intake);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        intake.setSpeed(Constants.Intake.UNPRIME_SPEED);
    }

    @Override
    protected boolean isFinished() {
        return intake.isCaptured();
    }

    @Override
    protected void end() {
        intake.stop();
    }

    @Override
    protected void interrupted() {
    }
}
