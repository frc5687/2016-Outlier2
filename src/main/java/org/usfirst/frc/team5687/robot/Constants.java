package org.usfirst.frc.team5687.robot;

/**
 * Created by Ben Bernard on 1/23/2016.
 */
public class Constants {
    public static final int CYCLES_PER_SECOND = 50;

    public class Deadbands {
        public static final double DRIVE_STICK = 0.1;

    }

    public class Limits {
        /***
         * Minimum time (in milliseconds) it should take to go from 0 to 1 (stop to full)
         */
        public static final double TIME_OF_ACCEL = 25;

        /***
         * Maximum accelerations per cycle
         */
        public static final double ACCELERATION_CAP = TIME_OF_ACCEL / CYCLES_PER_SECOND * 100;


    }

    public class Calibration {
        /***
         * Controls the sensitivity algorithm.
         * 0 results in a linear control-to-speed relationship, while 1 results in cubed.
         *
         * NEVER SET ABOVE 1 OR BELOW 0
         */
        public static final double SENSITIVITY_FACTOR = 0.5;

    }
}
