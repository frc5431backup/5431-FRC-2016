
package org.usfirst.frc.team5431.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team5431.robot.OI;
import org.usfirst.frc.team5431.robot.Robot;

/**
 *
 */
public class Teleop extends Command {

    public Teleop() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.DriveBase);
        requires(Robot.Intake_Subsystem);
        requires(Robot.Turret_Subsystem);
        //requires(Robot.Grip_Subsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.DriveBase.drive(OI.xbox.getRawAxis(OI.xboxYAxisL), OI.xbox.getRawAxis(OI.xboxYAxisR));
    	OI.intakeButton.toggleWhenPressed(Robot.Intake);
    	OI.turnTurretL.whenPressed(Robot._turnTurretL);
    	OI.turnTurretR.whenPressed(Robot._turnTurretR);
    	OI.shootButton.whenPressed(Robot._basicShoot);
    	
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
