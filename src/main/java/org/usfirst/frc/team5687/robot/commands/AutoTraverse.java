package org.usfirst.frc.team5687.robot.commands;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.Constants;

import static org.usfirst.frc.team5687.robot.Robot.imu;
import static org.usfirst.frc.team5687.robot.Robot.driveTrain;

public abstract class AutoTraverse extends Command{
    /**
     *
     */
    static final long inchesToTraverseDefense = 90;
    static final long inchesToClear = 3;
    //static final long inchesToCross = 20;
    //static final double rollThreshold = 8.0;
    static final double rollErrorMargin = 1.0;

    double timeStarted;
    long startingInches;

    long inchesToCross;
    double rollThreshold;

    private TraverseState state;
    private long startingStateInches;

    private boolean inchesToTraverseReached = false;


    double positionFirstLevel = 0;

    double inchesLevel;

    public AutoTraverse(long inchesToCross, double rollThreshold){

        inchesToCross = this.inchesToCross;
        rollThreshold = this.rollThreshold;

        if (imu==null) {
            throw new IllegalArgumentException("AutoTraverse commands require the IMU.");
        }
    }
    protected void initialize() {
        //setting the position in Inches and Time, which the robot was at when

        startingInches = (long)Math.abs(driveTrain.getDistance());
        timeStarted = System.currentTimeMillis();
        state = TraverseState.ROLL_UP;
        startingStateInches = startingInches;

        DriverStation.reportError("Starting inches " + startingInches, false);
        DriverStation.reportError("Starting time " + timeStarted , false);
    }

    protected void calculateProgress(){
        float roll = imu.getRoll();
        long distance  = (long)Math.abs(driveTrain.getDistance());
        long inchesInState = distance - startingStateInches;
        // DriverStation.reportError("Roll: " + Float.toString(roll), false);

        // State-dependent checks:
        switch (state) {
            case ROLL_UP:
                // Look for the roll threshold
                if (Math.abs(roll)> rollThreshold) {
                    state = TraverseState.CROSS;
                    startingStateInches = distance;
                    DriverStation.reportError("Roll up threshold reached!  Starting cross.", false);
                }
                break;
            case CROSS:
                if (inchesInState > inchesToCross) {
                    state = TraverseState.ROLL_DOWN;
                    startingStateInches = distance;
                    DriverStation.reportError("Cross inches threshold reached!  Starting roll down.", false);
                }
                break;
            case ROLL_DOWN:
                if (Math.abs(roll)> rollThreshold) {
                    state = TraverseState.LEVEL;
                    startingStateInches = distance;
                    DriverStation.reportError("Roll down threshold reached!  Starting lvel.", false);
                }
                break;
            case LEVEL:
                if (Math.abs(roll)< rollErrorMargin) {
                    state = TraverseState.CLEAR;
                    startingStateInches = distance;
                    DriverStation.reportError("Level reached!  Starting clear.", false);
                }
                break;
            case CLEAR:
                if (inchesInState > inchesToClear) {
                    state = TraverseState.DONE;
                    startingStateInches = distance;
                    DriverStation.reportError("Clear inches threshold reached!  Almost done!.", false);
                }
                break;
            default:
        }

        // DriverStation.reportError("Distance: " + distance, false);
        if(distance - startingInches > inchesToTraverseDefense ){
            inchesToTraverseReached = true;
            DriverStation.reportError("Inches to traverse reached!", false);
        }

    }

    protected boolean isFinished() {
        calculateProgress();
        if(state==TraverseState.DONE && inchesToTraverseReached){
            DriverStation.reportError("Cleared defense!!", false);
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
        driveTrain.tankDrive(0,0);
    }

    @Override
    protected void interrupted() {

    }


    public static enum TraverseState {
        ROLL_UP(0),
        CROSS(1),
        ROLL_DOWN(2),
        LEVEL(3),
        CLEAR(4),
        DONE(5);
        private final int value;

        private TraverseState(int value) {
            this.value = value;
        }

        public static TraverseState fromValue(int val) {
            try {
                return values()[val];
            } catch (ArrayIndexOutOfBoundsException e) {
                return null;
            }
        }

        public int getValue() {
            return value;
        }
    }
}
