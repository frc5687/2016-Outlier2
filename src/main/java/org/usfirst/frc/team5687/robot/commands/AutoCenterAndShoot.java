package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoCenterAndShoot extends CommandGroup {

    public AutoCenterAndShoot() {
        addParallel(new AutoFire());
        addSequential(new AutoCenterTarget());
        addSequential(new AutoApproachTarget());
    }

}
