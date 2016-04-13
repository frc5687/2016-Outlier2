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

                // Run forward
                addSequential(new AutoDrive(.7, 92.0f));

                // Turn towards the tower
                addSequential(new AutoAlign(60));

                // Run forward
                addSequential(new AutoDrive(.7, 60.0f));


                break;
            case 2:
                // Run forward
                addSequential(new AutoDrive(.7, 120.0f));

                // Turn towards the tower
                addSequential(new AutoAlign(60));
                break;
            case 3:
                // Turn towards the center
                addSequential(new AutoAlign(45));

                // Run forward 35 inches
                addSequential(new AutoDrive(.7, 54.0f));

                // Turn towards the tower
                addSequential(new AutoAlign(-1.0f));

                // Run forward 50 inches
                addSequential(new AutoDrive(.5, 30.0f));

                break;
            case 4:
                // Turn towards the tower
                addSequential(new AutoAlign(-45));

                // Run forward 35 inches
                addSequential(new AutoDrive(.7, 18.0f));

                // Turn towards the tower
                addSequential(new AutoAlign(1.0f));

                // Run forward 50 inches
                addSequential(new AutoDrive(.5, 54.0f));

                break;
            case 5:

                // Turn towards the center
                addSequential(new AutoAlign(-45));

                // Run forwar
                addSequential(new AutoDrive(.7, 90.0f));

                // Turn towards the tower
                addSequential(new AutoAlign(1.0f));

                // Run forward 50 inches
                addSequential(new AutoDrive(.5, 54.0f));

                break;
        }

    }

}
