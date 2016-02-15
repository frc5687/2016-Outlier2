package org.usfirst.frc.team5687.robot.commands;

import org.usfirst.frc.team5687.robot.Robot;


/**
 * Created by Baxter on 2/15/2016.
 */
public class AutoTraverseStaticDefense extends AutoTraverse {

    private double speed;

    public AutoTraverseStaticDefense(double speed){
        this.speed = speed;
    }

    protected void execute() {
        Robot.driveTrain.tankDrive(speed,speed);
    }
    
}
