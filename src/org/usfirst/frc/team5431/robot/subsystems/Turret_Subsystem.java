
package org.usfirst.frc.team5431.robot.subsystems;

import org.usfirst.frc.team5431.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Turret_Subsystem extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	private CANTalon horiz, left, right;
	private static final Turret_Subsystem INSTANCE = new Turret_Subsystem();
	
	
	private Turret_Subsystem()
	{
		super();
		this.horiz = new CANTalon(RobotMap.TurnBase);
		this.left = new CANTalon(RobotMap.LeftFly);
		this.right = new CANTalon(RobotMap.RightFly);
		this.horiz.enable();
		this.left.enable();
		this.right.enable();
		this.left.clearStickyFaults();
		this.right.clearStickyFaults();
		this.horiz.clearStickyFaults();
		this.horiz.enableBrakeMode(true);
		this.left.enableBrakeMode(false);
		this.right.enableBrakeMode(false);
	}
	
	public static Turret_Subsystem getInstance()
	{
		return INSTANCE;
	}
	
	public void turnLeft(double amount)
	{
		this.horiz.set(amount);
	}
	
	public void turnRight(double amount)
	{
		this.horiz.set(-amount);
	}
	
	public void stopTurn()
	{
		this.horiz.set(0);
		
	}
	
	public void setShoot(double amount)
	{
		this.left.set(-(amount));
		this.right.set(amount);
	}
	
	public void stopShoot()
	{
		this.left.set(0);
		this.right.set(0);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

