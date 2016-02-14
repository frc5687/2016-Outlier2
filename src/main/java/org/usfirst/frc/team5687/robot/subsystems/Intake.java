package org.usfirst.frc.team5687.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5687.robot.Constants;
import org.usfirst.frc.team5687.robot.RobotMap;
import org.usfirst.frc.team5687.robot.commands.RunIntakeManually;

/**
 * Class  for boulder intake subsystem
 * @author wil
 */
public class Intake extends Subsystem {

    /**
     * VictorSP speed controller for intake motor
     */
    private VictorSP intakeMotor;
    private AnalogInput boulderSensor;

    public Intake() {
        intakeMotor = new VictorSP(RobotMap.Intake.INTAKE_MOTOR);
        intakeMotor.setInverted(true);
        boulderSensor = new AnalogInput(RobotMap.Intake.INFARED_SENSOR);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new RunIntakeManually());
    }

    /**
     * Checks if boulder is detected
     *
     * @return Whether or not the infrared sensor sees anything
     */
    public boolean isDetected() {
        if (boulderSensor.getValue() < Constants.detectionThreshhold) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if it is primed
     * @return Whether or not the boulder is primed
     */
    public boolean isPrimed() {
        if (Constants.primedThreshhold > boulderSensor.getValue() && boulderSensor.getValue()> Constants.capturedThreshhold) {
            return true;
        } else{
            return false;
        }
    }

    /**
     * Checks if boulder is captured
     * @return Whether or not the ball is captured
     */
    public boolean isCaptured(){
        if(boulderSensor.getValue()< Constants.capturedThreshhold && boulderSensor.getValue() > Constants.primedThreshhold){
            return true;
        } else {
            return false;
        }
    }
    /**
     * Moves sensor data to smart dashboard
     */
    public void SendDashboardData() {
        SmartDashboard.putNumber("IR distance", boulderSensor.getValue());
        if (!isDetected()){
            SmartDashboard.putString("Boulder is", "Not Detected");
        }
        if (!isPrimed() && isDetected()){
            SmartDashboard.putString("Boulder is", "Detected");
        }
        if (isPrimed()){
            SmartDashboard.putString("Boulder is","Primed");
        }
        if (isCaptured()){
            SmartDashboard.putString("Boulder is", "Captured");
        }
    }

    /**
     * Sets the speed of the intake
     * @param speed the desired speed value
     */
    public void setSpeed(double speed) {
        intakeMotor.set(speed);
    }
}
