package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Created by Maya on 2/18/2016.
 */
public class AutoTraverseCheval extends CommandGroup {
    public AutoTraverseCheval() {
        addSequential(new AutoRunArms());//Run arms up
        addSequential(new AutoDrive(.5, true));//Drive until on ramp
        addSequential(new AutoRunArms(true));//While stopped, lower arms.
        addSequential(new AutoDrive(.5, 16.0));//TODO: add in double distance to drive until the center point of the cheval
        addSequential(new AutoRunArms());//Run arms up
        addSequential(new AutoDrive(.5, 16.0));//TODO: add in distance to drive off the cheval.
    }
}