package org.usfirst.frc.team5687.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team5687.robot.RobotMap;

/**
 * Subsystem to control lights for vision tracking and shooter aid
 * @author wil
 */
public class Lights extends Subsystem {
    private Solenoid flashlight;
    private Solenoid ringLight;

    public Lights() {
        flashlight = new Solenoid(RobotMap.Lights.FLASHLIGHT);
        ringLight = new Solenoid(RobotMap.Lights.RINGLIGHT);
    }

    @Override
    protected void initDefaultCommand() {
    }

    public boolean getFlashlight() {
        return flashlight.get();
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

    public void toggleRingLight() {
        boolean state = ringLight.get();
        ringLight.set(!state);
    }

    public void turnRingLightOn() {
        ringLight.set(true);
    }

    public void turnRingLightOff() {
        ringLight.set(false);
    }
}
