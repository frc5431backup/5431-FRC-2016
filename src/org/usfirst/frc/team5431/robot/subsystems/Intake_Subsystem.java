package org.usfirst.frc.team5431.robot.subsystems;

import org.usfirst.frc.team5431.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Intake_Subsystem extends Subsystem{

	CANTalon Intake= new CANTalon(RobotMap.intake);
	
	public void thissucks()
	{
		Intake.set(1);
	}
	
	public void thissucks(int run)
	{
		Intake.set(run);	
	}
	
	public void stop()
	{
		Intake.set(0);
	}
	
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

}
