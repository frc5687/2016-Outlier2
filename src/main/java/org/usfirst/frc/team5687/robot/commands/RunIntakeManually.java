package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.OI;
import org.usfirst.frc.team5687.robot.Robot;
import static org.usfirst.frc.team5687.robot.Robot.intake;
import static org.usfirst.frc.team5687.robot.Robot.oi;
import static org.usfirst.frc.team5687.robot.Robot.shooter;

/**
 * Command for basic manual control of the boulder intake
 * @author wil
 */
public class RunIntakeManually extends Command {

    public RunIntakeManually() {
        requires(intake);
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        // if (!shooter.isRunning() || oi.getOverride()) {
            intake.setSpeed(oi.getIntakeSpeed());
        // }
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

    }
}
