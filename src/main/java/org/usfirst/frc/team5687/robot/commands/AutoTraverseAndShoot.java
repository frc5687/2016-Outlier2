package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoTraverseAndShoot extends CommandGroup {

    public AutoTraverseAndShoot(String defense, int position) {

        if (defense == "LowBar") {
            addParallel(new IntakeLower());
        }
        addSequential(new AutoTraverseOnly(defense));
        addSequential(new AutoTurnOnly(position));
        if (position==1 || position==2) {
            addSequential(new Prime());
        } else {
            addParallel(new Prime());
            addSequential(new AutoChaseTarget());
        }
        addSequential(new Fire());

    }

}
