package org.usfirst.frc.team5687.robot;

/**
 * Collection of constant/ calibration values for the robot
 * Created by Ben Bernard on 1/23/2016.
 */
public class Constants {
    public static final int CYCLES_PER_SECOND = 50;

    public class Deadbands {
        /**
         * Deadband threshold for drive joysticks
         */
        public static final double DRIVE_STICK = 0.1;
        public static final double SHOOTER_WHEELS = 0.1;
        public static final double INTAKE_STICK = 0.1;
        public static final double ARMS = 0.1;
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

    public class Autonomous {
        public static final double MIN_AUTO_TRAVERSE_ANGLE = 9; // the minimum angle the robot will pitch when traversing a defense
        public static final double MAX_AUTO_TRAVERSE_SECONDS = 5; //the maximum time the robot should need to travers a defense in Autonomous,
    }

    public class Encoders {
        public class Defaults {
            public static final boolean REVERSED = false;
            public static final int SAMPLES_TO_AVERAGE = 20;
            public static final int PULSES_PER_ROTATION = 2048;
            public static final double WHEEL_DIAMETER = 13;
            public static final double INCHES_PER_ROTATION = Math.PI * WHEEL_DIAMETER;
            public static final double SCALAR_RATIO = 1.4;
            public static final double INCHES_PER_PULSE = INCHES_PER_ROTATION * SCALAR_RATIO / PULSES_PER_ROTATION ;
            public static final double MAX_PERIOD = 5;
        }

        public class RightDrive {
            public static final boolean REVERSED = Defaults.REVERSED;
            public static final double INCHES_PER_PULSE = Defaults.INCHES_PER_PULSE;
        }

        public class LeftDrive {
            public static final boolean REVERSED = true;
            public static final double INCHES_PER_PULSE = Defaults.INCHES_PER_PULSE;
        }
    }
}
