
package org.usfirst.frc.team5431.robot;

import org.usfirst.frc.team5431.robot.commands.Teleop;
import org.usfirst.frc.team5431.robot.subsystems.DriveBase;
import org.usfirst.frc.team5431.robot.subsystems.Intake_Subsystem;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team5431.robot.commands.Shoot;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	//public static final Turret_Subsystem Turret_Subsystem = new Turret_Subsystem();
	public static OI oi;
	
    Command autonomousCommand, shooter;
    //SendableChooser chooser;
    public static final DriveBase DriveBase = new DriveBase();
    public static final Intake_Subsystem Intake_Subsystem = new Intake_Subsystem();
    NetworkTable butt;
    Command autonomousCommand;
    public static Command Intake;
     SendableChooser chooser;
     
     PowerDistributionPanel PDP;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
		oi = new OI();
        //chooser = new SendableChooser();
        shooter = new org.usfirst.frc.team5431.robot.commands.Shoot();
		//shooter.setRunWhenDisabled(true);
        shooter.start();
        
        PDP = new PowerDistributionPanel();
     	PDP.clearStickyFaults();
  		oi = new OI();
         chooser = new SendableChooser();
         chooser.addDefault("Default Auto", new Teleop());
         chooser.addObject("My Auto", new MyAutoCommand());
         SmartDashboard.putData("Auto mode", chooser);
         SmartDashboard.putNumber("Temperature", PDP.getTemperature());
         SmartDashboard.putNumber("Power", PDP.getTotalPower());
        //chooser.addDefault("Default Auto", new Shoot());
        
        //chooser.addObject("My Auto", new MyAutoCommand());
        //SmartDashboard.putData("Auto mode", chooser);
        //NetworkTable.setClientMode();
        //NetworkTable.setIPAddress("10.54.31.20");
        //butt = NetworkTable.getTable("GRIP/vision");
        /*
        USBCamera cam = new USBCamera("cam0");
        cam.openCamera(); 
        cam.setFPS(20);
        cam.setWhiteBalanceAuto();
        cam.updateSettings(); 
        
        CameraServer server = CameraServer.getInstance();
        server.setQuality(40);
        server.startAutomaticCapture(cam);*/
        //butt = NetworkTable
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
        //autonomousCommand = (Command) chooser.getSelected();
        
		/* String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		switch(autoSelected) {
		case "My Auto":
			autonomousCommand = new MyAutoCommand();
			break;
		case "Default Auto":
		default:
			autonomousCommand = new ExampleCommand();
			break;
		} */
    	
    	// schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
    }


    public void teleopInit()  {
        if (autonomousCommand != null) autonomousCommand.cancel();
        if (! shooter.isRunning()) shooter.start();
        shooter.start();
     
    }

    public void teleopPeriodic() {Scheduler.getInstance().run();}
    public void testPeriodic() {LiveWindow.run();}
    public void autonomousPeriodic() {Scheduler.getInstance().run();}
}
