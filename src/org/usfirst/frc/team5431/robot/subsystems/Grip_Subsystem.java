package org.usfirst.frc.team5431.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Grip_Subsystem extends Subsystem {
    
	private static final Grip_Subsystem INSTANCE = new Grip_Subsystem(); //Return itself
	private NetworkTable grip;
	private final double[] defaults = {0}; //Default value to return
	
	public Grip_Subsystem() {
		super(); //Start super class
		this.grip = NetworkTable.getTable("GRIP/vision"); //Get current GRIP network table
	}
	
	public void stop() {
		NetworkTable.shutdown(); //Shutdown table so we don't get dumb error
	}
	
	public double[] getX() {
		final double holesY[] = grip.getNumberArray("centerX", this.defaults);
		return (this.mult(holesY)) ? holesY : this.defaults; //Same for all below return array 0 if a problem
	}
	
	public double[] getY() {
		final double holesX[] = grip.getNumberArray("centerY", this.defaults);
		return (this.mult(holesX)) ? holesX : this.defaults;
	}
	
	public double[] distance() {
		final double objects[] = this.getX();
		final double distances[] = {0};
		int num = 0;
		for(double object : objects) { //Get distance for each object
			distances[num] = (double) 33.8569 * Math.pow(1.007, object);
			num++;
		}
		return distances;
	}
	
	//See if number is within two other numbers
    public boolean withIn(double num, double lower, double upper) {
    	return ((num >= lower) && (num <= upper));
    }
    
    /*
    public boolean withRange(double want, double num, double min, double max)
    {
    	min = num - min;
    	max = num + max;
    	return (want <= max && want >= min);
    }*/ // Not currently used
	
	public double[] fromCenter(double HalfSize) {
		final double objects[] = this.getX();
		final double distances[] = {0};
		int num = 0;
		for(double object : objects) { //Find distance from each object from the center
			distances[num] = object - HalfSize;
			num++;
		}
		return distances;
	}
	
    //check if returned array is good
    private boolean mult(double[] multi) {
    	try  {
    		return (multi[0] != 0 && multi.length >= 1); //If no error in array (Null) then return array
    	}
    	catch(Exception ignored) {
    		return false; //If error then return false to 
    	}
    }
	
	public double[] area() { //Get area for each object
		final double areas[] = grip.getNumberArray("area", this.defaults);
		return (this.mult(areas)) ? areas : this.defaults;
	}
	
	public double[] solidity() { //Get solidity of object 0-100
		final double solidities[] = grip.getNumberArray("solidity", this.defaults);
		return (this.mult(solidities)) ? solidities : this.defaults;
	}
	
	public static Grip_Subsystem getInstance() {
		return INSTANCE; //Return itself
	}
	
    public void initDefaultCommand() {} //Not needed
}

