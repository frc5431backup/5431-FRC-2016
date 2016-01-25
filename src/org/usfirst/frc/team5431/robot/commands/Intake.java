package org.usfirst.frc.team5431.robot.commands;

import org.usfirst.frc.team5431.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Intake extends Command{

	
	protected void initialize() {
		// TODO Auto-generated method stub
		
	}

	
	protected void execute() {
		Robot.Intake_Subsystem.thissucks();
		
	}

	
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	
	protected void end() 
	{
		// TODO Auto-generated method stub
		Robot.Intake_Subsystem.thissucks(0);
	}

	@Override
	protected void interrupted() 
	{
		// TODO Auto-generated method stub
		Robot.Intake_Subsystem.thissucks(0);
	}

}
