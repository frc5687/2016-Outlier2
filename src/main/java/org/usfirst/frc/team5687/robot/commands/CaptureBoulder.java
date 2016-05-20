package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.Constants;

import java.awt.*;

import static org.usfirst.frc.team5687.robot.Robot.intake;
import static org.usfirst.frc.team5687.robot.Robot.lights;
import static org.usfirst.frc.team5687.robot.Robot.oi;

/**
 * Command to run intake motor until a boulder is detected
 */
public class CaptureBoulder extends Command {

    public CaptureBoulder() {
        requires(intake);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        lights.setStripColor(0,0,255);
        intake.setSpeed(Constants.Intake.CAPTURE_SPEED);
    }

    @Override
    protected boolean isFinished() {
        if (intake.isCaptured()) {
            lights.setStripColor(0,255,255);
            return true;
        };
        return false;
    }

    @Override
    protected void end() {
        intake.stop();
        oi.rumble(.3f,500);
    }

    @Override
    protected void interrupted() {
        end();
    }
}
