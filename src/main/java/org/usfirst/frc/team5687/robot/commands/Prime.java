package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team5687.robot.Constants;

/**
 * Command group to prime the shooter subsystem
 * @author wil
 */
public class Prime extends CommandGroup{

    public Prime() {
        addSequential(new PrimeBoulder());
        addSequential(new SetShooterSpeed(Constants.Shooter.SHOOTER_SPEED, Constants.Shooter.PRIME_TIME));
    }
}
