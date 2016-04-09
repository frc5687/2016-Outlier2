package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoTurnOnly extends CommandGroup {

    public AutoTurnOnly(int position) {

        double rotateAngle = 0;

        switch (position){
            case 1:

                // Run forward 100 inches
                addSequential(new AutoDrive(.7, 94.0f));

                // Turn towards the tower
                addSequential(new AutoAlign(50));

                // Run forward 112 inches
                addSequential(new AutoDrive(.7, 48.0f));


                break;
            case 2:
                addSequential(new AutoDrive(.5, 144.0f));

                // Turn towards the tower
                addSequential(new AutoAlign(40.0f));
                break;
            case 3:
                // Turn towards the tower
                addSequential(new AutoAlign(20));

                // Run forward 35 inches
                addSequential(new AutoDrive(.5, 30.0f));

                // Turn towards the tower
                addSequential(new AutoAlign(-2.0f));

                // Run forward 50 inches
                addSequential(new AutoDrive(.5, 50.0f));

                break;
            case 4:
                // Turn towards the tower
                addSequential(new AutoAlign(-20));

                // Run forward 35 inches
                addSequential(new AutoDrive(.5, 30.0f));

                // Turn towards the tower
                addSequential(new AutoAlign(2.0f));

                // Run forward 50 inches
                addSequential(new AutoDrive(.5, 30.0f));

                break;
            case 5:

                // Run forward 135 inches
                addSequential(new AutoDrive(.5, 135.0f));

                // Turn towards the tower
                addSequential(new AutoAlign(-25));

                break;
        }

    }

}
