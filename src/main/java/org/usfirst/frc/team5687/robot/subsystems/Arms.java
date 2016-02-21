package org.usfirst.frc.team5687.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5687.robot.Constants;
import org.usfirst.frc.team5687.robot.RobotMap;
import org.usfirst.frc.team5687.robot.commands.RunArmsManually;
import org.usfirst.frc.team5687.robot.utils.Helpers;

/**
 * Class for arms subsystem for crossing non-static defenses by lifting or lowering arms
 */
public class Arms extends Subsystem {

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
        boolean movingUp = speed > 0;
        armsMotor.set((movingUp && isAtUpperLimit() ? 0 : Helpers.applySensitivityTransform(speed)));
    }

    public void moveUp() {
        armsMotor.set(Constants.Arms.ARMS_SPEED);
    }

    public void moveDown() {
        armsMotor.set(-Constants.Arms.ARMS_SPEED);
    }

    public void stop() {
        armsMotor.set(0);
    }

    /**
     * Check if arms is at upper limit
     * @return hall effect sensor of upper limit
     */
    public boolean isAtUpperLimit() {
        return !armsSensor.get();
    }

    public void updateDashboard() {
        SmartDashboard.putBoolean("Arms upper limit", isAtUpperLimit());
    }
}
