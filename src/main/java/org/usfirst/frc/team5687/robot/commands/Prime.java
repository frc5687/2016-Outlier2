package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5687.robot.Constants;
import org.usfirst.frc.team5687.robot.Robot;

/**
 * Command group to prime the shooter subsystem
 * @author wil
 */
public class Prime extends CommandGroup{

    private double SHOOTER_SPEED;

    public Prime() {
        if (Robot.robot.getBoulderCondition().equals("2")) {
            SHOOTER_SPEED = Constants.Shooter.OLD_SHOOTER_SPEED;
        } else {
            SHOOTER_SPEED = Constants.Shooter.NEW_SHOOTER_SPEED;
        }

        addSequential(new PrimeBoulder());
        addSequential(new SetShooterSpeed(SHOOTER_SPEED, Constants.Shooter.PRIME_TIME));
    }
}
