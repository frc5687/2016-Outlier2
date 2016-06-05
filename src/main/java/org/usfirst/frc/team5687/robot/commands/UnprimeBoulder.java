package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.Constants;
import org.usfirst.frc.team5687.robot.LEDColors;
import org.usfirst.frc.team5687.robot.utils.Color;

import static org.usfirst.frc.team5687.robot.Robot.intake;
import static org.usfirst.frc.team5687.robot.Robot.ledStrip;
import static org.usfirst.frc.team5687.robot.Robot.lights;

/**
 * Command to unprime the intake or move the boulder back to captured position
 * @author wil
 */
public class UnprimeBoulder extends Command{
    private long endTime;

    public UnprimeBoulder() {
        requires(intake);
    }

    @Override
    protected void initialize() {
        endTime = System.currentTimeMillis() + Constants.Shooter.UNPRIME_TIME;
    }

    @Override
    protected void execute() {
        intake.setSpeed(Constants.Intake.UNPRIME_SPEED);
    }

    @Override
    protected boolean isFinished() {
        boolean isTimeOut = System.currentTimeMillis() > endTime;
        return isTimeOut || intake.isCaptured();
    }

    @Override
    protected void end() {
        intake.stop();
        lights.turnRingLightOff();
        lights.turnFlashlightOff();
        if (intake.isCaptured()) {
            ledStrip.setStripColor(LEDColors.CAPTURED);
        } else {
            ledStrip.setStripColor(LEDColors.TELEOP);
        }
    }

    @Override
    protected void interrupted() {
        end();
    }
}
