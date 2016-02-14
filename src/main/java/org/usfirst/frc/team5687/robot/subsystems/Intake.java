package org.usfirst.frc.team5687.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
     * Moves sensor data to smart dashboard
     */
    public void SendDashboardData() {
        SmartDashboard.putNumber("IR distance", boulderSensor.getValue());
    }

    /**
     * Sets the speed of the intake
     * @param speed the desired speed value
     */
    public void setSpeed(double speed) {
        intakeMotor.set(speed);
    }

    /**
     * Returns true if the bouler has been captured, and false if it has not been captured
     */
    public boolean isCaptured() {
        return true; // Placeholder
    }
}
