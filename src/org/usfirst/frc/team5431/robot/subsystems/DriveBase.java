
package org.usfirst.frc.team5431.robot.subsystems;

import org.usfirst.frc.team5431.robot.RobotMap;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveBase extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
    final CANTalon frontright = new CANTalon(RobotMap.frontright),
    		frontleft = new CANTalon(RobotMap.frontleft),
    		rearright = new CANTalon(RobotMap.rearright),
    		rearleft = new CANTalon(RobotMap.rearleft);
    
    public void initDefaultCommand() 
    {
    	
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void enable()
    {
    	frontright.enable();
    	frontleft.enable();
    	rearright.enable();
    	rearleft.enable();
    }
    public void disable()
    {
    	frontright.disable();
    	frontleft.disable();
    	rearright.disable();
    	rearleft.disable();
    }
    
    public void drive(double left, double right)
    {
    	frontright.set(right);
    	rearright.set(right);
    	frontleft.set(left);
    	rearleft.set(left);
    }
}

