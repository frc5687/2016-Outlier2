package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.Robot;
import org.usfirst.frc.team5687.robot.subsystems.Lights;

/**
 * Command to turn flashlight off
 * @author wil
 */
public class DisableFlashlight extends Command {
    Lights lights = Robot.lights;

    public DisableFlashlight() {
        requires(lights);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        lights.turnFlashlightOff();
    }

    @Override
    protected boolean isFinished() {
        return !lights.getFlashlight();
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }
}
