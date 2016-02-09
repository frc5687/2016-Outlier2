package org.usfirst.frc.team5687.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5687.robot.Constants;
import org.usfirst.frc.team5687.robot.RobotMap;
import org.usfirst.frc.team5687.robot.commands.RunShooterManually;

public class Shooter extends Subsystem {

    /**
     * Victor speed controller for the shooter motor
     */
    private VictorSP wheelMotor;
    private Encoder shooterWheelEncoder;

    /**
     * Constructor
     */
    public Shooter() {
        wheelMotor = new VictorSP(RobotMap.shooterWheelMotor);
        shooterWheelEncoder = initializeEncoder(RobotMap.shooterWheelEncoderChannelA, RobotMap.shooterWheelEncoderChannelB, Constants.Encoders.ShooterWheel.REVERSED, Constants.Encoders.ShooterWheel.INCHES_PER_PULSE);
    }

    /**
     * Set the shooter wheel speed
     * @param speed the desired speed control value
     */
    public void setSpeed(double speed) {
        wheelMotor.set(speed);
        SmartDashboard.putNumber("ShooterWheel speed", getShooterWheelRPM());
    }

    /**
     * Set the default command for the shooter subsystem
     */
    public void initDefaultCommand() {
        setDefaultCommand(new RunShooterManually());
    }

    private Encoder initializeEncoder(int channelA, int channelB, boolean reversed, double distancePerPulse) {
        Encoder encoder = new Encoder(channelA, channelB, reversed, Encoder.EncodingType.k4X);
        encoder.setDistancePerPulse(distancePerPulse);
        encoder.setMaxPeriod(Constants.Encoders.Defaults.MAX_PERIOD);
        encoder.setSamplesToAverage(Constants.Encoders.Defaults.SAMPLES_TO_AVERAGE);
        encoder.reset();
        return encoder;
    }

    public double getShooterWheelRPM() {
        return shooterWheelEncoder.getRate() / (Constants.Encoders.ShooterWheel.PULSES_PER_ROTATION * Constants.Encoders.ShooterWheel.INCHES_PER_PULSE);
    }
}
