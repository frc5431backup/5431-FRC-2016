
package org.usfirst.frc.team5431.robot.subsystems;

import org.usfirst.frc.team5431.robot.RobotMap;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveBase extends Subsystem {
	
    private static final DriveBase INSTANCE = new DriveBase();
	
    private CANTalon frontright = new CANTalon(RobotMap.frontright),
    		frontleft = new CANTalon(RobotMap.frontleft),
    		rearright = new CANTalon(RobotMap.rearright),
    		rearleft = new CANTalon(RobotMap.rearleft);
    		
    private RobotDrive drive;
    		
    public DriveBase()
    {
    	super();
    	drive = new RobotDrive(frontleft, rearleft, frontright, rearright);
    }
    
    public static DriveBase getInstance()
    {
    	return INSTANCE;
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
    	drive.tankDrive(left, right);
    }
    
    public void initDefaultCommand() {}
}

