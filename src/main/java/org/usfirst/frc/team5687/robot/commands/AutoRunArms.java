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

        double currentspeed = armsMotor.getSpeed();
        double time;
        public boolean isDown;
        private boolean armsThere = false;//TODO: Important: Ask if the isAtLimit method returns true when the arms are high or low

        public AutoRunArms(double speed, double time, boolean isDown){
            armsMotor = new VictorSP(RobotMap.Arms.ARMS_MOTOR);
            this.currentspeed = speed;
            this.time = time;
            armsMotor.setInverted(Constants.Cheval.ARM_MOTOR_INVERTED);
            requires(arms);
        }

        public AutoRunArms(double speed, double time){
            this.currentspeed = speed;
            this.time = time;
            requires(arms);
        }

        private void atTheLimit(){
            if(arms.isAtLimit() == true){
                armsThere = true;
            }
        }

        @Override
        protected void interrupted(){}

        @Override
        protected void initialize(){}
        @Override
        protected void execute(){armsMotor.set(desiredspeed);}
        @Override
        protected boolean isFinished() {
            if(armsThere){//TODO: Question: If I'm checking if the arms are there, what's the point in having set speed and time?
                return true;
            }
            else {
                return false;
            }
        }

        @Override
        protected void end(){}

    }