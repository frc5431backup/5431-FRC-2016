package org.usfirst.frc.team5431.robot.commands;

import org.usfirst.frc.team5431.robot.subsystems.Intake_Subsystem;

import edu.wpi.first.wpilibj.command.Command;

public class Intake extends Command{

	private Intake_Subsystem intake;
	
	protected void initialize() {
		this.intake = new Intake_Subsystem();
		requires(this.intake);
	}

	
	protected void execute() {
		this.intake.suck();
		
	}

	
	protected boolean isFinished() {
		return false;
	}

	
	protected void end() 
	{
		this.intake.suck(0);
	}

	@Override
	protected void interrupted() 
	{
		this.intake.suck(0);
	}

}
