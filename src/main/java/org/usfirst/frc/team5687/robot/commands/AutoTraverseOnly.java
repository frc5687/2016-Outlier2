package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team5687.robot.Constants;
import org.usfirst.frc.team5687.robot.Robot;
/**
 *
 */
public class AutoTraverseOnly extends CommandGroup {

    public  AutoTraverseOnly(String defense, int position) {

        double traverseSpeed = 0;
        double rotateAngle = 0;

        switch (defense){
            case "LowBar":
                traverseSpeed = Constants.Autonomous.staticDefenseTraverseSpeeds.LOW_BAR_SPEED;
                break;
            case "Moat":
                traverseSpeed = Constants.Autonomous.staticDefenseTraverseSpeeds.MOAT_SPEED;
                break;
            case "RockWall":
                traverseSpeed = Constants.Autonomous.staticDefenseTraverseSpeeds.ROCK_WALL_SPEED;
                break;
            case "Ramparts":
                traverseSpeed = Constants.Autonomous.staticDefenseTraverseSpeeds.RAMPARTS_SPEED;
                break;
            case "RoughTerrain":
                traverseSpeed = Constants.Autonomous.staticDefenseTraverseSpeeds.ROUGH_TERRAIN_SPEED;
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
                rotateAngle=15;
                break;
            case 4:
                rotateAngle=-10;
                break;
            case 5:
                rotateAngle=-25;
                break;
        }

        // Run forward 42 inches
        addSequential(new AutoDrive(.4, 42.0f));

        // Traverse the selected defense
        addSequential(new AutoTraverseStaticDefense(traverseSpeed));

        // Turn towards the tower
        addSequential(new AutoAlign(rotateAngle));

    }

}
