package org.usfirst.frc.team5687.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5687.robot.Constants;
import org.usfirst.frc.team5687.robot.RobotMap;
import org.usfirst.frc.team5687.robot.commands.RunIntakeManually;
import org.usfirst.frc.team5687.robot.utils.Helpers;
import org.usfirst.frc.team5687.robot.Constants.InfraRedConstants;

/**
 * Class for boulder intake subsystem
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
        boulderSensor = new AnalogInput(RobotMap.Intake.INFRARED_SENSOR);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new RunIntakeManually());
    }

    /**
     * Checks if boulder is detected
     * @return Whether or not the infrared sensor sees anything
     */
    public boolean isDetected() {
         return boulderSensor.getValue() < Constants.InfraRedConstants.DETECTION_THRESHOLD;
    }

    /**
     * Checks if boulder is captured
     * @return Whether or not the ball is captured
     */
    public boolean isCaptured(){
        boolean withinTolerance = Helpers.IsValueWithinTolerance(
                boulderSensor.getValue(),
                InfraRedConstants.CAPTURED_OPTIMAL,
                InfraRedConstants.CAPTURED_TOLERANCE);

        boolean beyondCaptured = boulderSensor.getValue() > Constants.InfraRedConstants.CAPTURED_OPTIMAL;
        return withinTolerance || beyondCaptured;
    }

    /**
     * Checks if it is primed
     * @return Whether or not the boulder is primed
     */
    public boolean isPrimed() {
        return Helpers.IsValueWithinTolerance(boulderSensor.getValue(),
                                              InfraRedConstants.PRIMED_OPTIMAL,
                                              InfraRedConstants.PRIMED_TOLERANCE);
    }

    /**
     * Updates intake data to smart dashboard
     */
    public void updateDashboard() {
        SmartDashboard.putNumber("IR distance", boulderSensor.getValue());
        if (!isDetected()){
            SmartDashboard.putString("Boulder", "Not Detected");
        }
        else if (isCaptured()){
            SmartDashboard.putString("Boulder", "Captured");
        }
        else if (isPrimed()){
            SmartDashboard.putString("Boulder", "Primed");
        } else {
            SmartDashboard.putString("Boulder", "Detected");
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
