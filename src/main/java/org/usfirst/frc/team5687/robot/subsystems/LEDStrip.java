package org.usfirst.frc.team5687.robot.subsystems;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5687.robot.RobotMap;

import org.usfirst.frc.team5687.robot.utils.*;


/**
 * Subsystem to control lights for vision tracking and shooter aid
 * @author wil
 */
public class LEDStrip extends Subsystem {
    private LEDController redStrip;
    private LEDController greenStrip;
    private LEDController blueStrip;


    public LEDStrip() {
        redStrip = new LEDController(RobotMap.Lights.RED_STRIP);
        greenStrip = new LEDController(RobotMap.Lights.GREEN_STRIP);
        blueStrip = new LEDController(RobotMap.Lights.BLUE_STRIP);
    }

    @Override
    protected void initDefaultCommand() {
        // setDefaultCommand(new PulseLEDStrip(Color.BLACK, Color.ORANGE, 700));
    }


    public void setStripColor(int red, int green, int blue) {
        redStrip.setRaw(red);
        greenStrip.setRaw(green);
        blueStrip.setRaw(blue);
        updateDashboard();
    }

    public void setStripColor(Color color) {
        redStrip.setRaw(color.getRed());
        greenStrip.setRaw(color.getGreen());
        blueStrip.setRaw(color.getBlue());
        updateDashboard();
    }

    public void updateDashboard() {
        SmartDashboard.putNumber("lights/red", redStrip.get());
        SmartDashboard.putNumber("lights/green", greenStrip.get());
        SmartDashboard.putNumber("lights/blue", blueStrip.get());
    }
}
