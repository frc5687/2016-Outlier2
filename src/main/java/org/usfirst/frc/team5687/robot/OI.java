package org.usfirst.frc.team5687.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5687.robot.commands.*;
import org.usfirst.frc.team5687.robot.utils.Gamepad;
import org.usfirst.frc.team5687.robot.utils.Helpers;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    private Gamepad gamepad;
    private Joystick joystick;

    // Drive Train Elements
    public static final int HORNS_DIRECTION = 1;
    public static final int INTAKE_DIRECTION = -1;
    private static int currentDirection = HORNS_DIRECTION; //Initial drive direction

    // Drive Train Buttons
    public static final int REVERSE = Gamepad.Buttons.BACK.getNumber();
    // Boulder Buttons
    public static final int CAPTURE = 3;
    public static final int BOWL = 2;
    public static final int PRIME = 5;
    public static final int UNPRIME = 6;
    public static final int FIRE = 1;
    // Camera switch
    public static int RESET_CAMERA = 7;

    /**
     * Create a new instance of the operator interface
     */
    public OI() {
        gamepad = new Gamepad(0);
        joystick = new Joystick(1);

        // Gamepad Buttons
        JoystickButton reverseButton = new JoystickButton(gamepad, REVERSE);
        // Joystick Buttons
        JoystickButton captureButton = new JoystickButton(joystick, CAPTURE);
        JoystickButton bowlButton = new JoystickButton(joystick, BOWL);
        JoystickButton primeButton = new JoystickButton(joystick, PRIME);
        JoystickButton unprimeButton = new JoystickButton(joystick, UNPRIME);
        JoystickButton fireButton = new JoystickButton(joystick, FIRE);
        JoystickButton resetCameraButton = new JoystickButton(joystick, RESET_CAMERA);

        // Drive Train Commands
        reverseButton.whenPressed(new ReverseDrive());
        // Boulder Commands
        captureButton.toggleWhenPressed(new CaptureBoulder());
        bowlButton.whenPressed(new Bowl());
        primeButton.whenPressed(new Prime());
        unprimeButton.whenPressed(new CancelPrime());
        fireButton.whenPressed(new Fire());
        // Reset Camera Command
        resetCameraButton.whenPressed(new ResetCamera());
    }

    /**
     * Gets the current direction of the drive
     * @return the direction, 1 for forward or -1 for reverse
     */
    public int getDirection() {
        return currentDirection;
    }

    /**
     * Sets the current direction of the drive
     * @param direction the desired direction of the drive
     */
    public void setDirection(int direction) {
        currentDirection = direction;
    }

    /**
     * Gets the desired speed for the left side of the drive
     * @return the control value for the right drive motors
     */
    public double getLeftSpeed(){
        if (currentDirection == INTAKE_DIRECTION) {
            return currentDirection * transformStickToSpeed(Gamepad.Axes.LEFT_Y);
        }
        return transformStickToSpeed(Gamepad.Axes.RIGHT_Y);
    }

    /**
     * Gets the desired speed for the right side of the drive
     * @return the control value for the right drive motors
     */
    public double getRightSpeed(){
        if (currentDirection == INTAKE_DIRECTION) {
            return currentDirection * transformStickToSpeed(Gamepad.Axes.RIGHT_Y);
        }
        return transformStickToSpeed(Gamepad.Axes.LEFT_Y);
    }

    /**
     * Gets the desired speed for the intake
     * @return the control value for the intake motor
     */
    public double getIntakeSpeed() {
        // Joystick's y-axis is set to control intake speed
        return Helpers.applyDeadband(joystick.getRawAxis(1), Constants.Deadbands.INTAKE_STICK);
    }

    /**
     * Gets the desired speed for the arms
     * @return the control value for the arms' motor
     */
    public double getArmsSpeed() {
        double value = Helpers.applyDeadband(gamepad.getRawAxis(Gamepad.Axes.LEFT_TRIGGER), Constants.Deadbands.ARMS) - Helpers.applyDeadband(gamepad.getRawAxis(Gamepad.Axes.RIGHT_TRIGGER), Constants.Deadbands.ARMS);
        SmartDashboard.putNumber("Arms Speed", value);
        return value;
    }

    /**
     * Get the requested stick position from the gamepad, apply deadband and sensitivity transforms, and return the result.
     * @param stick the gamepad axis to adjust and use
     * @return the adjusted control value from the gamepad
     */
    private double transformStickToSpeed(Gamepad.Axes stick) {
        double result = gamepad.getRawAxis(stick);
        result = Helpers.applyDeadband(result, Constants.Deadbands.DRIVE_STICK);
        result = Helpers.applySensitivityTransform(result);
        return result;
    }
}

