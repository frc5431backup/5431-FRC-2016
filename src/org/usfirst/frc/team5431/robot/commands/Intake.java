package org.usfirst.frc.team5431.robot.commands;

import org.usfirst.frc.team5431.robot.subsystems.Intake_Subsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 * {@link Command} which defines the intake code and {@link Intake_Subsystem subsystem}.
 * */
public class Intake extends Command{

	private Intake_Subsystem intake;
	//flag for whether it is finished
	private boolean finished = false;
	
	/**
	 * Create the command. Needs to be called at least once.
	 * */
	protected void initialize() {
		finished=false;
		this.intake = new Intake_Subsystem();
		requires(this.intake);
	}

	/**
	 * Starts this command, grabbing the ball with the intake system.
	 * */
	protected void execute() {
		finished=false;
		this.intake.suck();
	}

	/**
	 * Returns whether this command has stopped running.
	 * */
	protected boolean isFinished() {
		return finished;
	}

	/**
	 * Stops this command and stop intaking the ball.
	**/
	protected void end() 
	{
		finished=true;
		this.intake.suck(0);
		
	}

	/**
	 * Called when this command is interrupted.
	 * */
	@Override
	protected void interrupted() 
	{
		end();
	}

}
