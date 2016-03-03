package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5687.robot.Constants;
import org.usfirst.frc.team5687.robot.OI;
import org.usfirst.frc.team5687.robot.Robot;
import org.usfirst.frc.team5687.robot.RobotMap;
import org.usfirst.frc.team5687.robot.subsystems.Arms;



    /**
     * Command for basic manual-control of the arms
     * Created by Maya on 3/2/2016.
     */

public class AutoRunArms extends Command implements PIDOutput{
   Arms arms = Robot.arms;
   OI oi = Robot.oi;
   private VictorSP armsmotor;

   public double armrotationspeed;//TODO: fix so that has camelcase. Brag to john.
   double time;
   public boolean isDown;
   private boolean armsthere = false;

   public AutoRunArms(double speed, boolean isDown){//TODO: no longer need doubles speed or time
            armsmotor = new VictorSP(RobotMap.Arms.ARMS_MOTOR);
            this.armrotationspeed = speed;//TODO: ask: how do I make sure that the speed used here is armrotationspeed?
            armsmotor.setInverted(Constants.Cheval.ARM_MOTOR_INVERTED);
            requires(arms);
        }

   public AutoRunArms(double speed){
            this.armrotationspeed = speed;
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
        if (arms.belowTarget()) {//Arms are not there and are currently moving
           armsmotor.set(armrotationspeed); // Run the arms motor at armrotationspeed //TODO: Is .set correct?
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


    @Override
    public void pidWrite(double output){
        SmartDashboard.putNumber("Arm/PID output", output);
        armrotationspeed = output;
    }

    }