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
        if (boulderSensor.getValue() < Constants.InfraRedConstants.DETECTION_THRESHOLD) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if it is primed
     * @return Whether or not the boulder is primed
     */
    public boolean isPrimeAble() {
        return Math.abs(boulderSensor.getValue()- Constants.InfraRedConstants.PRIMED_OPTIMAL)< Constants.InfraRedConstants.PRIMED_TOLERANCE;
    }

    /**
     * Checks if boulder is captured
     * @return Whether or not the ball is captured
     */
    public boolean isCaptured(){
         return ((Constants.InfraRedConstants.CAPTURED_OPTIMAL-boulderSensor.getValue()<Constants.InfraRedConstants.CAPTURED_TOLERANCE)||(boulderSensor.getValue()>Constants.InfraRedConstants.CAPTURED_OPTIMAL));
    }
    /**
     * Moves sensor data to smart dashboard
     */
    public void SendDashboardData() {
        SmartDashboard.putNumber("IR distance", boulderSensor.getValue());
        if (!isDetected()){
            SmartDashboard.putString("Boulder is", "Not Detected");
        } else {
            if (!isPrimeAble() && isDetected()){
                SmartDashboard.putString("Boulder is", "Detected");
            }
            if (isPrimeAble()){
                SmartDashboard.putString("Boulder is","Primeable");
            }
            if (isCaptured()){
                SmartDashboard.putString("Boulder is", "Captured");
            }
        }
    }

    /**
     * Sets the speed of the intake
     * @param speed the desired speed value
     */
    public void setSpeed(double speed) {
        intakeMotor.set(speed);
    }

    /**
     * Stops the intake motor
     */
    public void stop() {
        intakeMotor.set(0);
    }
}
