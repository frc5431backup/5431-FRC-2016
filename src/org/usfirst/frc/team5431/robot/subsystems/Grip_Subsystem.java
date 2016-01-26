package org.usfirst.frc.team5431.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Grip_Subsystem extends Subsystem {
    
	private static final Grip_Subsystem INSTANCE = new Grip_Subsystem();
	private NetworkTable grip;
	private final double[] defaults = {0};
	
	public Grip_Subsystem()
	{
		super();
		
		this.grip = NetworkTable.getTable("GRIP/vision");
	}
	
	public void stop()
	{
		NetworkTable.shutdown();
	}
	
	public double[] getX()
	{
		final double holesY[] = grip.getNumberArray("centerX", this.defaults);
		return (this.mult(holesY)) ? holesY : this.defaults;
	}
	
	public double[] getY()
	{
		final double holesX[] = grip.getNumberArray("centerY", this.defaults);
		return (this.mult(holesX)) ? holesX : this.defaults;
	}
	
	public double[] distance()
	{
		final double objects[] = this.getX();
		final double distances[] = {0, 0, 0, 0, 0, 0};
		int num = 0;
		for(double object : objects)
		{
			distances[num] = (double) 33.8569 * Math.pow(1.007, object);
			num++;
		}
		return distances;
	}
	
    public boolean withIn(double num, double lower, double upper)
    {
    	return ((num >= lower) && (num <= upper));
    }
    
    public boolean withRange(double want, double num, double min, double max)
    {
    	min = num - min;
    	max = num + max;
    	return (want <= max && want >= min);
    }
	
	public double[] fromCenter(double HalfSize)
	{
		final double objects[] = this.getX();
		final double distances[] = {0, 0, 0, 0, 0, 0};
		int num = 0;
		for(double object : objects)
		{
			distances[num] = (double) (object - HalfSize);
			num++;
		}
		return distances;
	}
	
    private boolean mult(double[] multi)
    {
    	try 
    	{
    		return (multi[0] != 0 && multi.length >= 1);
    	}
    	catch(Exception ignored)
    	{
    		return false;
    	}
    }
	
	public double[] area()
	{
		final double areas[] = grip.getNumberArray("area", this.defaults);
		return (this.mult(areas)) ? areas : this.defaults;
	}
	
	public double[] solidity()
	{
		final double solidities[] = grip.getNumberArray("solidity", this.defaults);
		return (this.mult(solidities)) ? solidities : this.defaults;
	}
	
	public static Grip_Subsystem getInstance()
	{
		return INSTANCE;
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
    }
}

