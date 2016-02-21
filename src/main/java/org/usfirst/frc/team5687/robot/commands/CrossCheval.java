package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team5687.robot.Constants;

/**
 * Created by Baxter on 2/19/2016.
 */
public class CrossCheval extends CommandGroup {
    public CrossCheval(){
        addSequential(new AutoDrive(0.5, Constants.Autonomous.ON_RAMP_DISTANCE));//Drive up to ramp
        //TODO:pull cheval down, make this dependent on the robot being on the ramp (you could use a method similar to draft 2's isOnRamp)
        //
    }


}
