package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.Robot;
import org.usfirst.frc.team5687.robot.subsystems.DriveTrain;

/**
 * Created by Baxter on 2/18/2016.
 */
public class AutoTraverseCheval extends Command{

    private AutoDrive autoarmshigh;
    private AutoDrive autoarmslow;
    private float currentAngle;
    double currentLeftDistance;
    double currentRightDistance;

    DriveTrain driveTrain = Robot.driveTrain;


    private float thePitch(){
        return Robot.imu.getPitch();
    }


    desiredAngle = new AutoDrive();//TODO: Add in desiredAngle once I have the measurements

//Get the distance of the robot, to be used when gauging if robot is halfway over the cheval




    boolean armsDown = false;


/* desiredAngle=sin(y/z) where
* y = height from edge of wheel contact on ramp to ground and
* z = wheel contact w/ ramp
*/

    private boolean isOnRamp() {
        if (currentAngle == desiredAngle) {
            onRamp = true;
        }
    }

    protected void execute () {
        thePitch();
        currentAngle = thePitch();
        isOnRamp();
        moveArmsUp();
        currentRightDistance = driveTrain.getRightDistance();//TODO: so put `DriveTrain driveTrain = Robot.driveTrain and call driveTrain.getLeftDistance(); instead
        currentLeftDistance = driveTrain.getLeftDistance();
        if (isOnRamp()) {
            driveTrain.tankDrive(0,0);//stop driving
            moveArmsDown();//If on ramp, move arms down.
        }
        if (areArmsDown()){//If arms are down, drive.
            driveTrain.tankDrive(.5,.5); //TODO: is this a good speed?
        }//Add something that can tell when the cheval is down all the way

        if (currentAngle == 0 && currentLeftDistance == ½ ramp && currentRightDistance == half ramp) {
            armsUp();
        }// Lift arms when robot angle evens out and the distance driven is equal to half the ramp, both with a range set on the values.
    }

    /**
     *Methods that pull the arms up/down to the desired height.
     */
    private void moveArmsUp() {
        autoarmshigh = new AutoDrive(0.5, x);//TODO: Add in x.
    }

    public void moveArmsDown() {
        autoarmslow = new AutoDrive(-0.5, x);
    }

    private boolean areArmsDown(){
//If that compares, what sets the value?
        if(armDistance == ARMS_LOW){
            armsDown = true;
        }
    }


}
