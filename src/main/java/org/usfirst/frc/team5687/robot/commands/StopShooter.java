package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.LEDColors;
import org.usfirst.frc.team5687.robot.utils.Color;

import java.time.Instant;

import static org.usfirst.frc.team5687.robot.Robot.ledStrip;
import static org.usfirst.frc.team5687.robot.Robot.shooter;

/**
 * Created by Ben Bernard on 4/8/2016.
 */
public class StopShooter extends Command {
    private long _startMills = 0;
    private long _brakeTime = 500;
    private double _brakeSpeed = -0.2;

    public StopShooter() {
        requires(shooter);
    }

    @Override
    protected void initialize() {
        _startMills = System.currentTimeMillis();
    }

    @Override
    protected void execute() {
        ledStrip.setStripColor(Color.BLACK);
        shooter.setRawSpeed(_brakeSpeed);
    }

    @Override
    protected boolean isFinished() {
        return System.currentTimeMillis() > _startMills + _brakeTime;
    }

    @Override
    protected void end() {
        shooter.setRawSpeed(0);
    }

    @Override
    protected void interrupted() {
        end();
    }
}
