package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.OI;
import org.usfirst.frc.team5687.robot.Robot;
import org.usfirst.frc.team5687.robot.subsystems.Arm;

/**
 * Created by John on 2/10/2016.
 */
public class RunArmsManually extends Command {
    Arm arm = Robot.arm;
    OI oi = Robot.oi;

    @Override
    protected void initialize() {
        requires(arm);
    }

    @Override
    protected void execute() {
        arm.setSpeed(oi.getArmSpeed());
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
