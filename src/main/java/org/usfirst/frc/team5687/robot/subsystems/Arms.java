package org.usfirst.frc.team5687.robot.subsystems;

import edu.wpi.first.wpilibj.*;
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

    private Potentiometer armsPot;
    private VictorSP armsMotor;
    private DigitalInput armsSensor;
    public Arms() {
        armsMotor = new VictorSP(RobotMap.Arms.ARMS_MOTOR);
        armsSensor = new DigitalInput(RobotMap.Arms.ARMS_HALL);
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

    /**
     * Check if arms are below limit
     * @return receeds min value
     */

    public boolean belowTarget(){
        return armsPot.get()< Constants.Arms.DESIRED_DEGREES; //TODO: is .get correct?
    }



    public void updateDashboard() {
        SmartDashboard.putBoolean("Arms limit", isAtLimit());
    }
}
