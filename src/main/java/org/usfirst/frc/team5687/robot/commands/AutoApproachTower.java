package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.Date;

import static org.usfirst.frc.team5687.robot.Robot.driveTrain;
import static org.usfirst.frc.team5687.robot.Robot.imu;

/**
 * Autonomous command to run the drivetrain.
 * For now, runs at a preset speed for a preset time.
 * Eventually we will want to add distance-based options.
 */

public class AutoApproachTower extends Command{

    //private long endTime = 0;
    //private int timeToDrive = 0; //not used at the moment

    private double inchesToDrive = 0;
    private double speed = 0;
    private double inchesDriven = 0;
    private double inchesAtStart = 0;
    private double targetRoll = 0;
    private double angleBuffer = 1;

    /**
     * Drive at a specified speed for a distance specified in inches.
     *
     * @param speed Speed to drive (range 0 to +1
     * @param inchesToDrive Inches to drive (negative for reverse)
     */
    public AutoApproachTower(double speed, double inchesToDrive,double targetRoll) {
        requires(driveTrain);
        this.speed = speed;
        this.targetRoll = targetRoll;
        this.inchesToDrive = inchesToDrive;
    }

    @Override
    protected void initialize() {

        DriverStation.reportError("Approaching Tower at speed " + speed + "until Roll" + targetRoll+".\n", false);

        // endTime = (new Date()).getTime() + timeToDrive; //not used at the moment

        inchesAtStart = driveTrain.getDistance();

    }

    @Override
    protected void execute() {
        driveTrain.tankDrive(inchesToDrive,inchesToDrive,false);
        inchesDriven = inchesAtStart+driveTrain.getDistance();
    }

    @Override
    protected boolean isFinished() {


        if (Math.abs(imu.getRoll()-targetRoll)<angleBuffer){
            return true;
        }
        else{
            return true;
        }
    }

    @Override
    protected void end() {
        DriverStation.reportError("AutoDrive done.\n", false);
        driveTrain.tankDrive(0,0);

    }

    @Override
    protected void interrupted() {

    }
}
