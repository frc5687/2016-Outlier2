package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import org.usfirst.frc.team5687.robot.Robot;

/**
 * Created by Ben Bernard on 2/18/2016.
 */
public class AutoTraverseBuilder extends Command {


    @Override
    protected void initialize() {
        String defense = Robot.robot.getSelectedDefense();
        int position = Integer.parseInt(Robot.robot.getSelectedPosition());

        DriverStation.reportError("Building autotraverse for defense " + defense + " in position " + defense,false);

        // Traverse the selected defense
        Scheduler.getInstance().add(new AutoTraverseOnly(defense, position));

    }

    @Override
    protected void execute() {

    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {

    }
}
