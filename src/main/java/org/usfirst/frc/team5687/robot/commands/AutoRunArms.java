package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.Constants;
import org.usfirst.frc.team5687.robot.OI;
import org.usfirst.frc.team5687.robot.Robot;
import org.usfirst.frc.team5687.robot.RobotMap;
import org.usfirst.frc.team5687.robot.subsystems.Arms;



    /**
     * Command for basic manual-control of the arms
     * Created by Maya on 3/2/2016.
     */
    public class AutoRunArms extends Command {

        Arms arms = Robot.arms;
        OI oi = Robot.oi;
        double desiredspeed;
        private VictorSP armsMotor;

        armsMotor = new VictorSP(RobotMap.Arms.ARMS_MOTOR);
        double currentspeed = armsMotor.getSpeed();
        double time;
        double direction;
        public boolean isDown;


        public AutoRunArms(){requires(arms);}

        public AutoRunArms(double speed, double time, boolean isDown){
            this.currentspeed = speed;
            this.time = time;
            armsMotor.setInverted(Constants.Cheval.ARM_MOTOR_INVERTED);
        }
        public AutoRunArms(double speed, double time){
            this.currentspeed = speed;
            this.time = time;
        }


        //Method to check if the motor has run for the correct time and speed, declared in constants
        //Another or same method to check when the arms are moving back down


        @Override
        protected void interrupted(){}

        @Override
        protected void initialize(){}
        @Override
        protected void execute(){}
        @Override
        protected boolean isFinished(){
                if(arms.isAtLimit() == true){
                    return(true);
                }
            }

        @Override
        protected void end(){}

    }