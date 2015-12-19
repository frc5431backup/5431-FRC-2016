package org.usfirst.frc.team5431.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.RobotDrive;

/*
 * Welcome to the main code for the FRC 5431 robotics team, this code involves mecanum
 * Which includes a Gyro, go ahead and take a look around
 * There are two other files you can look at called Joy = joysticks
 * And Map = Any hardware mapping
 */

public class Robot extends IterativeRobot 
	{
    /**
     * Place all global vars and objects here
     */
	
	//Joysticks
	Joystick xbox;
	
	
	//Motor controllers/Drives
	CANTalon frontLeft, rearLeft, frontRight, rearRight;
	RobotDrive drive;
	
	//Sensors
	Gyro gyro;
	
	//Arduino Comm
	static SerialPort serial;
	
	//Below function is a mapper which is called on robot boot
    public void robotInit() 
    {
    	//Wiring
    	serial = new SerialPort(Map.BaudRate, Map.SersPort);
    	Map.lcdWrite(Map.onBoot);
    	
    	//Joysticks
    	xbox = Joy.xbox;
    	
    	//Motor controllers
    	frontLeft = new CANTalon(Map.FrontLeftMotor);
    	rearLeft = new CANTalon(Map.RearLeftMotor);
    	frontRight = new CANTalon(Map.FrontRightMotor);
    	rearRight = new CANTalon(Map.RearRightMotor);
    	
    	//Sensors
    	gyro = new Gyro(Map.GyroAnalogPort);
    	
    	//Robot Drive
    	drive = new RobotDrive(frontLeft, rearLeft, frontRight, rearRight);
    	
    	RobotCalibration(); //Calibrate sensors to start

    }

    //Called when auton is on
    public void autonomousPeriodic() 
    {

    }

    //Called when teleop is on, no loops here or robot will lag, think of it like an arduino
    public void teleopPeriodic() 
    {
    	drive.mecanumDrive_Cartesian(getX(), getY(), getZ(), gyro.getAngle()); //Mecanum drive for robot
    }
    
    public double getX()
    {
    	double xVal = Math.pow(xbox.getRawAxis(Joy.leftX), Joy.joyPow); //non linear movement
    	return (xVal)+(Joy.joyEven*(xVal)) ; //Even out the curve just slightly (Same for others below)
    }
    
    public double getY()
    {
    	double yVal = Math.pow(xbox.getRawAxis(Joy.leftY), Joy.joyPow);
    	return (yVal)+(Joy.joyEven*(yVal));
    }
    
    public double getZ()
    {
    	xbox.setRumble(Joystick.RumbleType.kLeftRumble, (float) 1); //Just for fun
    	xbox.setRumble(Joystick.RumbleType.kRightRumble, (float) 1);
    	double zVal = Math.pow(xbox.getRawAxis(Joy.rightX), Joy.joyPow); //Currently the right X axis is going to be our Z axis
    	return (zVal)+(Joy.joyEven*(zVal));
    }
    
    public void RobotCalibration()
    {
    	//Serial line
    	serial.flush();
    	serial.reset();
    	Map.lcdWrite(Map.onConfigone, Map.onConfigtwo);
    	
    	//Gyro reset
    	gyro.initGyro();//Make sure robot is not moving during boot up period
    	if(gyro.getAngle() != 0) gyro.reset();
    }
    
    //Don't use it at all...
    public void testPeriodic(){}
    
}
