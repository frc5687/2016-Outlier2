package org.usfirst.frc.team5687.robot.subsystems;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5687.robot.RobotMap;

/**
 * Subsystem to control lights for vision tracking and shooter aid
 * @author wil
 */
public class Lights extends Subsystem {
    private PWM flashlight;
    private PWM visionLight;

    public Lights() {
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

    public void updateDashboard() {
        SmartDashboard.putBoolean("lights/flashlight", flashlight.getRaw()>0);
        SmartDashboard.putBoolean("lights/ringlight", visionLight.getRaw()>0);
    }
}
