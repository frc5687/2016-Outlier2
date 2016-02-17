package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team5687.robot.Constants;

/**
 * Command group to cancel priming the shooter subsystem
 * @author wil
 */
public class CancelPrime extends CommandGroup{

    public CancelPrime() {
        addSequential(new SetShooterSpeed(0, Constants.Shooter.UNPRIME_TIME)); // Stops the shooter
        addSequential(new UnprimeBoulder()); // Unprimes the shooter to captured position
    }
}
