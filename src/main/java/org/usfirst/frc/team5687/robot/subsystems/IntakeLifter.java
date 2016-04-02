package org.usfirst.frc.team5687.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team5687.robot.RobotMap;
import org.usfirst.frc.team5687.robot.commands.PositionIntake;

/**
 * Subsystem for lifting / lowering intake system
 */
public class IntakeLifter extends Subsystem {

    private VictorSP lifterMotor;

    public IntakeLifter() {
        lifterMotor = new VictorSP(RobotMap.Intake.LIFT_MOTOR);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new PositionIntake());
    }

    public void setSpeed(double speed) {
        lifterMotor.set(speed);
    }

    public void stop() {
        lifterMotor.set(0);
    }
}
