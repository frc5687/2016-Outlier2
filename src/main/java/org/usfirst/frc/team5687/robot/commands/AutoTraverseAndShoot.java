package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team5687.robot.Constants;

/**
 *
 */
public class AutoTraverseAndShoot extends CommandGroup {

    public AutoTraverseAndShoot(String defense, int position) {

        addSequential(new AutoTraverseOnly(defense));
        addSequential(new AutoTurnOnly(position));
        addParallel(new Prime());
        addSequential(new AutoChaseTarget());
        addSequential(new AutoApproachTower(.5,Constants.Tower.BATTER_LENGTH, Constants.Tower.BATTER_ANGLE));
        addSequential(new Fire());


    }

}
