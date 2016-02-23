package org.usfirst.frc.team5687.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

    /**
     * Drive Train ports
     */
    public static class Drive {
        public static final int LEFT_MOTOR_FRONT = 0;
        public static final int LEFT_MOTOR_REAR = 1;
        public static final int RIGHT_MOTOR_FRONT = 2;
        public static final int RIGHT_MOTOR_REAR = 3;

        public static final int PDP_LEFT_MOTOR_FRONT = 14;
        public static final int PDP_LEFT_MOTOR_REAR = 15;
        public static final int PDP_RIGHT_MOTOR_FRONT = 12;
        public static final int PDP_RIGHT_MOTOR_REAR = 13;

        public static final int LEFT_ENCODER_CHANNEL_A = 8;
        public static final int LEFT_ENCODER_CHANNEL_B = 9;
        public static final int RIGHT_ENCODER_CHANNEL_A = 6;
        public static final int RIGHT_ENCODER_CHANNEL_B = 7;
    }

    /**
     * Shooter ports
     */
    public static class Shooter {
        public static final int WHEEL_MOTOR = 8;
    }

    /**
     * Intake ports
     */
    public static class Intake {
        public static final int INTAKE_MOTOR = 9;
        public static final int INFRARED_SENSOR = 3;
    }

    /**
     * Arms ports
     */
    public static class Arms {
        public static final int ARMS_MOTOR = 5;
        public static final int ARMS_POT = 2;
        public static final int ARMS_HALL = 1;
    }

    /**
     * Climber ports
     */
    public static class Climber {
        public static final int CLIMBER_MOTOR = 4;
    }

    /**
     * Lights ports
     */
    public static class Lights {
        public static final int FLASHLIGHT = 0;
        public static final int RINGLIGHT = 1;
    }

    /**
     * Camera ports
     */
    public static class Cameras {
        public static final String hornsEnd = "cam0";
        public static final String intakeEnd = "cam1";
    }
}
