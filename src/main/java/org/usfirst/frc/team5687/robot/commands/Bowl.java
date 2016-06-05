package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.Constants;
import org.usfirst.frc.team5687.robot.LEDColors;
import org.usfirst.frc.team5687.robot.utils.Color;

import static org.usfirst.frc.team5687.robot.Robot.intake;
import static org.usfirst.frc.team5687.robot.Robot.ledStrip;

import java.util.Date;

/**
 * Command for bowling the boulder into a low goal
 * @author wil
 */
public class Bowl extends Command{
    private long endTime;

    public Bowl() {
        requires(intake);
    }

    @Override
    protected void initialize() {
        ledStrip.setStripColor(LEDColors.BOWLING);
        endTime = System.currentTimeMillis()+ Constants.Intake.BOWL_TIME;
    }

    @Override
    protected void execute() {
        intake.setSpeed(Constants.Intake.BOWL_SPEED);
    }

    @Override
    protected boolean isFinished() {
        return System.currentTimeMillis()>endTime;
    }

    @Override
    protected void end() {
        intake.stop();
        ledStrip.setStripColor(LEDColors.TELEOP);
    }

    @Override
    protected void interrupted() {
        end();
    }
}
