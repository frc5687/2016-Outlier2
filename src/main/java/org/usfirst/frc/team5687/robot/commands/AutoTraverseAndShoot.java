package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoTraverseAndShoot extends CommandGroup {

    public AutoTraverseAndShoot(String defense, int position) {

        addSequential(new AutoTraverseOnly(defense, position));
        // addSequential(new AutoChaseTarget());
        addSequential(new Prime());
        addSequential(new Fire());




    }

}
