package org.usfirst.frc.team5687.robot.commands;

/**
 * Created by Baxter on 1/23/2016.
 */

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5687.robot.OI;
import org.usfirst.frc.team5687.robot.Robot;
import org.usfirst.frc.team5687.robot.subsystems.DriveTrain;

/**
 * Command for basic driver-control of the robot chassis
 */

public class DriveWith2Joysticks extends Command {

    DriveTrain driveTrain = Robot.driveTrain;
    OI oi = Robot.oi;

    /*
     * Constructor
     */
    public DriveWith2Joysticks() {
        requires(driveTrain);
    }

    /*
     * Sets up the command
     * Called just before this Command runs the first time(non-Javadoc)
     * @see edu.wpi.first.wpilibj.command.Command#initialize()
     */
    protected void initialize() {
        driveTrain.resetDriveEncoders();
    }

    /*
     * Executes the command
     * Called repeatedly when this Command is scheduled to run(non-Javadoc)
     * @see edu.wpi.first.wpilibj.command.Command#execute()
     */
    protected void execute() {
        driveTrain.tankDrive(oi.getLeftspeed(), oi.getRightspeed());
        DriverStation.reportError(String.format("Right distance: %1$f\r\n", driveTrain.getRightDistance()), false);
        DriverStation.reportError(String.format(" Left distance: %1$f\r\n", driveTrain.getLeftDistance()), false);
        DriverStation.reportError(String.format("   Right ticks: %1$f\r\n", driveTrain.getRightTicks()), false);
        DriverStation.reportError(String.format("    Left ticks: %1$f\r\n", driveTrain.getLeftTicks()), false);

        SmartDashboard.putNumber("Right distance" ,driveTrain.getRightDistance());
        SmartDashboard.putNumber("Left distance" ,driveTrain.getLeftDistance());
        SmartDashboard.putNumber("Right ticks" ,driveTrain.getRightTicks());
        SmartDashboard.putNumber("Left ticks" ,driveTrain.getLeftTicks());
    }

    /*
     * Check if this command is finished running
     * Make this return true when this Command no longer needs to run execute()(non-Javadoc)
     * @see edu.wpi.first.wpilibj.command.Command#isFinished()
     */
    protected boolean isFinished() {
        return false;
    }

    /*
     * Command execution clean-up
     * Called once after isFinished returns true(non-Javadoc)
     * @see edu.wpi.first.wpilibj.command.Command#end()
     */
    protected void end() {
    }

    /*
     * Handler for when command is interrupted
     * Called when another command which requires one or more of the same(non-Javadoc)
     * @see edu.wpi.first.wpilibj.command.Command#interrupted()
     */
    protected void interrupted() {
    }
}
