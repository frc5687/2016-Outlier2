package org.usfirst.frc.team5687.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team5687.robot.Constants;
import org.usfirst.frc.team5687.robot.RobotMap;
import org.usfirst.frc.team5687.robot.commands.RunArmsManually;

/**
 * Created by John on 2/10/2016.
 */
public class Arm extends Subsystem {

    private VictorSP armMotor;

    public Arm() {
        armMotor = new VictorSP(RobotMap.Arm.ARM_MOTOR);
    }

    public void setSpeed (double speed) {
        speed = Math.min(speed, armMotor.get() + Constants.Limits.ACCELERATION_CAP);
        speed = Math.max(speed, armMotor.get() - Constants.Limits.ACCELERATION_CAP);

        armMotor.set(speed);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new RunArmsManually());
    }
}
