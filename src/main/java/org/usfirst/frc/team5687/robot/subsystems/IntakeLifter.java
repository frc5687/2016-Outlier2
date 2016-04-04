package org.usfirst.frc.team5687.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team5687.robot.Constants;
import org.usfirst.frc.team5687.robot.RobotMap;
import org.usfirst.frc.team5687.robot.commands.PositionIntake;

/**
 * Subsystem for lifting / lowering intake system
 */
public class IntakeLifter extends Subsystem {

    private VictorSP lifterMotor;
    private DigitalInput lowerLimitHall;
    private DigitalInput upperLimitHall;

    public IntakeLifter() {
        lifterMotor = new VictorSP(RobotMap.Intake.LIFT_MOTOR);
        lowerLimitHall = new DigitalInput(RobotMap.Intake.LOWER_HALL);
        upperLimitHall = new DigitalInput(RobotMap.Intake.UPPER_HALL);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new PositionIntake());
    }

    public void setSpeed(double speed) {
        lifterMotor.set(speed);
    }

    public void lower() {
        lifterMotor.set(Constants.IntakeLifter.OUTWARD_SPEED);
    }

    public void raise() {
        lifterMotor.set(Constants.IntakeLifter.INWARD_SPEED);
    }

    public void stop() {
        lifterMotor.set(0);
    }

    public boolean isAtLowerLimit() {
        return !lowerLimitHall.get();
    }

    public boolean isAtUpperLimit() {
        return !upperLimitHall.get();
    }

}
