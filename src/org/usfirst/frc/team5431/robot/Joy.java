package org.usfirst.frc.team5431.robot;

import edu.wpi.first.wpilibj.Joystick;

/*
 * Call all global objects here so that mapping of each joystick could be done easily
 */

public class Joy {
	
	//Xbox controller mapping
	public static Joystick xbox = new Joystick(0); //Put usb order as 0 for
	
	//Axis
	public static final int leftX = 0;
	public static final int leftY = 1;
	public static final int rightX = 4;
	public static final int rightY = 5;
	
	//Axis controls
	public static final int joyPow = 3; //Must be ODD number to accept negatives
	public static final double joyEven = 0.3; //Amount to even out the curve
	//End xbox controller mapping
	
	

}
