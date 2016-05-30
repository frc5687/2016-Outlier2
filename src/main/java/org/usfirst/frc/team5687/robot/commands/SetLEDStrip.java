package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.utils.Color;

import static org.usfirst.frc.team5687.robot.Robot.ledStrip;

/**
 * Command to turn flashlight on
 * @author wil
 */
public class SetLEDStrip extends Command {

    private Color _color;


    public SetLEDStrip(Color color) {
        requires(ledStrip);
        _color=color;
    }

    @Override
    protected void initialize() {
        // ledStrip.setStripColor(_color);
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
        end();
    }
}
