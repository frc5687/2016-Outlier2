package org.usfirst.frc.team5687.robot.subsystems;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5687.robot.Constants;
import org.usfirst.frc.team5687.robot.RobotMap;
import org.usfirst.frc.team5687.robot.commands.RunArmsManually;
import org.usfirst.frc.team5687.robot.utils.Helpers;

/**
 * This controls the Arms which move up and down to presumably lift something
 */
public class Arms extends Subsystem {

    private int timeToRaise = 0; //TODO: add in timeToRaise.
    private int speedToRaise = 0; //TODO: add in speedToRaise.
    private VictorSP armsMotor;
    private DigitalInput armsSensor;
    public Arms() {
        armsMotor = new VictorSP(RobotMap.Arms.ARMS_MOTOR);
        armsSensor = new DigitalInput(RobotMap.Arms.ARMS_HALL);
    }

    public Arms(int timeToRaise, int speedToRaise){
        this.timeToRaise = timeToRaise;
        this.speedToRaise = speedToRaise;
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new RunArmsManually());
    }

    public void setSpeed(double speed) {
        if (speed >= 0 && isAtLimit()) {
            armsMotor.set(0);
        } else {
            armsMotor.set(Helpers.applySensitivityTransform(speed));
        }
    }

    public void stop() {
        armsMotor.set(0);
    }

    /**
     * Check if arms is at limit
     * @return hall effect sensor state
     */
    public boolean isAtLimit() {
        return !armsSensor.get();
    }

    public void updateDashboard() {
        SmartDashboard.putBoolean("Arms limit", isAtLimit());
    }
}
