package org.usfirst.frc.team5687.robot.utils;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Handle input from standard Joysticks connected to the Driver Station. This
 * class handles standard input that comes from the Driver Station. Each time a
 * value is requested the most recent value is returned. There is a single class
 * instance for each joystick and the mapping of ports to hardware buttons
 * depends on the code in the driver station.
 */
public class Gamepad extends Joystick{
    /*
	 * Enumeration for the various analog axes
	 */
    public static enum Axes {
        LEFT_X(0),
        LEFT_Y(1),
        LEFT_TRIGGER(2),
        RIGHT_TRIGGER(3),
        RIGHT_X(4),
        RIGHT_Y(5),
        D_PAD_HORIZONTAL(6),
        D_PAD_VERTICAL(7);

        private final int axisValue;
        Axes(int axisValue) {
            this.axisValue = axisValue;
        }

        public int getAxisValue() {
            return axisValue;
        }
    }

    /*
     * Enumeration for the various buttons
     */
    public static enum Buttons {
        A(0),
        B(1),
        X(2),
        Y(3),
        LB(4),
        RB(5),
        BACK(6),
        START(7),
        LEFT_BUTTON(8),
        RIGHT_BUTTON(9);

        private final int buttonValue;
        Buttons(int buttonValue) {
            this.buttonValue = buttonValue;
        }

        public int getButtonValue() {
            return buttonValue;
        }
    }

    /*
     * Constructor
     */
    public Gamepad(int port) {
        super(port);
    }

    /*
     * Gets the raw value for the specified axis
     * @param Axes the desired gamepad axis
     * @return double the analog value for the axis
     */
    public double getRawAxis(Axes axis) {
        return super.getRawAxis(axis.ordinal());
    }

    /*
     * Checks if the specified button is pressed
     * @param Buttons the desired gamepad button
     * @return bool true if the button is pressed
     */
    public boolean getRawButton(Buttons button) {
        return super.getRawButton(button.ordinal() + 1);
    }
}
