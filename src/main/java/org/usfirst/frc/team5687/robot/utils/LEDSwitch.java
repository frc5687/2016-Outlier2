package org.usfirst.frc.team5687.robot.utils;

import edu.wpi.first.wpilibj.DigitalOutput;

/**
 * Specialized DigitialOutput class for use with on/off LED circuits.
 * This wrapper class simply adds tracking of the current state of the channel.
 */
public class LEDSwitch extends DigitalOutput {
    /**
     * The current state of the switch (on=true)
     */
    private boolean _state = false;

    /**
     * Create an instance of an LED switch output given a channel.
     *
     * @param channel the DIO channel to use for the digital output. 0-9 are
     *                on-board, 10-25 are on the MXP
     */
    public LEDSwitch(int channel) {
        super(channel);
    }

    /**
     * Set the value of the LEDSwitch (true=on, false=off).
     *
     * @param value true is on, off is false
     */
    public void set(boolean value) {
        _state = value;
        super.set(_state);
    }


    /**
     * Get the value of the LEDSwitch (true=on, false=off).
     */
    public boolean get() {
        return _state;
    }

}
