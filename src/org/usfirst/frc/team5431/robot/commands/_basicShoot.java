package org.usfirst.frc.team5431.robot.commands;

import org.usfirst.frc.team5431.robot.Robot;
import org.usfirst.frc.team5431.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class _basicShoot extends Command 
{
	CANTalon leftFlyW = new CANTalon(RobotMap.LeftFly);
	CANTalon rightFlyW = new CANTalon(RobotMap.RightFly);
	CANTalon inputMotor = new CANTalon(RobotMap.shootInput);
			;
	public _basicShoot() {
		requires(Robot.Turret_Subsystem);
		// TODO Auto-generated constructor stub
	}


	@Override
	protected void initialize() 
	{
		// TODO Auto-generated method stub
		Robot.Turret_Subsystem.setShoot(.75);

	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		inputMotor.set(1);
		Robot.Turret_Subsystem.setShoot(.75);
		Timer.delay(3);
		this.end();
	}

	@Override
	protected boolean isFinished() {
	
		Robot.Turret_Subsystem.setShoot(0);
		inputMotor.set(0);
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		Robot.Turret_Subsystem.setShoot(0);
		inputMotor.set(0);
		// TODO Auto-generated method stub

	}

	@Override
	protected void interrupted() {
		Robot.Turret_Subsystem.setShoot(0);
		inputMotor.set(0);
		// TODO Auto-generated method stub

	}

}
