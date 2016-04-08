package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.Robot;
import org.usfirst.frc.team5687.robot.subsystems.Lights;

/**
 * Command to turn flashlight off
 * @author wil
 */
public class DisableVisionLight extends Command {
    Lights lights = Robot.lights;

    public DisableVisionLight() {
        requires(lights);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        lights.turnVisionLightOff();
    }

    @Override
    protected boolean isFinished() {
        return !lights.getVisionLight();
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }
}
