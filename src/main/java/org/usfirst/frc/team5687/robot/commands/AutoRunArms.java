package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5687.robot.Constants;
import org.usfirst.frc.team5687.robot.OI;
import org.usfirst.frc.team5687.robot.Robot;
import org.usfirst.frc.team5687.robot.subsystems.Arms;


    /**
     * Command for basic manual-control of the arms
     * Created by Maya on 3/2/2016.
     */

public class AutoRunArms extends Command {//TODO: get rid of PID

   Arms arms = Robot.arms;
   OI oi = Robot.oi;

   public double armrotationspeed;//TODO: fix so that has camelcase. Brag to john.
   public boolean isDown;
   double desiredAngle;
   private boolean armsthere = false;

   public AutoRunArms(boolean isDown){
       requires(arms);
       this.desiredAngle = Constants.Autonomous.ARMS_HIGH;
       arms.invertMotor();
       this.isDown = isDown;
        }

   public AutoRunArms(){
            requires(arms);
        }

   public void areArmsThere(){
       if(arms.atTarget()){
           armsthere = true;
       } else{
           armsthere = false;
       }
   }

   @Override
   protected void interrupted(){}

    @Override
    protected void initialize(){}

    @Override
    protected void execute() {
        areArmsThere();
        if (arms.isBelowTarget() && !isDown) {//Arms are not there and are currently moving
           arms.setSpeed(armrotationspeed);
        } // Run the arms motor at armrotationspeed
            else if (arms.isAboveTarget() && isDown){
            arms.setSpeed(-armrotationspeed);
            }
            else{
            arms.setSpeed(0);
            }
    }

    @Override
    protected boolean isFinished() {
        if(armsthere){
                return true;
        } else {
                return false;
            }
        }

    @Override
    protected void end(){}


    }