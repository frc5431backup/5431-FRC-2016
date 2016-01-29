
package org.usfirst.frc.team5431.robot;

import org.usfirst.frc.team5431.robot.commands.*;
import org.usfirst.frc.team5431.robot.subsystems.*;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
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
	//public static final Grip_Subsystem Grip_Subsystem = new Grip_Subsystem(); //GRIP subsystem for processing data from GRIP
	public static final Turret_Subsystem Turret_Subsystem = new Turret_Subsystem(); //Turret subsystem with motors for turret
	//oi m8
	public static OI oi;

    Command autonomousCommand;
    Command teleopCommand;
    public static Command _turnTurretL = new _turnTurret(-.25); //A 'subcommand'. Do not seperately from TeleOp or Auton commands
    public static Command _turnTurretR = new _turnTurret(.25);
    public static Command _basicShoot; //This is a 'subcommand'. Do Not Use separate from TeleOp and Auto. Actually, this particular one you should NEVER use.
    public static Command Intake;
    SendableChooser chooser;
    
    PowerDistributionPanel PDP;
    
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	
    	PDP = new PowerDistributionPanel();
    	PDP.clearStickyFaults();
		oi = new OI();
        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", new Teleop());
        SmartDashboard.putData("Auto mode", chooser);
        SmartDashboard.putNumber("Temperature", PDP.getTemperature());
        SmartDashboard.putNumber("Power", PDP.getTotalPower());
        
        
    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    public void disabledInit(){

    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString code to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the chooser code above (like the commented example)
	 * or additional comparisons to the switch structure below with additional strings & commands.
	 */
    public void autonomousInit() {
        autonomousCommand = (Command) chooser.getSelected();
        
		
    	
    	// schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
    	teleopCommand = (Command) chooser.getSelected();
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
        if (teleopCommand != null) teleopCommand.start();
        
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }

}
