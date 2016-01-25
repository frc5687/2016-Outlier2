package org.usfirst.frc.team5687.robot;

import org.usfirst.frc.team5687.robot.utils.Gamepad;
import org.usfirst.frc.team5687.robot.utils.Helpers;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 *
 */
public class OI {
    private Gamepad xbox;

    /**
     * Create a new instance of the operator interface
     */
    public OI() {
        xbox = new Gamepad(0);
    }

    /**
     * Gets the desired speed for the left side of the drive
     * @return the control value for the right drive motors
     */
    public double getLeftSpeed(){
        return transformStickToSpeed(Gamepad.Axes.LEFT_Y);
    }

    /**
     * Gets the desired speed for the right side of the drive
     * @return the control value for the right drive motors
     */
    public double getRightSpeed(){
        return transformStickToSpeed(Gamepad.Axes.RIGHT_Y);
    }

    /***
     * Get the requested stick position from the gamepad, apply deadpand and sensitivity transforms, and return the result.
     * @param stick the gamepad axis to adjust and use
     * @return the adjusted control value from the gamepad
     */
    private double transformStickToSpeed(Gamepad.Axes stick) {
        double result = xbox.getRawAxis(stick);
        result = Helpers.applyDeadband(result, Constants.Deadbands.DRIVE_STICK);
        result = Helpers.applySensitivityTransform(result);
        return result;
    }

    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.

    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
}

