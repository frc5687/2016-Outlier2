package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.Robot;
import org.usfirst.frc.team5687.robot.subsystems.Intake;

/**
 * Command for bowling the boulder into a low goal
 * @author wil
 */
public class Bowl extends Command{
    Intake intake = Robot.intake;

    public Bowl() {
        requires(intake);
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        intake.setSpeed(-1);
    }

    @Override
    protected boolean isFinished() {
        // return Intake.hasNoBoulder();
        return false;
    }

    @Override
    protected void end() {
        intake.stop();
    }

    @Override
    protected void interrupted() {

    }
}
