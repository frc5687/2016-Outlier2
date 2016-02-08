package org.usfirst.frc.team5687.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team5687.robot.RobotMap;

public class Boulder extends Subsystem {

    VictorSP intakeMotor;
    VictorSP shooterMotor;
    Encoder shooterEncoder;
    DigitalInput hopperSensor;
    DigitalInput primeSensor;

    public Boulder() {
        intakeMotor = new VictorSP(RobotMap.intakeMotor);
        shooterMotor = new VictorSP(RobotMap.shooterMotor);
        shooterEncoder = new Encoder(RobotMap.shooterEncoderChannelA, RobotMap.shooterEncoderChannelB, false, Encoder.EncodingType.k4X);
        hopperSensor = new DigitalInput(RobotMap.hopperSensor);
        primeSensor = new DigitalInput(RobotMap.primeSensor);
    }

    @Override
    public void initDefaultCommand() {

    }

}
