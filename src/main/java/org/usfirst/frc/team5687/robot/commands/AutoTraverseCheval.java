package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Created by Maya on 2/18/2016.
 */
public class AutoTraverseCheval extends CommandGroup {
    public AutoTraverseCheval() {
        addSequential(new AutoRunArms());
        addSequential(new AutoDrive(.5, true));
        addSequential(new AutoRunArms(true));//TODO: While stopped, lower arms.
        addSequential(new AutoDrive(.5, 16.0));//TODO: add in double distance to drive until the center point of the cheval
        addSequential(new AutoRunArms());//Is same as beginning
        addSequential(new AutoDrive(.5, 16.0));//TODO: add in distance to drive off the cheval.
    }
}