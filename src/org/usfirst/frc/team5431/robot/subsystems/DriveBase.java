
package org.usfirst.frc.team5431.robot.subsystems;

import org.usfirst.frc.team5431.robot.RobotMap;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveBase extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    private static final DriveBase INSTANCE = new DriveBase();
	
    public CANTalon frontright = new CANTalon(RobotMap.frontright),
    		frontleft = new CANTalon(RobotMap.frontleft),
    		rearright = new CANTalon(RobotMap.rearright);
    		//rearleft = new CANTalon(RobotMap.rearleft);
    public Victor rearleft = new Victor(9);
    //private RobotDrive drive;
    		
    public DriveBase()
    {
    	super();
    	frontleft.setSafetyEnabled(false);
    	frontright.setSafetyEnabled(false);
    	rearleft.setSafetyEnabled(false);
    	rearright.setSafetyEnabled(false);
    	//drive = new RobotDrive(frontleft, rearleft, frontright, rearright);
    }
    
    public static DriveBase getInstance()
    {
    	return INSTANCE;
    }
    
    public void enable()
    {
    	
    	frontright.enableControl();
    	frontleft.enableControl();
    	rearright.enableControl();
    	//rearleft.enableControl();
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
    	//drive.tankDrive(left, right);
    	frontright.set(right);
    	frontleft.set(left);
    	rearright.set(right);
    	rearleft.set(left);
    }
    
    public void initDefaultCommand() {}
}

