package org.usfirst.frc.team5687.robot.subsystems;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5687.robot.RobotMap;

import java.awt.*;

/**
 * Subsystem to control lights for vision tracking and shooter aid
 * @author wil
 */
public class Lights extends Subsystem {
    private PWM flashlight;
    private PWM visionLight;

    private PWM redStrip;
    private PWM greenStrip;
    private PWM blueStrip;


    public Lights() {
        redStrip = new PWM(RobotMap.Lights.RED_STRIP);
        greenStrip = new PWM(RobotMap.Lights.GREEN_STRIP);
        blueStrip = new PWM(RobotMap.Lights.BLUE_STRIP);
        flashlight = new PWM(RobotMap.Lights.FLASHLIGHT);
        visionLight = new PWM(RobotMap.Lights.RINGLIGHT);
    }

    @Override
    protected void initDefaultCommand() {
    }

    public boolean getFlashlight() {
        return flashlight.getRaw()>0;
    }


    public boolean getVisionLight() {
        return visionLight.getRaw()>0;
    }

    public void turnFlashlightOn() {
        flashlight.setRaw(255);
    }

    public void turnFlashlightOff() {
        flashlight.setRaw(0);
    }

    public void toggleFlashlight() {
        boolean state = flashlight.getRaw()>0;
        flashlight.setRaw(state?0:255);
    }

    public void toggleVisionLight() {
        boolean state = visionLight.getRaw()>0;
        visionLight.setRaw(state?0:255);
    }

    public void turnRingLightOn() {
        visionLight.setRaw(255);
    }

    public void turnVisionLightOff() {
        visionLight.setRaw(0);
    }

    public void setStripColor(int red, int green, int blue) {
        redStrip.setRaw(red);
        greenStrip.setRaw(green);
        blueStrip.setRaw(blue);
    }

/*    public void setStripColor(Color color) {
        redStrip.setRaw(color.getRed());
        greenStrip.setRaw(color.getGreen());
        blueStrip.setRaw(color.getBlue());
    }
*/
    public void updateDashboard() {
        SmartDashboard.putBoolean("lights/flashlight", flashlight.getRaw()>0);
        SmartDashboard.putBoolean("lights/ringlight", visionLight.getRaw()>0);
    }
}
