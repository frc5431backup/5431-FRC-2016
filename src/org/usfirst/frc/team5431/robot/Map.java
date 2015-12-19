package org.usfirst.frc.team5431.robot;

import edu.wpi.first.wpilibj.SerialPort.Port;

public class Map {
	
	//Motors by CAN id
	public static final int FrontLeftMotor = 4;
	public static final int RearLeftMotor = 2;
	public static final int FrontRightMotor = 5;
	public static final int RearRightMotor = 3;
	
	//Sensors
	public static final int GyroAnalogPort = 0;
	
	//Other
	public static final Port SersPort = Port.kOnboard;
	public static final int BaudRate = 9600;

	//Serial writings
	public static final String onBoot = "Booting up...";
	public static final String onConfigone = "Calibrating...";
	public static final String onConfigtwo = "Don't move me";
	
	public static void lcdWrite(String toWriteone)
	{
		lcdWrite(toWriteone," ");
	}
	
	public static void lcdWrite(String toWriteone, String toWritetwo)
	{
		Robot.serial.writeString("<LCDPRINT:>"+toWriteone+"<:LCDPRINTTWO:>"+toWritetwo+"<:LCDEOF:>");
	}
}
