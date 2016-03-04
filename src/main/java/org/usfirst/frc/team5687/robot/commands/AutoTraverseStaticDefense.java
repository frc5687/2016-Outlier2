package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import org.usfirst.frc.team5687.robot.Robot;

import static org.usfirst.frc.team5687.robot.Robot.driveTrain;


/**
 * Created by Maya on 2/15/2016.
 */
public class AutoTraverseStaticDefense extends AutoTraverse {

    private double speed;

    public AutoTraverseStaticDefense(double speed, long inchesToCross, double rollThreshold){
        super(inchesToCross,rollThreshold);
        this.speed = speed;
    }
    protected void initialize() {
        //setting the position in Inches and Time, which the robot was at when
        DriverStation.reportError("Starting autotraverse at speed " + speed, false);
        super.initialize();
    }

    protected void execute() {
        Robot.driveTrain.tankDrive(speed,speed);
    }
    
}