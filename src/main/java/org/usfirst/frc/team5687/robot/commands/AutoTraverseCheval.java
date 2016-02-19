package org.usfirst.frc.team5687.robot.commands;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.Constants;
import org.usfirst.frc.team5687.robot.Robot;
import org.usfirst.frc.team5687.robot.subsystems.DriveTrain;

/**
 * Created by Baxter on 2/18/2016.
 */
public class AutoTraverseCheval extends Command{

    AutoDrive driveforward;
    AutoDrive autormshigh;
    AutoDrive autoarmslow;
    DriveTrain stopmoving;
    private float currentAngle;
    double currentLeftDistance;
    double currentRightDistance;
    double currentArmPosition;
    double lengthOfCheval = q;//TODO: over two feet, but I can't find the width of the ramp on the first rulebook.

    private double desiredAngle = calculateDesiredAngle();//Question: will this send the return to the variable? That's what I want...
    boolean onRamp = false;
    boolean armsDown = false;
    double edgeWheelHeight = y; //TODO: add in this value. If you're wondering why I don't have these, know that I will need to take a few minutes to explain my thinking.
    double wheelContactRamp = z; //TODO: add in this value. Same as above.
    double onRampDistance = 13;//Distance to drive onto ramp
    double centerChevalDistance = x;//TODO: one foot plus any extra distance on current ramp

    DriveTrain driveTrain = Robot.driveTrain;

    public AutoTraverseCheval(double desiredAngle){
       this.desiredAngle = desiredAngle;
    }



    private double calculateDesiredAngle(){
        return Math.sin(edgeWheelHeight/wheelContactRamp);
    }

/* desiredAngle=sin(y/z) where
* y = height from edge of wheel contact on ramp to ground and
* z = wheel contact w/ ramp
*/
//Get the distance of the robot, to be used when gauging if robot is halfway over the cheval


    @Override
    protected void initialize() {

        }

    protected void execute () {

        driveForward();
        moveArmsUp();

        thePitch();//Get the current pitch
        currentAngle = thePitch();
        isOnRamp();//Check to see if the robot's two front wheels are on the ramp
        if(onRamp){//If it's on the ramp, move the arms down
            stopmoving.tankDrive(0,0);//TODO: reset encoder
            moveArmsDown();
            driveforward = new AutoDrive(0.5, centerChevalDistance);
        }

        currentRightDistance = driveTrain.getRightDistance();//TODO: so put `DriveTrain driveTrain = Robot.driveTrain and call driveTrain.getLeftDistance(); instead
        currentLeftDistance = driveTrain.getLeftDistance();
        currentArmPosition = driveTrain.getArmDistance();
        
        if (armsDown){//If arms are down, drive.
            driveTrain.tankDrive(.5,.5); //TODO: is this a good speed?
        }//Add something that can tell when the cheval is down all the way

        if (currentAngle == 0 && currentLeftDistance == x && currentRightDistance == x){//TODO: Need distance per pulse to set values {//TODO: This will probably need a deadband.
        moveArmsUp();}//Lift arms when robot angle evens out and the distance driven is equal to half the ramp, both with a range set on the values.
}

    private void driveForward(){
        driveforward = new AutoDrive(0.5, onRampDistance); //TODO: q = time to drive forward

    }

    private void moveArmsUp() {
        autoarmshigh = new AutoDrive(0.5, Constants.Autonomous.ARMS_HIGH);//TODO: is this a good speed?
    }

    private float thePitch(){
        return Robot.imu.getPitch();
    }

    private void isOnRamp() {
        if (currentAngle == desiredAngle) {
            onRamp = true;
        }
    }

    /**
     *Methods that pull the arms up/down to the desired height.
     */

    private void moveArmsDown() {
        autoarmslow = new AutoDrive(-0.5, Constants.Autonomous.ARMS_LOW);
    }

    private void areArmsDown(){
        if(currentArmPosition == Constants.Autonomous.ARMS_LOW){
            armsDown = true;
        }
    }

    @Override
    protected boolean isFinished() {
        if (currentLeftDistance == lengthOfCheval){ //Question: is it fine to just pick one?
            if(currentAngle == 0){return true;}
        }
        return false;
    }

    @Override
    protected void end() {
        
    }

    @Override
    protected void interrupted() {

    }
}
