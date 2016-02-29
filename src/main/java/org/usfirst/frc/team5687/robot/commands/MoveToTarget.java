package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Command group to autonomously drive to target and prime boulder
 * @author wil
 */
public class MoveToTarget extends CommandGroup {
    public MoveToTarget() {
        addParallel(new AutoChaseTarget());
        addParallel(new Prime());
    }
}
