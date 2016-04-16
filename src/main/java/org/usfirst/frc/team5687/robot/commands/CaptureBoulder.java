package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.Constants;
import static org.usfirst.frc.team5687.robot.Robot.intake;
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
        intake.setSpeed(Constants.Intake.CAPTURE_SPEED);
    }

    @Override
    protected boolean isFinished() {
        return intake.isCaptured();
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
