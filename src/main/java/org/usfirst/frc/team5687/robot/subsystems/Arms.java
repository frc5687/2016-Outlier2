package org.usfirst.frc.team5687.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5687.robot.Constants;
import org.usfirst.frc.team5687.robot.RobotMap;
import org.usfirst.frc.team5687.robot.commands.RunArmsManually;
import org.usfirst.frc.team5687.robot.utils.Helpers;

/**
 * This controls the Arms which move up and down to presumably lift something
 */
public class Arms extends Subsystem {

    private VictorSP armsMotor;
    private DigitalInput armsSensor;
    private Potentiometer armsPot;

    public Arms() {
        armsMotor = new VictorSP(RobotMap.Arms.ARMS_MOTOR);
        armsSensor = new DigitalInput(RobotMap.Arms.ARMS_HALL);
        armsPot = new AnalogPotentiometer(RobotMap.Arms.ARMS_POT, Constants.Arms.ARMS_SCALE, Constants.Arms.ARMS_OFFSET);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new RunArmsManually());
    }

    public void setSpeed (double speed) {
        armsMotor.set(Helpers.applySensitivityTransform(speed));
    }

    /**
     * Returns if arms is beyond limit
     * @return true if arms cannot move more in current direction
     */
    public boolean isArmsBeyondLimit() {
        return armsPot.get() > Constants.Arms.ARMS_MAX_DEGREES || armsPot.get() < Constants.Arms.ARMS_MIN_DEGRESS;
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
        SmartDashboard.putNumber("Potentiometer value is", armsPot.get());
    }
}
