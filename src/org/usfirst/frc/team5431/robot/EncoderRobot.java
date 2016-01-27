package org.usfirst.frc.team5431.robot;


import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This sample program shows how to control a motor using a joystick. In the operator
 * control part of the program, the joystick is read and the value is written to the motor.
 *
 * Joystick analog values range from -1 to 1 and speed controller inputs also range from -1
 * to 1 making it easy to work together. The program also delays a short time in the loop
 * to allow other threads to run. This is generally a good idea, especially since the joystick
 * values are only transmitted from the Driver Station once every 20ms.
 */
public class Robot extends SampleRobot {
	
    private SpeedController motor2;	// the motor to directly control with a joystick
    private Joystick stick;
    private SpeedController motor4;
    private SpeedController motor5;
    private SpeedController motor3;
    private Encoder encode;
    private Counter counter;
	private final double k_updatePeriod = 0.005; // update every 0.005 seconds/5 milliseconds (200Hz)

    public Robot() {
        motor2 = new CANTalon(2);
        motor4 = new CANTalon(4);
        motor3 = new CANTalon(3);
        motor5 = new CANTalon(5);// initialize the motor as a Talon on channel 0
        stick = new Joystick(0);	// initialize the joystick on port 0
        counter = new Counter(0);
        
//        encode = new Encoder(0, 1, false, EncodingType.k1X);
//        encode.setDistancePerPulse(1);
//        encode.setSamplesToAverage(7);
//        encode.reset();
    }
     /**
     * Runs the motor from a joystick.
     */
    public void operatorControl() {
    	int iterator = 10;
        while (isOperatorControl() && isEnabled()) {
        	//if(iterator<10)iterator++;
        	//else iterator=0;
//        	SmartDashboard.putNumber("Encoder Value", encode.get());
//        	SmartDashboard.putNumber("Area", encode.get());
//        	SmartDashboard.putNumber("Rate:", encode.getRate());
//        	SmartDashboard.putNumber("Encode Rate" + iterator, encode.getRate());
//        	SmartDashboard.putNumber("Encoder RPM" + iterator, (60*encode.getRate())/7); 
        	
        	final double counternum = counter.getPeriod();
        	if(counternum>0){SmartDashboard.putNumber("counter", counternum);
        	SmartDashboard.putNumber("Counter RotationsPM #"+iterator, (60/(7*counternum)));
    		SmartDashboard.putBoolean("counter is 0?", false);

        	}else{
        		SmartDashboard.putBoolean("counter is 0?", true);
        	}
        	       	// Set the motor's output.
        	// This takes a number from -1 (100% speed in reverse) to +1 (100% speed going forward)
        	//motor2.set(stick.getRawAxis(5)); 
        	motor2.set(0.1*iterator);
        	motor4.set(0.1*iterator);
        	motor3.set(0.1*iterator);
        	motor5.set(0.1*iterator);
        	//motor3.set(-(stick.getRawAxis(1)));
        	//motor5.set(stick.getRawAxis(1));
        	
            Timer.delay(k_updatePeriod);	// wait 5ms to the next update
        }
        
    }
}
