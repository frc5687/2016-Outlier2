package org.usfirst.frc.team5687.robot.commands;

import org.usfirst.frc.team5687.robot.Robot;


/**
 * Created by Baxter on 2/15/2016.
 */
public class AutoTraverseStaticDefense extends AutoTraverse {

    double desiredSpeed;

    AutoTraverseStaticDefense(){
        desiredSpeed = //TODO: not sure how to keep this open

    }

    protected void initialize(){
        super.initialize();
    }

    protected void execute() {

        Robot.driveTrain.tankDrive(desiredSpeed,desiredSpeed);
    }


    protected boolean isFinished(){
        super.isFinished();

    }

    @Override
    protected void interrupted() {
        Robot.driveTrain.tankDrive(0,0);

    }
}
