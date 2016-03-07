package org.usfirst.frc.team5687.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;


import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5687.robot.Constants;
import org.usfirst.frc.team5687.robot.RobotMap;
import org.usfirst.frc.team5687.robot.commands.RunArmsManually;
import org.usfirst.frc.team5687.robot.utils.Helpers;

/**
 * Class for arms subsystem to cross defenses by lifting or lowering
 */
public class Arms extends Subsystem {

    private Potentiometer armsPot;
    private VictorSP armsMotor;
    private DigitalInput armsSensor;

    public Arms() {
        armsMotor = new VictorSP(RobotMap.Arms.ARMS_MOTOR);
        armsSensor = new DigitalInput(RobotMap.Arms.ARMS_HALL);
        armsPot = new AnalogPotentiometer(RobotMap.Arms.ARMS_POT, Constants.Arms.ARMS_SCALE, Constants.Arms.ARMS_OFFSET);
    }

    public void invertMotor(){
        armsMotor.setInverted(true);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new RunArmsManually());
    }

    public void setSpeed(double speed) {
        boolean movingUp = speed < 0;
        SmartDashboard.putString("Arms motion", movingUp ? "UP" : "DOWN");
        armsMotor.set(Helpers.applySensitivityTransform(speed));

        /*
        TODO: Calibrate safeguards for arms, pot v hall, before using
        armsMotor.set((movingUp && isAtUpperLimit() || movingUp && isAboveLimit() || !movingUp && isBelowLimit())
            ? 0 : Helpers.applySensitivityTransform(speed));
         */
    }

    public void moveUp() {
        armsMotor.set(-Constants.Arms.ARMS_SPEED);
    }

    public void moveDown() {
        armsMotor.set(Constants.Arms.ARMS_SPEED);
    }

    public void setInvertedSpeed(double thespeed){
        if(thespeed <= 0); //TODO: add limit?
    }

    public void stop() {
        armsMotor.set(0);
    }

    /**
     * Check if arms is at upper limit
     * @return hall effect sensor state
     */
    public boolean isAtUpperLimit() {
        return !armsSensor.get();
    }

    /**

    /* Returns if arms is above pot limit
    * @return whether arms exceeds max value
    */
    public boolean isAboveLimit() {
        return armsPot.get() > Constants.Arms.MAX_DEGREES;
    }

    /**
     * Returns if arms is below pot limit
     * @return whether arms recedes min value
     */
    public boolean isBelowLimit() {
        return armsPot.get() < Constants.Arms.MIN_DEGREES;
    }
    public boolean atTarget(){
        return armsPot.get() == Constants.Arms.DESIRED_DEGREES; }

    public boolean isBelowTarget(){
        return armsPot.get() < Constants.Arms.DESIRED_DEGREES;
    }

    public boolean isAboveTarget(){
        return armsPot.get() > Constants.Arms.DESIRED_DEGREES;}

    public void updateDashboard() {
        SmartDashboard.putBoolean("Arms upper limit", isAtUpperLimit());
        SmartDashboard.putBoolean("Arms MAX", isAboveLimit());
        SmartDashboard.putBoolean("Arms MIN", isBelowLimit());
    }
}
