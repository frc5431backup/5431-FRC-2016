
package org.usfirst.frc.team5431.robot;

import org.usfirst.frc.team5431.robot.commands.Intake;
import org.usfirst.frc.team5431.robot.subsystems.DriveBase;
import org.usfirst.frc.team5431.robot.subsystems.Intake_Subsystem;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is 5431's FRC 2016 Stronghold Competition Code. 
 * This class is run first.
 */
public class Robot extends IterativeRobot {

	public static final DriveBase DriveBase = new DriveBase();	//Drive Base subsystem where drivebase motors are defined/used
	public static final Intake_Subsystem Intake_Subsystem = new Intake_Subsystem(); //Intake subsystem where intake motors are defined/used

    public static Command autonomousCommand, Intake;
    public static SendableChooser chooser;
    
    static Joystick joy;
    static Button intakeButton;
    static PowerDistributionPanel PDP;
    static RobotDrive drive;
    

    public void robotInit() {
    	//Auto chooser
        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", chooser);
        SmartDashboard.putData("Auto mode", chooser);
        
        //Power Distribution Panel
    	PDP = new PowerDistributionPanel();
    	PDP.clearStickyFaults();
        SmartDashboard.putNumber("Temperature", PDP.getTemperature());
        SmartDashboard.putNumber("Power", PDP.getTotalPower());
        
        //Joystick stuff
        joy = OI.joy;
        intakeButton = OI.intakeButton;
        
        DriveBase.enable();
        
        
    }
	
    public void autonomousInit() {
        autonomousCommand = (Command) chooser.getSelected();
        if (autonomousCommand != null) autonomousCommand.start();
        
    }

    public void teleopInit() {
        if (autonomousCommand != null) autonomousCommand.cancel();
		intakeButton.toggleWhenPressed(new Intake());
        
        while(true) {
        	try {
        		
        		double toDriveLeftY = joy.getRawAxis(OI.joyLeftY);
        		double toDriveRightY = joy.getRawAxis(OI.joyRightY);
        		
        		DriveBase.drive(toDriveLeftY, toDriveRightY);
        		
        	}catch(Exception imsorryimbad){}
        }
        
    }
    
    //Schedulers to update the above methods
	public void disabledPeriodic(){Scheduler.getInstance().run();}
    public void autonomousPeriodic(){Scheduler.getInstance().run();}
    public void teleopPeriodic(){Scheduler.getInstance().run();}
    public void testPeriodic(){LiveWindow.run();}
    public void disabledInit(){}
}
