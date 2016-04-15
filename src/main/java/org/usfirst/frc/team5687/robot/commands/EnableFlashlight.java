package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import static org.usfirst.frc.team5687.robot.Robot.robot;
import static org.usfirst.frc.team5687.robot.Robot.lights;

/**
 * Command to turn flashlight on
 * @author wil
 */
public class EnableFlashlight extends Command {

    public EnableFlashlight() {
        requires(lights);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        if (!robot.isAutonomous()) {
            lights.turnFlashlightOn();
        }
    }

    @Override
    protected boolean isFinished() {
        return robot.isAutonomous() || lights.getFlashlight();
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }
}
