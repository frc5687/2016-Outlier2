package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.Constants;
import org.usfirst.frc.team5687.robot.LEDColors;
import org.usfirst.frc.team5687.robot.utils.*;
import org.usfirst.frc.team5687.robot.utils.Color;

import java.awt.*;

import static org.usfirst.frc.team5687.robot.Robot.intake;
import static org.usfirst.frc.team5687.robot.Robot.ledStrip;
import static org.usfirst.frc.team5687.robot.Robot.lights;

/**
 * Command to prime the intake or move the boulder back to primed position
 * @author wil
 */
public class PrimeBoulder extends Command {

    public PrimeBoulder() {
        requires(intake);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        ledStrip.setStripColor(LEDColors.PRIMING);
        intake.setSpeed(Constants.Intake.PRIME_SPEED);
    }

    @Override
    protected boolean isFinished() {
        return intake.isPrimed();
    }

    @Override
    protected void end() {
        ledStrip.setStripColor(LEDColors.PRIMED);
        intake.stop();
    }

    @Override
    protected void interrupted() {
        end();
    }
}
