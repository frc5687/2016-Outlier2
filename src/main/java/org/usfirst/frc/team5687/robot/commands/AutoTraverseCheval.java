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


    @Override
    protected void execute() {
        super.execute();
        while (Robot.driveTrain.onRamp == false){
        }

    }

    public AutoTraverseCheval(){
    addSequential(new AutoDrive(.5,x)); //driveForward//TODO: add
    addSequential(new AutoTraverseCheval());
    // addSequential( );


        );    // Stop moving when on ramp
    //Arms down while on ramp
    //drive forward until center
    //Stop moving while at center
    //Arms up
    //drive forward rest of the way
    }
}
//Questions: 1) How do I get onRamp into this class?
