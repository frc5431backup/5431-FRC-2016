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
	
	//Initial update arrays, they have a single val so we don't get thrown IndexOutOfBounds Error
	private double[] areas = {0}; //Area for every object
	private double[] distances = {0}; //Distance from object
	private double[] fromCenters = {0}; //Pixels from center line negate if to the left
	private double[] holeSolids = {0}; //How solid an object is (Make sure it's not a blob)
	private boolean stops = true; //Just in case if interrupted stop the while loop
	
	//Edit these values
	public static final screenHalf = 170; //Just divide the total res 340/2
	//Area to be within when to shoot
	public static final minDistance = 90;
	public static final maxDistance = 144;
	//Pixels to be within to shoot
	public static final leftTrig = -10;
	public static final rightTrig = 10;
	
	
    public Shoot() {
    	try {
    		turret = Turret_Subsystem.getInstance(); //Get instance just recalls the class within the subsystem on start
    		vision = Grip_Subsystem.getInstance(); // To get values from grip aka Network Tables
    	} catch(Exception ignored) {
    		SmartDashboard.putString("ERROR", "Error: Starting subsystems"); //Report back something is wrong
    	}
        requires(turret); //Use the subsystems in the Command
        requires(vision);
    }

    protected void initialize() // Init values like above
    {
    	turret.stopShoot(); //Don't waste battery life
    	SmartDashboard.putNumber("MOTOR VALUES:", 0); //Show that the motors aren't runninh
    }

    protected void execute() 
    {
    	while(stops) //Pretty much run until error occurs
    	{
    		try {
	    		areas = vision.area(); //Get array of all areas
	    		distances = vision.distance();
	    		fromCenters = vision.fromCenter(screenHalf); //Get distance away from center the 170 is the current half size of the screen
	    		holeSolids = vision.solidity();
	    		int toShoot = this.chooseHole(areas.length, areas, distances, holeSolids, fromCenters); //Chooses an object to shoot at(Method below)
	    		SmartDashboard.putNumber("Hole Num:", toShoot); //Display to dashboard what to shoot at
    			
    			if(toShoot != 666) //Don't shoot at nothing (THE DEVIL)
    		{
    			double tempCenter = vision.fromCenter(screenHalf)[toShoot]; //For some weird reason double array pulls the absolute value so we make a temp
        		
        		//Display values to SmartDashboard!
        		SmartDashboard.putNumber("Hole area:", areas[toShoot]);
        		SmartDashboard.putNumber("Distance:", distances[toShoot]);
        		SmartDashboard.putNumber("From Center:", tempCenter);
        		SmartDashboard.putNumber("Solidity:", holeSolids[toShoot]);
    			
    			int forback = (vision.withIn(distances[toShoot], minDistance, maxDistance)) ? 0 : 
    					(distances[toShoot] < minDistance) ? 1 : 2;
    					
    			int lefight = (vision.withIn(tempCenter, leftTrig, rightTrig)) ? 0 :
    					(tempCenter < leftTrig) ? 1 : 2;
    			
    			boolean Fire = ((forback == 0) && (lefight == 0));
    			
    			this.aim(tempCenter);
    			
    			
    			if(Fire)
    			{
    				SmartDashboard.putString("FIRE", "YES FIRE!");
    			}
        		
        		/*
        		int ready = 0; 
        		
    			if(vision.withIn(tempCenter, -10, 10) && vision.withIn(distances[toShoot], 100, 144))
        		{
        			turret.stopTurn();
        			ready += 1;
        		}*/
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
