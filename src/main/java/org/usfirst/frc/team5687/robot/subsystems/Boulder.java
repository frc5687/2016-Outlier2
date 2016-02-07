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

    public void intake() {
        //Start Conditions: Hopper sensor detects NO boulder in hopper
        //                  Check for shooter wheels not spinning
        //Do What:          Run Intake roller IN
        //End Conditions:   Hopper sensor detects a boulder in hopper, sets hasBoulder flag
    }

    public void bowlLow() {
        //Start Conditions: if hasBoulder(), (hopper sensor detects boulder in hopper)
        //                  IMU Detects proper angle (not on floor, as that is too far away from Goal)
        //                  Vision tracking detects goal (if able) or distance (if able)
        //Do What:          Run Intake roller reverse
        //End Conditions:   Hopper sensor detects NO boulder in hopper and wait for a time and/(or?) Drivers view Boulder enter Goal
        //                  Reset(?)
    }

    public void prime() {
        //Should run automatically when IMU detects robot has navigated a defense into opponent's courtyard

        //Start Conditions: Hopper sensor detects boulder in hopper, robot is level
        //Do What:          Run Intake roller 1/4 turn reverse, using a constant
        //                  (once complete), gets wheel
        //End Conditions:   sets isPrimed flag, shootHigh command is ready
    }

    public void shootHigh() {
        //Start Conditions: isPrimed, Hopper sensor detects NO boulder in contact with shooter wheels
        //                  Robot is not moving significantly (or at all?)
        //Do What:          Run Intake roller forward
        //End Conditions:   Encoder detects drop in shooter wheels RPM, or wait for time
        //                  Reset(?)
    }

    public void cancel() {
        //Do What:         Cancel a command
        //End Conditions:  Command has been canceled
    }

    public void lock() {
        //Should run automatically if IMU detects a collision

        //Do What:          Prevent ShootHigh or ShootLow command from executing
        //End Conditions:   Robot stops moving
    }

    public void reset() {
        //Do What:          Cut power to shooter wheels
        //End Conditions:   No boulder in hopper, shooter wheels at 0 rpm
        //Then do what:     Clear primed and hasBoulder flags
    }
}
