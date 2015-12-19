package org.usfirst.frc.team5431.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.vision.USBCamera;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.CameraServer;

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
	PowerDistributionPanel power;
	CameraServer camera;
	USBCamera usbCam;
	
	//Arduino Comm
	static SerialPort serial;
	
	//Below function is a mapper which is called on robot boot
    public void robotInit() 
    {
    	//Wiring
    	serial = new SerialPort(Map.BaudRate, Map.SersPort);
    	Map.lcdWrite(Map.onBoot);
    	power = new PowerDistributionPanel();
    	
    	//Camera (USB part)
    	usbCam = new USBCamera();
    	//Settings
    	usbCam.setFPS(10);
    	usbCam.setBrightness(50); //50% brightness
    	usbCam.setExposureAuto();
    	usbCam.setWhiteBalanceAuto();
    	
    	usbCam.updateSettings();
    	usbCam.openCamera();    //Start capturing
    	usbCam.startCapture();
    	
    	//Camera (Server/Dashboard part)
    	camera.setQuality(50); //50% quality
    	camera.setSize(20);
    	camera.startAutomaticCapture(usbCam);
    	
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
    	if(power.getVoltage() <= 11.5)
    	{
    		Map.lcdWrite("Power is low", "Replace battery");
    	}
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
