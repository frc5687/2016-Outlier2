package org.usfirst.frc.team5687.robot.commands;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team5687.robot.Constants;
import org.usfirst.frc.team5687.robot.Robot;
import org.usfirst.frc.team5687.robot.subsystems.Arms;
import org.usfirst.frc.team5687.robot.subsystems.DriveTrain;

/**
 * Created by Baxter on 2/18/2016.
 */
public class AutoTraverseCheval extends CommandGroup {
    public AutoTraverseCheval() {
        addSequential(new Arms(x, y));//TODO: add in set time and speed to raise arms.    Raise arms by running motor at a set time and speed to a desired distance.
        addSequential(new AutoDrive(.5, x)); //driveForward//TODO: add how far forward
        addSequential(new AutoDrive(.5, x, true));//Stop moving when at correct angle //TODO: add in inchesToDrive
        addSequential(new Arms(x, -y));//TODO: add in set time and speed to lower arms. While stopped, lower arms.
        addSequential(new AutoDrive(.5, x));//TODO: add in double distance to drive until the center point of the cheval
        addSequential(new Arms(x, y));//Is same as beginning
        addSequential(new AutoDrive(.5, x));//TODO: add in distance to drive off the cheval.
    }
}