package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Created by Ben Bernard on 6/7/2016.
 */
public class AutoCenterApproachShoot extends CommandGroup {

    public AutoCenterApproachShoot() {
        addParallel(new AutoFire());
        addSequential(new AutoCenterTarget());
        addSequential(new AutoApproachTarget());
    }
}
