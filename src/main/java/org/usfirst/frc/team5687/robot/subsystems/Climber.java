package org.usfirst.frc.team5687.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team5687.robot.RobotMap;
import org.usfirst.frc.team5687.robot.commands.ClimbManually;

/**
 * Subsystem for scaling the tower
 */
public class Climber extends Subsystem {
    private VictorSP climberMotor;

    public Climber() {
        climberMotor = new VictorSP(RobotMap.Climber.CLIMBER_MOTOR);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new ClimbManually());
    }

    public void setTapeMeasureSpeed(double speed) {
        climberMotor.set(speed);
    }

    public void stop() {
        climberMotor.set(0);
    }
}
