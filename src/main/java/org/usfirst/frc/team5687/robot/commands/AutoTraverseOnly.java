package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team5687.robot.Constants;

/**
 *
 */
public class AutoTraverseOnly extends CommandGroup {

    public  AutoTraverseOnly(String defense) {

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
                addSequential(new AutoTraverseStaticDefense(defaultTraverseSpeed, 20, 15)); //Tested and tuned
                break;
            case "RockWall":
                addSequential(new AutoTraverseStaticDefense(.4,2,18)); //Tested and tuned
                break;
            case "Rampart":
                addSequential(new AutoTraverseStaticDefense(.4,24,13.5));
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

    }

}
