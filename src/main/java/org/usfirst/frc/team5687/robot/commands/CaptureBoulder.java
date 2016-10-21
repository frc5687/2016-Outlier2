package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.Constants;
import org.usfirst.frc.team5687.robot.LEDColors;
import org.usfirst.frc.team5687.robot.utils.*;
import org.usfirst.frc.team5687.robot.utils.Color;

import java.awt.*;

import static org.usfirst.frc.team5687.robot.Robot.*;

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
        ledStrip.setStripColor(LEDColors.INTAKE);
        intake.setSpeed(Constants.Intake.CAPTURE_SPEED);
    }

    @Override
    protected boolean isFinished() {
        if (intake.isCaptured()) {
            ledStrip.setStripColor(LEDColors.CAPTURED);
            return true;
        };
        return false;
    }

    @Override
    protected void end() {
        if (!intake.isCaptured()) {
            ledStrip.setStripColor(LEDColors.TELEOP);
        }
        intake.stop();
        oi.rumble(.3f,500);
    }

    @Override
    protected void interrupted() {
        end();
    }
}
