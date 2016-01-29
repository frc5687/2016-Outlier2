package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.OI;
import org.usfirst.frc.team5687.robot.Robot;
import org.usfirst.frc.team5687.robot.subsystems.DriveTrain;

import java.util.Date;

/**
 * Temporary command to run the drivetrain at a preset speed for a preset time.
 * Used for the
 * Created by Ben Bernard on 1/28/2016.
 */
public class AutoDrive extends Command {
    DriveTrain driveTrain = Robot.driveTrain;
    OI oi = Robot.oi;
    private long end = 0;
    private int timeToDrive = 0;
    private double rightSpeed = 0;
    private double leftSpeed = 0;

    public AutoDrive(double speed, int timeToDrive) {
        requires(driveTrain);
        this.leftSpeed = speed;
        this.rightSpeed = speed;
        this.timeToDrive = timeToDrive;
    }

    @Override
    protected void initialize() {
        DriverStation.reportError(String.format("Accelerating to %1$f\n", rightSpeed), false);
        end = (new Date()).getTime() + timeToDrive;
    }

    @Override
    protected void execute() {
        if (oi.isSafetyPressed()) {
            driveTrain.tankDrive(leftSpeed, rightSpeed);
        } else {
            driveTrain.tankDrive(0, 0);
        }
    }

    @Override
    protected boolean isFinished() {
        long now = (new Date()).getTime();
        return now > end;
    }

    @Override
    protected void end() {
        DriverStation.reportError(String.format("   Right speed: %1$f\n", driveTrain.getRightSpeed()), false);
        DriverStation.reportError(String.format("    Right rate: %1$f\n", driveTrain.getRightRate()), false);
        DriverStation.reportError(String.format("    Left speed: %1$f\n", driveTrain.getLeftSpeed()), false);
        DriverStation.reportError(String.format("     Left rate: %1$f\n", driveTrain.getLeftRate()), false);
        DriverStation.reportError("====================\n", false);
    }

    @Override
    protected void interrupted() {

    }
}
