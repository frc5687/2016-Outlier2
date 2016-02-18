package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team5687.robot.Constants;

/**
 * Command group to fire the boulder into a high goal
 * @author wil
 */
public class Fire extends CommandGroup{

    public Fire() {
        addSequential(new FireBoulder());
        addSequential(new SetShooterSpeed(0, Constants.Shooter.UNPRIME_TIME));
    }
}
