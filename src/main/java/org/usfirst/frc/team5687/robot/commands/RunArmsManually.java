package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.Constants;
import org.usfirst.frc.team5687.robot.OI;
import org.usfirst.frc.team5687.robot.Robot;
import org.usfirst.frc.team5687.robot.subsystems.Arms;

/**
 * Command for basic manual-control of the arms
 * Created by John on 2/10/2016.
 */
public class RunArmsManually extends Command {
    Arms arms = Robot.arms;
    OI oi = Robot.oi;
    private VictorSP armsMotor;
    double speed;
    double time;
    double direction;
    public boolean isDown;

    public RunArmsManually(double speed, double time, boolean isDown){
        this.speed = speed;
        this.time = time;
        armsMotor.setInverted(Constants.Cheval.ARM_MOTOR_INVERTED);
    }

    public RunArmsManually(double speed, double time){
        this.speed = speed;
        this.time = time;
    }

    public RunArmsManually() {
        requires(arms);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        arms.setSpeed(oi.getArmsSpeed());
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
