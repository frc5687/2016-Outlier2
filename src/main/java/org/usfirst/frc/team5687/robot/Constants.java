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
        public static final double MAX_AUTO_TRAVERSE_SECONDS = 5; // the maximum time the robot should need to travers a defense in Autonomous,
    }

    public class InfraRedConstants {

        public static final int DETECTION_THRESHOLD = 100;
        public static final int PRIMED_OPTIMAL = 1000; // Optimal position of boulder for Priming the Shooter Wheel
        public static final int PRIMED_TOLERANCE = 100; // Tolerance in either direction of boulder position for Priming
        public static final int CAPTURED_OPTIMAL = 1300; // Optimal position of boulder for storing bolder and traversing defenses
        public static final int CAPTURED_TOLERANCE = 200; // Tolerance in either direction of boulder position for carrying boulder
    }

    public class Horns {
        public static final double MAX_SPEED = .5; //Speed is Capped at this
        public static final double SESNITIVITY = 2; // Speed is divided by this
    }

    public class Intake {
        /**
         * Speed to run the intake roller to capture the boulder until detected
         */
        public static final double CAPTURE_SPEED = 1.0;

        /**
         * Speed to run the intake roller to prime the boulder
         */
        public static final double PRIME_SPEED = -0.8;

        /**
         * Speed to run the intake roller to unprime the boulder to captured position
         */
        public static final double UNPRIME_SPEED = 0.3;

        /**
         * Speed to run the intake to fire the boulder for shooting a high goal
         */
        public static final double FIRE_SPEED = 1.0;

        /**
         * Time allowed for a boulder to move away from the intake roller to shooter wheels for firing
         */
        public static final long FIRE_TIME = 1000;

        /**
         * Time it takes for a boulder to clear the intake roller when bowling (in milliseconds).
         */
        public static final long BOWL_TIME = 1000;

        /**
         * Speed to run the intake roller when bowling.
         */
        public static final double BOWL_SPEED = -1.0;
    }

    public class Shooter {
        /**
         * Time for running shooter wheels for priming
         */
        public static final long PRIME_TIME = 1750;

        /**
         * Time allowed for shooter wheels to stop before unpriming the boulder
         */
        public static final long UNPRIME_TIME = 1500;

        /**
         * Speed to run the shooter wheels to shoot high goal
         */
        public static final double SHOOTER_SPEED = 1.0;

        /**
         * Time allowed for shooter wheels to stop after firing to standby
         */
        public static final long STANDBY_TIME = 1500;
    }

    public class Encoders {
        public class Defaults {
            public static final boolean REVERSED = false;
            public static final int SAMPLES_TO_AVERAGE = 20;
            public static final int PULSES_PER_ROTATION = 2048;
            public static final double WHEEL_DIAMETER = 13;
            public static final double INCHES_PER_ROTATION = Math.PI * WHEEL_DIAMETER;
            public static final double SCALAR_RATIO = .45;
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
