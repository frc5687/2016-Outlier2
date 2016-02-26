package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import static org.usfirst.frc.team5687.robot.Robot.camera;

/**
 * Created by Ben Bernard on 2/25/2016.
 */
public class StreamCamera extends Command {

    public StreamCamera() {
        requires(camera);
    }
    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        camera.execute();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {

    }
}
