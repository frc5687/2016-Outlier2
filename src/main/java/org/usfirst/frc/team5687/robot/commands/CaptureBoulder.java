package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.Robot;
import org.usfirst.frc.team5687.robot.subsystems.Intake;

/**
 * Autonomous command to run the intake motors until it detects a ball with Intake.isCaptured()
 * For now, it will never spin the motor wheels for longer than 1 tick until Intake.isCaptured() is complete
 */
public class CaptureBoulder extends Command {

    Intake intake = Robot.intake;

    public CaptureBoulder() {
        requires(intake);
    }

    @Override
    protected void initialize() {
        intake.setSpeed(1.0);
    }

    @Override
    protected void execute() {
        if (intake.isCaptured()) {
            intake.setSpeed(0.0);
        }
    }

    @Override
    protected boolean isFinished() {
        return intake.isCaptured();
    }

    @Override
    protected void end() {
        intake.setSpeed(0.0);
    }

    @Override
    protected void interrupted() {
        intake.setSpeed(0.0);
    }
}
