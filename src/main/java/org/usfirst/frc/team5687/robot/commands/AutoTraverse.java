package org.usfirst.frc.team5687.robot.commands;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.Constants;
import org.usfirst.frc.team5687.robot.Robot;

import static org.usfirst.frc.team5687.robot.Robot.*;

public abstract class AutoTraverse extends Command{
    AHRS imu = Robot.imu;

    double timeStarted;
    double positionFirstLevel = 0;

    double startingInches;
    double inchesLevel;
    private boolean inchesToTraverseReached = false;
    private boolean pitchThresholdReached = false;
    static int inchesToTraverseDefense = 80;
    static int pitchErrorMargin = 2;


    public AutoTraverse() {
        if (imu==null) {
            throw new IllegalArgumentException("AutoTraverse commands require the IMU.");
        }
    }
    protected void initialize() {
        //setting the position in Inches and Time, which the robot was at when
        startingInches = Math.abs(driveTrain.getDistance());
        timeStarted = System.currentTimeMillis();
    }


    protected void execute() {


        if(Math.abs(Math.abs(imu.getPitch())-Constants.Autonomous.MIN_AUTO_TRAVERSE_ANGLE) < pitchErrorMargin) {
            pitchThresholdReached = true;
        }

        if(Math.abs(driveTrain.getDistance())-startingInches > inchesToTraverseDefense ){
            inchesToTraverseReached = true;
        }

        if(pitchThresholdReached && inchesToTraverseReached && Math.abs(imu.getPitch())<pitchErrorMargin){
            //  the robot is level,
            // and has traveled at least far enough to have traversed the defense,
            // and has pitched at least enough to have traversed the defense

            if (positionFirstLevel == 0) {
                positionFirstLevel = driveTrain.getLeftDistance();
            }
            inchesLevel = driveTrain.getLeftDistance()-positionFirstLevel;
            //writing how long the robot has been level
        }

    }


    protected boolean isFinished() {

        if(pitchThresholdReached && inchesToTraverseReached && inchesLevel > 3){
            return true;
        }

        if(System.currentTimeMillis()-timeStarted>(Constants.Autonomous.MAX_AUTO_TRAVERSE_SECONDS*1000)){
            //if we take too long to register as Traversed, cancel driving as safeguard.
            return true;
        }
        return false;
    }

    protected void end() {

    }
}
