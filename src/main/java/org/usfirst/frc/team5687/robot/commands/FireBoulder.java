package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.Constants;
import org.usfirst.frc.team5687.robot.LEDColors;

import static org.usfirst.frc.team5687.robot.Robot.intake;
import static org.usfirst.frc.team5687.robot.Robot.ledStrip;
import static org.usfirst.frc.team5687.robot.Robot.pitrackerOutputs;

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
        ledStrip.setStripColor(LEDColors.FIRING);
        double distance = pitrackerOutputs.getNumber("distance", 0);
        double offsetAngle = pitrackerOutputs.getNumber("offsetAngle", 0);

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
        ledStrip.setStripColor(LEDColors.TELEOP);
    }

    @Override
    protected void interrupted() {
        end();
    }
}
