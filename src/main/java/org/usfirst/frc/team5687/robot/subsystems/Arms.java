package org.usfirst.frc.team5687.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team5687.robot.RobotMap;
import org.usfirst.frc.team5687.robot.commands.RunArmsManually;

/**
 * Created by John on 2/10/2016.
 * This controls the Arms which move up and down to presumably lift something
 */
public class Arms extends Subsystem {

    private VictorSP armsMotor;

    public Arms() {
        armsMotor = new VictorSP(RobotMap.Arms.ARMS_MOTOR);
    }

    public void setSpeed (double speed) {
        armsMotor.set(speed);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new RunArmsManually());
    }
}
