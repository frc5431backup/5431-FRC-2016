package org.usfirst.frc.team5431.robot.commands;

import org.usfirst.frc.team5431.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Auton extends Command{


	protected void initialize() {
	  
	}

	
	protected void execute() {
		Robot.Intake_Subsystem.suck();
		
	}

	
	protected boolean isFinished() {
		return false;
	}

	
	protected void end() 
	{
		Robot.Intake_Subsystem.suck(0);
	}

	@Override
	protected void interrupted() 
	{
		Robot.Intake_Subsystem.suck(0);
	}

}