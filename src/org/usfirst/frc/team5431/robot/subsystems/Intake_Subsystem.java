package org.usfirst.frc.team5431.robot.subsystems;

import org.usfirst.frc.team5431.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Intake_Subsystem extends Subsystem{

	final CANTalon Intake= new CANTalon(RobotMap.intake);
	
	public void suck()
	{
		suck(1);
	}
	
	public void suck(int run)
	{
		Intake.set(run);	
	}
	
	public void stopSucking()
	{
		Intake.set(0);
	}
	
	protected void initDefaultCommand() {
		
	}

}
