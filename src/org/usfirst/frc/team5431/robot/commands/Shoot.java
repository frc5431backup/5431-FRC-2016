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
	public static final double screenHalf = 170; //Just divide the total res 340/2
	//Area to be within when to shoot
	public static final double minDistance = 90;
	public static final double maxDistance = 144;
	//Pixels to be within to shoot
	public static final double leftTrig = -10;
	public static final double rightTrig = 10;
	//Speed 0-1 for motor to turn
	public static final double motorSpeed = 0.15;
	public static final double shootSpeed = 0.9;
	
	//Choose hole options (Total should be one)
	private static final double areaNum = 0.2; //how important area is 
    	private static final double distNum = 0.2; //distance
    	private static final double solidNum = 0.4; //Solidity
    	private static final double fromNum = 0.2; //Distance from center of camera
	
	
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
    					(distances[toShoot] < minDistance) ? 1 : 2; //Get which direction to drive
    					
    			int lefight = (vision.withIn(tempCenter, leftTrig, rightTrig)) ? 0 :
    					(tempCenter < leftTrig) ? 1 : 2; //Amount to turn the turrent
    			
    			boolean Fire = ((forback == 0) && (lefight == 0)); //If in both them fire
    			
    			if(Fire) {
    				SmartDashboard.putString("FIRE", "YES FIRE!");
    				SmartDashboard.putString("PULL", "YES FIRE!");	
    				this.turret.setShoot(shootSpeed); //Set current fly wheel speeds
    			}
    			else {
    				this.aim(lefight); //Auto aim onto the target
    				SmartDashboard.putString("PULL", ((forback == 0) ? "" : (forback == 1) ? "Drive Back!" : "Drive Forward!")); //Display to the dashboard
    				
    			}
        	} else { //If returns 666, then hole isn't found
        		SmartDashboard.putString("FIRE", "HOLE NOT FOUND!");
        		this.turret.stopShoot(); //Stop fly wheels
        	}
    		
    	}catch(Exception error) {
    		SmartDashboard.putString("ERROR", "Error: running forever loop"); //Print to dashboard
    		}		
    	}
    }
    
    private void aim(double fromCenter) {
	if(fromCenter == 2) {
		this.turret.turnRight(motorSpeed); //turn to direction (Right)
		this.turret.setShoot(shootSpeed); //If in range then get ready to shoot
		SmartDashboard.putString("FIRE", "TURN RIGHT!");
	}
	else if(fromCenter == 1) {
		this.turret.turnLeft(motorSpeed); //turn to direction (Left)
		this.turret.setShoot(shootSpeed); //If in range then get ready to shoot
		SmartDashboard.putString("FIRE", "TURN LEFT!");
	}
	else if(fromCenter == 0) {
		this.turret.stopTurn(); //Your freaking there stop
		SmartDashboard.putString("FIRE", "STOP TURNING!");
	}
    }
    
    protected boolean isFinished() {
    	vision.stop(); 
    	stops = false;
        return (! (stops)); //Only return when the loop is done
    }

    private int chooseHole(int amount, double[] areas, double[] distances, double[] solidity, double[] fromCenter)
    {	
    	try
    	{
    		double holes[] = {0}; //Don't mess
    		double largest = 0; //Don't mess
    		int current = 0; //Don't mess
		
    		//If any of the values are negative make sure that they are positive
	    	for(int now = 0; now < amount; now++) {
	    		areas[now] = (areas[now] < 0) ? -(areas[now]) : areas[now];
	    		distances[now] = (distances[now] < 0) ? -(distances[now]) : distances[now];
	    		solidity[now] = (solidity[now] < 0) ? -(solidity[now]) : solidity[now];
	    		fromCenter[now] = (fromCenter[now] < 0) ? -(fromCenter[now]) : fromCenter[now];
	    	}
	    	
	    	//Calculate the values by multiplying the max values
	    	for(int now = 0; now < amount; now++) {
	    		holes[now] = ((areas[now]/2000) * areaNum) + ((1-(distances[now]/maxDistance)) * distNum)
	    		+ ((solidity[now]/100) * solidNum) + ((fromCenter[now]/screenHalf) * fromNum);
	    	}
	    	
	    	//See which hole was the largest and add that to the current hole
	    	for(int now = 0; now < holes.length; now++) {
	    		if(holes[now] > largest) {
	    			largest = holes[now];
	    			current = now;
	    		}
	    	}
	    	return current;
    	}
    	catch(Exception ignored) {
    		return 666; //Return 666 which means none found
    	}
    }
    
    //Close and remove all var
    protected void end() {
    	stops = false;
    	vision.stop(); //Stop NetworkTables
    }

    //Called when .cancel is called
    protected void interrupted() {
    	stops = false;
    	vision.stop(); //Stop NetworkTables
    }

}
