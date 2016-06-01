package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.Constants;
import org.usfirst.frc.team5687.robot.utils.Color;

import static org.usfirst.frc.team5687.robot.Robot.intake;
import static org.usfirst.frc.team5687.robot.Robot.ledStrip;
import static org.usfirst.frc.team5687.robot.Robot.pitracker;

/**
 * Command to fire the boulder from the intake to shooter wheels
 * @author wil
 */
public class FireBoulder extends Command {
    private long endTime;

    public FireBoulder() {
        requires(intake);
    }

    @Override
    protected void initialize() {
        double distance = pitracker.getNumber("distance", 0);
        double offsetAngle = pitracker.getNumber("offsetAngle", 0);

        endTime = System.currentTimeMillis() + Constants.Shooter.UNPRIME_TIME;
        DriverStation.reportError("Boulder Fired at offsetAngle: "+ offsetAngle +", and distance: "+ distance, false);
    }

    @Override
    protected void execute() {
        intake.setSpeed(Constants.Intake.FIRE_SPEED);
    }

    @Override
    protected boolean isFinished() {
        return !intake.isPrimed() && System.currentTimeMillis() > endTime;
    }

    @Override
    protected void end() {
        intake.stop();
        ledStrip.setStripColor(Color.WHITE);
    }

    @Override
    protected void interrupted() {
    }
}
