package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team5687.robot.Constants;

/**
 *
 */
public class AutoTraverseOnly extends CommandGroup {

    public  AutoTraverseOnly(String defense, int position) {

        double traverseSpeed = 0;
        double rotateAngle = 0;

        long defaultInchesToCross = 20;
        double defaultRollThreshold = 8.0;
        double defaultTraverseSpeed = .5;

        // Run forward 72 inches
        addSequential(new AutoDrive(.7, 36.0f));

        switch (defense){

            //(traverseSpeed, inchesToCross, rollThreshold)

            case "LowBar":
                addSequential(new AutoTraverseStaticDefense(defaultTraverseSpeed,defaultInchesToCross,defaultRollThreshold));
                break;
            case "Moat":
                addSequential(new AutoTraverseStaticDefense(defaultTraverseSpeed, 20, 15));
                break;
            case "RockWall":
                addSequential(new AutoTraverseStaticDefense(.4,2,18));
                break;
            case "Ramparts":
                addSequential(new AutoTraverseStaticDefense(.3,4,defaultRollThreshold));
                break;
            case "RoughTerrain":
                addSequential(new AutoTraverseStaticDefense(defaultTraverseSpeed,12,12));
                break;
            case "Cheval":
                //addSequential(new AutoTraverseCheval());
                break;
            case "Portcullis":
                //addSequential(new AutoTraversePortcullis());
                break;
        }


        switch (position){
            case 1:
                rotateAngle=50;
                break;
            case 2:
                rotateAngle=30;
                break;
            case 3:
                // Turn towards the tower
                addSequential(new AutoAlign(20));

                // Run forward 24 inches
                addSequential(new AutoDrive(.5, 35.0f));

                // Turn towards the tower
                addSequential(new AutoAlign(-2.0f));

                // Run forward 60 inches
                addSequential(new AutoDrive(.5, 50.0f));

                break;
            case 4:
                rotateAngle=-10;
                break;
            case 5:
                rotateAngle=-25;
                break;
        }




    }

}
