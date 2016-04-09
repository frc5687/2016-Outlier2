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
        addParallel(new Prime());
        addSequential(new AutoChaseTarget());
        addSequential(new Fire());




    }

}
