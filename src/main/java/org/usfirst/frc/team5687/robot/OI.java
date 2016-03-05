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
    public static final int RECOVER = 4;
    // Prime Speed Buttons
    public static final int LOW_PRIME_SPEED = 8; // 0.92
    public static final int DEFAULT_PRIME_SPEED = 7; // 0.94
    public static final int HIGH_PRIME_SPEED = 10; // 0.96
    public static final int EXTREME_PRIME_SPEED = 9; // 0.98
    // Lights Buttons
    public static final int SWITCH_RING_LIGHT = 12;
    public static final int SWITCH_FLASHLIGHT = 11;
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
        JoystickButton recoverButton = new JoystickButton(joystick, RECOVER);
        JoystickButton resetCameraButton = new JoystickButton(joystick, RESET_CAMERA);
        JoystickButton visionLightSwitch = new JoystickButton(joystick, SWITCH_RING_LIGHT);
        JoystickButton flashlightSwitch = new JoystickButton(joystick, SWITCH_FLASHLIGHT);

        JoystickButton lowSpeedButton = new JoystickButton(joystick, LOW_PRIME_SPEED);
        JoystickButton normalSpeedButton = new JoystickButton(joystick, DEFAULT_PRIME_SPEED);
        JoystickButton highSpeedButton = new JoystickButton(joystick, HIGH_PRIME_SPEED);
        JoystickButton extremeSpeedButton = new JoystickButton(joystick, EXTREME_PRIME_SPEED);

        // Drive Train Commands
        reverseButton.whenPressed(new ReverseDrive());
        // Boulder Commands
        captureButton.toggleWhenPressed(new CaptureBoulder());
        bowlButton.whenPressed(new Bowl());
        primeButton.whenPressed(new Prime());
        unprimeButton.whenPressed(new CancelPrime());
        fireButton.whenPressed(new Fire());
        recoverButton.whenPressed(new RecoverBoulder());
        // Light Switch Commands
        visionLightSwitch.whenPressed(new ToggleVisionLight());
        flashlightSwitch.whenPressed(new ToggleFlashlight());
        // Reset Camera Command
        resetCameraButton.whenPressed(new ResetCamera());

        lowSpeedButton.whenPressed(new SetShooterSpeed(Constants.Shooter.SHOOTER_SPEED_LOW));
        normalSpeedButton.whenPressed(new SetShooterSpeed(Constants.Shooter.SHOOTER_SPEED));
        highSpeedButton.whenPressed(new SetShooterSpeed(Constants.Shooter.SHOOTER_SPEED_HIGH));
        extremeSpeedButton.whenPressed(new SetShooterSpeed(Constants.Shooter.SHOOTER_SPEED_EXTREME));
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
        return (Helpers.applyDeadband(gamepad.getRawAxis(Gamepad.Axes.LEFT_TRIGGER), Constants.Deadbands.ARMS) - Helpers.applyDeadband(gamepad.getRawAxis(Gamepad.Axes.RIGHT_TRIGGER), Constants.Deadbands.ARMS)) * 0.9;
    }

    /**
     * Gets the desired speed for the climber
     * @return the value for the climber motor
     */
    public double getClimberSpeed() {
        if (gamepad.getRawButton(Gamepad.Buttons.X)) {
            return Constants.Climber.RAISE_SPEED;
        } else if (gamepad.getRawButton(Gamepad.Buttons.B)) {
            return Constants.Climber.LOWER_SPEED;
        }
        return 0;
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

