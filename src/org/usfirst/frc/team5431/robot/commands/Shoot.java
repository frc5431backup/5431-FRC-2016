package org.usfirst.frc.team5431.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5431.robot.subsystems.Grip_Subsystem;
import org.usfirst.frc.team5431.robot.subsystems.Turret_Subsystem;

/*
 * This is the Shoot command, that runs a loop until the robot is disabled which calculates 
 * the current distance away from the tower the horizontal angle and the speed at which to
 * Shoot the boulder at...
 */

public class Shoot extends Command {
	
	Turret_Subsystem turret; // Subsystem to control the motors for the turret
	Grip_Subsystem vision; // Subsystem that pulls from NetworkTables and updates values from grip
	
	double[] areas = {0};
	double[] distances = {0};
	double[] fromCenters = {0};
	double[] holeSolids = {0};
	private boolean stops = true;
	
	
    public Shoot() {
    	try {
    		turret = Turret_Subsystem.getInstance();
    		vision = Grip_Subsystem.getInstance();
    	} catch(Exception ignored) {
    		
    		
    	}
        requires(turret);
        requires(vision);
    }

    protected void initialize() 
    {
    	turret.setShoot(0.25);
    	SmartDashboard.putNumber("MOTOR VALUES:", 1);
    }

    protected void execute() 
    {
    	while(true)
    	{
    		try {
    		areas = vision.area();
    		distances = vision.distance();
    		fromCenters = vision.fromCenter(170); //Half size of
    		holeSolids = vision.solidity();
    		int toShoot = this.chooseHole(areas.length, areas, distances, holeSolids, fromCenters);
    		SmartDashboard.putNumber("Hole Num:", toShoot);
    		if(toShoot != 666)
    		{
    			double tempCenter = vision.fromCenter(170)[toShoot];
        		SmartDashboard.putNumber("Hole area:", areas[toShoot]);
        		SmartDashboard.putNumber("Distance:", distances[toShoot]);
        		SmartDashboard.putNumber("From Center:", tempCenter);
        		SmartDashboard.putNumber("Solidity:", holeSolids[toShoot]);
    			
        		int ready = 0;
        		
    			if(vision.withIn(tempCenter, -10, 10) && vision.withIn(distances[toShoot], 100, 144))
        		{
        			SmartDashboard.putString("FIRE", "YES FIRE!");
        			turret.stopTurn();
        			ready += 1;
        		}
        		else if(tempCenter <= -10)
        		{
        			turret.stopShoot();
        			SmartDashboard.putString("FIRE", "TURN LEFT!");
        			this.aim(tempCenter);
        			/*
        			
        			while(! this.vision.withIn(tempCenter, -10, 10))
        			{
        				this.aim(tempCenter);
        			}
        			*/
        			
        		}
        		else if(tempCenter >= 10)
        		{
        			turret.stopShoot();
        			SmartDashboard.putString("FIRE", "TURN RIGHT!");
        			this.aim(tempCenter);
        			/*
        			while(! this.vision.withIn(tempCenter, -10, 10))
        			{
        				this.aim(tempCenter);
        			}
        			*/
        		}
        		else
        		{
        			SmartDashboard.putString("FIRE", "");
        			turret.stopTurn();
        		}
        		
        		if(vision.withIn(tempCenter, -10, 10) && vision.withIn(distances[toShoot], 100, 144))
        		{
        			SmartDashboard.putString("PULL", "YES FIRE!");	
        			ready += 1;
        			turret.stopTurn();
        		}
        		else if(distances[toShoot] <= 100)
        		{
        			SmartDashboard.putString("PULL", "DRIVE THE HELL BACKWARDS!");
        			turret.stopShoot();
        		}
        		else if(distances[toShoot] >= 144)
        		{
        			SmartDashboard.putString("PULL", "DRIVE THE HELL FORWARDS!");
        			turret.stopShoot();
        		}
        		else
        		{
        			SmartDashboard.putString("PULL", "");
        		}
        		
        		if(ready == 2)
        		{
        			turret.setShoot(0.9);
        		}
        		
        	}
        	else
        	{
        		SmartDashboard.putString("FIRE", "HOLE NOT FOUND!");
        	}
    		
    		}catch(Exception error)
    		{
    			SmartDashboard.putString("ERROR", "Error");
    	}		
      }
    }
    
    private void aim(double fromCenter)
    {
		if(fromCenter <= -10)
		{
			this.turret.turnRight(0.15);
			SmartDashboard.putString("NEGATE", "RIGHT");
		}
		else if(fromCenter >= 10)
		{
			this.turret.turnLeft(0.15);
			SmartDashboard.putString("NEGATE", "LEFT");
		}
		else
		{
			this.turret.stopTurn();
			SmartDashboard.putString("NEGATE", "STOP");
		}
    }
    
    protected boolean isFinished() {
    	vision.stop();
    	stops = false;
        return false;
    }

    private int chooseHole(int amount, double[] areas, double[] distances, double[] solidity, double[] fromCenter)
    {	
    	double areaNum = 0.2;
    	double distNum = 0.2;
    	double solidNum = 0.4;
    	double fromNum = 0.2;
    	
    	double holes[] = {0, 0, 0, 0};
    	
    	try
    	{
	    	for(int now = 0; now < amount; now++)
	    	{
	    		areas[now] = (areas[now] < 0) ? -(areas[now]) : areas[now];
	    		distances[now] = (distances[now] < 0) ? -(distances[now]) : distances[now];
	    		solidity[now] = (solidity[now] < 0) ? -(solidity[now]) : solidity[now];
	    		fromCenter[now] = (fromCenter[now] < 0) ? -(fromCenter[now]) : fromCenter[now];
	    	}
    		
	    	for(int now = 0; now < amount; now++)
	    	{
	    		holes[now] = ((areas[now]/2000) * areaNum) + ((distances[0]/144) * distNum)
	    		+ ((solidity[0]/100) * solidNum) + ((fromCenter[0]/170) * fromNum);
	    	}
	    	
	    	double largest = 0;
	    	int current = 0;
	    	
	    	
	    	for(int now = 0; now < holes.length; now++)
	    	{
	    		if(holes[now] > largest)
	    		{
	    			largest = holes[now];
	    			current = now;
	    		}
	    	}
	    	
	    	return current;
	    	
    	}
    	catch(Exception ignored)
    	{
    		return 666;
    	}
    }
    
    protected void end() {
    	stops = false;
    	vision.stop();
    }

    protected void interrupted() {
    	stops = false;
    	vision.stop();
    }

}
