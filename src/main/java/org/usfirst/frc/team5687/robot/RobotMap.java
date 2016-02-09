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
        public static final int LEFT_MOTORS = 0;
        public static final int RIGHT_MOTORS = 1;

        public static final int LEFT_ENCODER_CHANNEL_A = 8;
        public static final int LEFT_ENCODER_CHANNEL_B = 9;
        public static final int RIGHT_ENCODER_CHANNEL_A = 6;
        public static final int RIGHT_ENCODER_CHANNEL_B = 7;

        public static final int PDP_LEFT_MOTOR1 = 0;
        public static final int PDP_LEFT_MOTOR2 = 1;
        public static final int PDP_RIGHT_MOTOR1 = 2;
        public static final int PDP_RIGHT_MOTOR2 = 3;
    }

    /**
     * Shooter ports
     */
    public static class Shooter {
        public static final int WHEEL_MOTOR = 2;
    }

}
