package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.Constants;
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
        ledStrip.setStripColor(Color.ORANGE);
        intake.setSpeed(Constants.Intake.PRIME_SPEED);
    }

    @Override
    protected boolean isFinished() {
        if (intake.isPrimed()) {
            ledStrip.setStripColor(Color.RED);
            return true;
        };
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
