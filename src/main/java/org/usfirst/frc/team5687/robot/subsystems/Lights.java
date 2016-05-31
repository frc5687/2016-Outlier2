package org.usfirst.frc.team5687.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5687.robot.RobotMap;
import org.usfirst.frc.team5687.robot.utils.LEDSwitch;

/**
 * Subsystem to control lights for vision tracking and shooter aid
 * @author wil
 */
public class Lights extends Subsystem {
    private LEDSwitch flashlight;
    private LEDSwitch ringLight;

    public Lights() {
        flashlight = new LEDSwitch(RobotMap.Lights.FLASHLIGHT);
        ringLight = new LEDSwitch(RobotMap.Lights.RINGLIGHT);
    }

    @Override
    protected void initDefaultCommand() {
    }

    public boolean getFlashlight() {
        return flashlight.get();
    }


    public boolean getRingLight() {
        return ringLight.get();
    }

    public void turnFlashlightOn() {
        flashlight.set(true);
    }

    public void turnFlashlightOff() {
        flashlight.set(false);
    }

    public void toggleFlashlight() {
        boolean state = flashlight.get();
        flashlight.set(state);
    }

    public void toggleRingLight() {
        DriverStation.reportError("Ringlight toggle",false);

        boolean state = ringLight.get();
        ringLight.set(state);
    }

    public void turnRingLightOn() {
        DriverStation.reportError("Ringlight on",false);
        ringLight.set(true);
    }

    public void turnRingLightOff() {
        DriverStation.reportError("Ringlight off",false);
        ringLight.set(false);
    }

    public void updateDashboard() {
        SmartDashboard.putBoolean("lights/flashlight", flashlight.get());
        SmartDashboard.putBoolean("lights/ringlight", ringLight.get());
    }
}
