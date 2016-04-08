package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoTurnAndShoot extends CommandGroup {

    public AutoTurnAndShoot(int position) {

        addSequential(new AutoTurnOnly(position));
        addParallel(new Prime());
        addSequential(new AutoChaseTarget());
        addSequential(new Fire());
    }

}
