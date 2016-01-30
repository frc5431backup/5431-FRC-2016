
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
public final class Robot extends IterativeRobot {

	/**
	 * Drive Base subsystem where drivebase motors are defined/used
	 * 
	 * @see DriveBase
	 * */
	public static final DriveBase DriveBase = new DriveBase();
	
	/**
	 * Intake subsystem where intake motors are defined/used
	 * 
	 * @see Intake_Subsystem
	 * */
	public static final Intake_Subsystem Intake_Subsystem = new Intake_Subsystem(); 

	/**
	 * {@link Command} variable that is used in command-based autonomous.
	 * <p>
	 * The current command, as referenced by this variable, is called during autonomous. As the robot encounters new obstacles or problems, the command changes to accomodate it.
	 * */
	public static Command autonomousCommand;
	
	/**
	 * {@link Command} reference for the intake code.
	 * 
	 * @see Intake
	 * */
	public static Command Intake;
	/**
	 * {@link SendableChooser} which specifies what {@link Command} {@link #autonomousCommand} references.
	 * <p>
	 * Called during {@link #autonomousInit()}
	 * */
	public static SendableChooser chooser;

	/**
	 * Reference to {@link OI#joy} created in {@link #robotInit()}
	 * */
	static Joystick joy;
		/**
	 * Reference to {@link OI#intakeButton} created in {@link #robotInit()}
	 * */
	static Button intakeButton;
	/**
	 * {@link PowerDistributionPanel} created in {@link #robotInit()}
	 * */
	static PowerDistributionPanel PDP;
	static RobotDrive drive;

	/**
	 * Method called at the very beginning of a robot's life, or when the robot is turned on.
	 * */
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
	
	/**
	 * Called at the start of autonomous.
	 **/
	public void autonomousInit() {
		autonomousCommand = (Command) chooser.getSelected();
		if (autonomousCommand != null) 
			autonomousCommand.start();
	}
	
	/**
	 * Called at the start of teleoperated robot control.
	 * */
	public void teleopInit() {
		if (autonomousCommand != null) 
			autonomousCommand.cancel();
		
		intakeButton.toggleWhenPressed(new Intake());

		while(true) {
			try {
				final double toDriveLeftY = joy.getRawAxis(OI.joyLeftY);
				final double toDriveRightY = joy.getRawAxis(OI.joyRightY);

				DriveBase.drive(toDriveLeftY, toDriveRightY);
			}catch(Exception imsorryimbad){
				imsorryimbad.printStackTrace();
			}
		}

	}

	//Schedulers to update the above methods
	public void disabledPeriodic(){Scheduler.getInstance().run();}
	public void autonomousPeriodic(){Scheduler.getInstance().run();}
	public void teleopPeriodic(){Scheduler.getInstance().run();}
	public void testPeriodic(){LiveWindow.run();}
	public void disabledInit(){}
}
