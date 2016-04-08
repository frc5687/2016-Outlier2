package org.usfirst.frc.team5687.robot.subsystems;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5687.robot.Constants;
import org.usfirst.frc.team5687.robot.RobotMap;

/**
 * Subsystem for lifting / lowering intake system
 */
public class IntakeLifter extends Subsystem {

    private VictorSP lifterMotor;
    private DigitalInput lowerLimitHall;
    private Counter lowerCounter;
    private DigitalInput upperLimitHall;
    private Counter upperCounter;

    public IntakeLifter() {
        lifterMotor = new VictorSP(RobotMap.Intake.LIFT_MOTOR);
        lowerLimitHall = new DigitalInput(RobotMap.Intake.LOWER_HALL);
        lowerCounter = new Counter(lowerLimitHall);
        upperLimitHall = new DigitalInput(RobotMap.Intake.UPPER_HALL);
        upperCounter = new Counter(upperLimitHall);
    }

    @Override
    protected void initDefaultCommand() {
        //setDefaultCommand(new PositionIntake()); // Switching to separated hold buttons
    }

    // Idle for present
    public void setSpeed(double speed) {
        boolean movingLower = speed < 0;
        if (movingLower && isAtLowerLimit() || !movingLower && isAtUpperLimit()) {
            lifterMotor.set(0);
        } else {
            lifterMotor.set(speed);
        }
    }

    public void lower() {
        lifterMotor.set(Constants.IntakeLifter.LOWER_SPEED);
    }

    public void raise() {
        lifterMotor.set(Constants.IntakeLifter.RAISE_SPEED);
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

    public boolean isLowerCounterSet() {
        return lowerCounter.get() > 0;
    }

    public void initLowerCounter() {
        lowerCounter.reset();
    }

    public boolean isUpperCounterSet() {
        return upperCounter.get() > 0;
    }

    public void initUpperCounter() {
        upperCounter.reset();
    }

    public void updateDashboard() {
        SmartDashboard.putBoolean("Lifter LowerLimit", isAtLowerLimit());
        SmartDashboard.putBoolean("Lifter UpperLimit", isAtUpperLimit());
    }

}
