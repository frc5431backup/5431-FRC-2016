
package org.usfirst.frc.team5431.robot.subsystems;

import org.usfirst.frc.team5431.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;


public class Turret_Subsystem extends Subsystem {

	
	//private CANTalon horiz, left, right;
	private CANTalon left, right;
	private static final Turret_Subsystem INSTANCE = new Turret_Subsystem();
	
	
	public Turret_Subsystem()
	{
		super();
		//this.horiz = new CANTalon(RobotMap.TurnBase); // Horizontal turning motor
		this.left = new CANTalon(RobotMap.LeftFly); // Left flywheel motor
		this.right = new CANTalon(RobotMap.RightFly); // Right flywheel motor
		//this.horiz.enable(); // Automatically called in init, but why not call it again just to make sure
		this.left.enable();
		this.right.enable();
		this.left.clearStickyFaults(); // Clears any values inside talon from previous executions
		this.right.clearStickyFaults();
		//this.horiz.clearStickyFaults();
		//this.horiz.enableBrakeMode(true); // Brake when no signal received. Prevent backdrive
		this.left.enableBrakeMode(false);
		this.right.enableBrakeMode(false);
	}
	
	public static Turret_Subsystem getInstance()
	{
		return INSTANCE;
	}
	
	public void turnLeft(double amount)
	{
		//this.horiz.set(amount); // Turn left at this power
	}
	
	public void turnRight(double amount)
	{
		//this.horiz.set(-amount); // Turn right at this power
	}
	
	public void stopTurn()
	{
		//this.horiz.set(0); // Brake
		
	}
	
	public void setShoot(double amount)
	{
		// Spin up the motors 
		this.left.set(-(amount));
		this.right.set(amount);
	}
	
	public void stopShoot()
	{
		// Brake the shooter
		this.left.set(0); 
		this.right.set(0);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

