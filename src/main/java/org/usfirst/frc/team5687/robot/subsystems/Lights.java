package org.usfirst.frc.team5687.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5687.robot.RobotMap;

/**
 * Subsystem to control lights for vision tracking and shooter aid
 * @author wil
 */
public class Lights extends Subsystem {
    private Solenoid flashlight;
    private Solenoid visionLight;

    public Lights() {
        flashlight = new Solenoid(RobotMap.Lights.FLASHLIGHT);
        visionLight = new Solenoid(RobotMap.Lights.RINGLIGHT);
    }

    @Override
    protected void initDefaultCommand() {
    }

    public boolean getFlashlight() {
        return flashlight.get();
    }


    public boolean getVisionLight() {
        return visionLight.get();
    }

    public void turnFlashlightOn() {
        flashlight.set(true);
    }

    public void turnFlashlightOff() {
        flashlight.set(false);
    }

    public void toggleFlashlight() {
        boolean state = flashlight.get();
        flashlight.set(!state);
    }

    public void toggleVisionLight() {
        boolean state = visionLight.get();
        visionLight.set(!state);
    }

    public void turnRingLightOn() {
        visionLight.set(true);
    }

    public void turnVisionLightOff() {
        visionLight.set(false);
    }

    public void updateDashboard() {
        SmartDashboard.putBoolean("lights/flashlight", flashlight.get());
        SmartDashboard.putBoolean("lights/ringlight", visionLight.get());
    }
}
