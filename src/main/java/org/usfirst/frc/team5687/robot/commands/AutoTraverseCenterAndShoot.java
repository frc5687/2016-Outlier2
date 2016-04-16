package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoTraverseCenterAndShoot extends CommandGroup {

    public AutoTraverseCenterAndShoot(String defense, int position) {

        if (defense == "LowBar") {
            addParallel(new IntakeLower());
        }
        addSequential(new AutoTraverseOnly(defense));
        addSequential(new AutoTurnOnly(position));
        // addParallel(new AutoFire());
        addSequential(new AutoCenterTarget());
        addSequential(new AutoApproachTarget());
        addSequential(new AutoDrive(.6, -24.0f));
        addSequential(new AutoAlign(-179));

    }

}
