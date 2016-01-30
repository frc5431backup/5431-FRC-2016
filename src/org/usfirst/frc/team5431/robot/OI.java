package org.usfirst.frc.team5431.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 * <p>
 * Contains various constants for user input.
 * 
 * @see RobotMap
 * @see Robot
 * */
public class OI {
		/**
	 * Constant that defines the ID for the left, vertical {@link Button] on {@link #joy}. Currently {@value #joyLeftY}.
	 * 
	 * @see Robot
	 * */
	public static final int joyLeftY = 5;
		/**
	 * Constant that defines the ID for the left, vertical {@link Button] on {@link #joy}. Currently {@value #joyLeftY}.
	 * 
	 * @see Robot
	 * */
	public static final int joyRightY = 1;
	/**
	 * Constant that defines which {@link Button} of {@link Joystick} powers the intake. Used in {@link OI#intakeButton}.
	 * */
	public static final int joyYbutton = 4;
	
	/**
	 * {@link Joystick} instance that handles how fast the robot will shoot. Bound to channel 0.
	 * */
	public static final Joystick joy = new Joystick(0);
	
	/**
	 * Constant {@link Button} that controls the intake. Uses {@link OI#joy} and {@link OI#joyYbutton} in it's constructor.
	 * */
	public static final Button intakeButton = new JoystickButton(joy, joyYbutton); 
		
	
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);
    
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
	
}

