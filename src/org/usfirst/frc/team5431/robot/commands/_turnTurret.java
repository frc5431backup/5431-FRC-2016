package org.usfirst.frc.team5431.robot.commands;

import org.usfirst.frc.team5431.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class _turnTurret extends Command {

	private double turretPower;
	
	public _turnTurret(double power) 
	{
		requires(Robot.Turret_Subsystem);
		this.turretPower = power;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		if(this.turretPower < 0)
			Robot.Turret_Subsystem.turnRight(this.turretPower);
		else
			Robot.Turret_Subsystem.turnLeft(this.turretPower);
		Timer.delay(.5);
	}

	@Override
	protected void execute() {
		this.end();
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		Robot.Turret_Subsystem.stopTurn();
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		Robot.Turret_Subsystem.stopTurn();
	}

}
