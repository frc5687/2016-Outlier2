package org.usfirst.frc.team5687.robot;

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
         * Maximum accelerations per cycl
         */
        public static final double ACCELERATION_CAP = TIME_OF_ACCEL / CYCLES_PER_SECOND * 100;

    }
    public class sensitivityController {
        // NEVER SET ABOVE 1 OR BELOW 0
        public static final double sensitivityExponent = 0;
        // A mathematical way to determine the sensativity at low levels

    }

    public class Encoders {
        public class Defaults {
            public static final boolean REVERSED = false;
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
            public static final double MAX_PERIOD = Defaults.MAX_PERIOD;
        }

        public class LeftDrive {
            public static final boolean REVERSED = true;
            public static final double INCHES_PER_PULSE = Defaults.INCHES_PER_PULSE;
            public static final double MAX_PERIOD = Defaults.MAX_PERIOD;
        }
    }
}
