package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Command group to fire the boulder into a high goal
 * @author wil
 */
public class AutoFire extends CommandGroup{

    public AutoFire() {
        addSequential(new Prime());
        addSequential(new AutoDetectTarget());
        addSequential(new FireBoulder());
        addSequential(new StopShooter());
    }
}
