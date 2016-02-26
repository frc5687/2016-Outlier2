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


    public AutoTraverseCheval(){
    addSequential(new AutoDrive(.5,x)); //driveForward//TODO: add how far forward
    addSequential(new AutoDrive(.5, x, true));//Stop moving when at correct angle //TODO: add in inchesToDrive
    addSequential(new AutoDrive(0.5, centerChevalDistance)); //drive forward

//isFinished() {isOnRamp method return true if on ramp}

        /* When it is on the ramp, stop, put arms down, drive forward until center.


    */

    // addSequential( );


         // Stop moving when on ramp );  
    //Arms down while on ramp
    //drive forward until center
    //Stop moving while at center
    //Arms up
    //drive forward rest of the way
    }
}
//Questions: 1) How do I get onRamp into this class?
