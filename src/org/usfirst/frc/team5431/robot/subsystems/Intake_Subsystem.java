package org.usfirst.frc.team5431.robot.subsystems;

import org.usfirst.frc.team5431.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Intake_Subsystem extends Subsystem{

	final CANTalon Intake= new CANTalon(RobotMap.intake);
	public static final Intake_Subsystem INSTANCE = new Intake_Subsystem();
	
	public void suck() {
		this.suck(1); //Calling mesthod below, with int as argument
	}
	
	public void suck(int run) { //Turn on intake
		Intake.set(run);
	}
	
	public void stopSucking() { //Turns off intake system
		Intake.set(0);
	}

	protected void initDefaultCommand() {}
	public Intake_Subsystem getInstance() {return INSTANCE;}
	
}
