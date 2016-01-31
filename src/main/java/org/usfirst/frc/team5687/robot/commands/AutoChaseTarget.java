package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import org.usfirst.frc.team5687.robot.Robot;
import org.usfirst.frc.team5687.robot.subsystems.DriveTrain;

/**
 * Created by Ben Bernard on 1/31/2016.
 */
public class AutoChaseTarget extends Command {
    DriveTrain driveTrain;
    NetworkTable tracking;

    private static final double targetWidth = 50;

    @Override
    protected void initialize() {
        driveTrain = Robot.driveTrain;
        tracking = NetworkTable.getTable("tracking");
    }

    @Override
    protected void execute() {

        // read network tables
        double width = tracking.getNumber("width", 0);

        if (width==0) {
            driveTrain.tankDrive(0,0);
        } else if (width<targetWidth) {
            // set motor speed
            driveTrain.tankDrive(.1, .1);
        } else if (width>targetWidth) {
            driveTrain.tankDrive(-.1, -.1);
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {

    }
}
