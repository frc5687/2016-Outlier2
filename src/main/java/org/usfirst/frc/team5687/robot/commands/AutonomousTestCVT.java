package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Simple command group to enable DRYDOCK TESTING of the CVT.
 *
 * This will run the motors at 70% for 10 seconds, log the stats, then increment to 75%, and so on up to 100%.
 *
 * Created by Ben Bernard on 1/28/2016.
 */
public class AutonomousTestCVT extends CommandGroup {
    public  AutonomousTestCVT() {
        addSequential(new AutoDrive(.7, 10000));
        addSequential(new AutoDrive(.75, 10000));
        addSequential(new AutoDrive(.8, 10000));
        addSequential(new AutoDrive(.85, 10000));
        addSequential(new AutoDrive(.9, 10000));
        addSequential(new AutoDrive(.95, 10000));
        addSequential(new AutoDrive(1, 10000));
        addSequential(new AutoDrive(0, 5000));
    }
}
