package org.usfirst.frc.team5687.robot.commands;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.Constants;
import org.usfirst.frc.team5687.robot.Robot;

import static org.usfirst.frc.team5687.robot.Robot.imu;
import static org.usfirst.frc.team5687.robot.Robot.driveTrain;

public abstract class AutoTraverse extends Command{

    double timeStarted;
    double positionFirstLevel = 0;

    double startingInches;
    double inchesLevel;
    private boolean inchesToTraverseReached = false;
    private boolean rollThresholdReached = false;
    static int inchesToTraverseDefense = 90;
    static int rollErrorMargin = 1;

    public AutoTraverse(){

        if (imu==null) {
            throw new IllegalArgumentException("AutoTraverse commands require the IMU.");
        }
    }
    protected void initialize() {
        //setting the position in Inches and Time, which the robot was at when

        startingInches = Math.abs(driveTrain.getDistance());
        timeStarted = System.currentTimeMillis();
        DriverStation.reportError("Starting inches " + startingInches, false);
        DriverStation.reportError("Starting time " + timeStarted , false);
    }

    protected void calculateProgress(){
        float roll = imu.getRoll();
        // DriverStation.reportError("Roll: " + Float.toString(roll), false);

        if(Math.abs(Math.abs(roll)-Constants.Autonomous.MIN_AUTO_TRAVERSE_ANGLE) < rollErrorMargin) {
            rollThresholdReached = true;
            DriverStation.reportError("Roll threshold reached!", false);
        }

        double distance  = Math.abs(driveTrain.getDistance());
        // DriverStation.reportError("Distance: " + distance, false);
        if(distance-startingInches > inchesToTraverseDefense ){
            inchesToTraverseReached = true;
            DriverStation.reportError("Inches to traverse reached!", false);
        }

        if(rollThresholdReached && inchesToTraverseReached && Math.abs(roll)<rollErrorMargin){
            //  the robot is level,
            // and has traveled at least far enough to have traversed the defense,
            // and has pitched at least enough to have traversed the defense

            if (positionFirstLevel == 0) {
                positionFirstLevel = distance;
            }
            inchesLevel = distance-positionFirstLevel;
            //writing how long the robot has been level
        }
    }

    protected boolean isFinished() {
        calculateProgress();
        if(rollThresholdReached && inchesToTraverseReached && inchesLevel > 3){
            return true;
        }

        if(System.currentTimeMillis()-timeStarted>(Constants.Autonomous.MAX_AUTO_TRAVERSE_SECONDS*1000)){
            //if we take too long to register as Traversed, cancel driving as safeguard.
            DriverStation.reportError("Out of time!", false);
            return true;
        }
        return false;
    }

    protected void end() {

    }

    @Override
    protected void interrupted() {

    }
}
