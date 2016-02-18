package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import org.usfirst.frc.team5687.robot.Constants;
import org.usfirst.frc.team5687.robot.Robot;

import static org.usfirst.frc.team5687.robot.Robot.driveTrain;

/**
 *
 */
public class AutoTraverseOnly extends Command {

    public  AutoTraverseOnly() {


    }


    protected void initialize() {
        double traverseSpeed = 0;
        double rotateAngle = 0;

        switch (Robot.robot.getSelectedDefense()){
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


        switch (Robot.robot.getSelectedPosition()){
            case "1":
                rotateAngle=50;
                break;
            case "2":
               rotateAngle=30;
                break;
            case "3":
                rotateAngle=15;
                break;
            case "4":
                rotateAngle=-10;
                break;
            case "5":
                rotateAngle=-25;
                break;
        }

        DriverStation.reportError("Traversing "+Robot.robot.getSelectedDefense()+", in position "+Robot.robot.getSelectedPosition()+", at "+traverseSpeed+" speed.",false);

        // Run forward 36 inches
        //Scheduler.getInstance().add(new AutoDrive(.4, 42.0f));

        // Traverse the selected defense
        Scheduler.getInstance().add(new AutoTraverseStaticDefense(traverseSpeed));

        // Turn towards the tower
        // Scheduler.getInstance().add(new AutoAlign(rotateAngle));


    }

    @Override
    protected void execute() {

    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {

    }
}
