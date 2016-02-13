package org.usfirst.frc.team5687.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5687.robot.RobotMap;
import org.usfirst.frc.team5687.robot.commands.RunIntakeManually;

/**
 * Class for boulder intake subsystem
 * @author wil
 */
public class Intake extends Subsystem {

    /**
     * VictorSP speed controller for intake motor
     */
    private VictorSP intakeMotor;

    public Intake() {
        intakeMotor = new VictorSP(RobotMap.Intake.INTAKE_MOTOR);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new RunIntakeManually());
    }

    public static double getBoulderDistance(){
        return RobotMap.Intake.INFARED_SENSOR;
    }
    public static void SendDashboardData() {
        SmartDashboard.putNumber("IR distance", getBoulderDistance());
    }
    /**
     * Sets the speed of the intake
     * @param speed the desired speed value
     */
    public void setSpeed(double speed) {
        intakeMotor.set(speed);
    }
}
