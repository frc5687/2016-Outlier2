package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Command group to autonomously drive to high goal target and fire boulder
 * @author wil
 */
public class AutoScore extends CommandGroup {
    public AutoScore() {
        addSequential(new MoveToTarget());
        addSequential(new Fire());
    }
}
