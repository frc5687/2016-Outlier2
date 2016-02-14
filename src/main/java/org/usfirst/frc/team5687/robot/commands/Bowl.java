package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.Robot;
import org.usfirst.frc.team5687.robot.subsystems.Intake;

import java.util.Date;

/**
 * Command for bowling the boulder into a low goal
 * @author wil
 */
public class Bowl extends Command{
    Intake intake = Robot.intake;
    private long endTime;

    public Bowl() {

    }

    @Override
    protected void initialize() {
        intake = Robot.intake;
        endTime = System.currentTimeMillis()+1000;
    }

    @Override
    protected void execute() {
        intake.setSpeed(-1);
    }

    @Override
    protected boolean isFinished() {
        return System.currentTimeMillis()>endTime;
    }

    @Override
    protected void end() {
        intake.stop();
    }

    @Override
    protected void interrupted() {

    }
}
