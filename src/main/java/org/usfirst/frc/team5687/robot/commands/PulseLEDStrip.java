package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5687.robot.utils.Color;


import static org.usfirst.frc.team5687.robot.Robot.ledStrip;

/**
 * Command to turn flashlight on
 * @author wil
 */
public class PulseLEDStrip extends Command {

    private Color _aColor;
    private Color _bColor;
    private double _period;
    private long _startMillis = 0;

    private double rRange;
    private double gRange;
    private double bRange;

    public PulseLEDStrip(Color aColor, Color bColor, long period) {
        requires(ledStrip);
        _aColor=aColor;
        _bColor=bColor;
        _period=period;

        rRange = bColor.getRed() - aColor.getRed();
        gRange = bColor.getGreen() - aColor.getGreen();
        bRange = bColor.getBlue() - aColor.getBlue();
    }

    @Override
    protected void initialize() {
        _startMillis = System.currentTimeMillis();
    }

    @Override
    protected void execute() {
        // Figure time...
        double offset = System.currentTimeMillis() - _startMillis;

        double factor = .5 + (Math.sin(((offset % _period) / _period) * Math.PI * 2))/2;

        double red = _aColor.getRed() + factor * rRange;
        double green = _aColor.getGreen() + factor * gRange;
        double blue = _aColor.getBlue() + factor * bRange;

        ledStrip.setStripColor((int) red, (int) green, (int) blue);

    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
        end();
    }
}
