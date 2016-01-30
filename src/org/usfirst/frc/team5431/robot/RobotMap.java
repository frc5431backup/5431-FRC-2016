package org.usfirst.frc.team5431.robot;

/**
 * Class that defines the constant IDs of all the {@link Button Buttons}.
 * 
 * @see Robot
 * @see OI
 * */
public final class RobotMap {
	//to prevent instantiation
	private RobotMap(){}
	
	/**
	 * Constant with the button ID of the front right button.
	 * <p>
	 * Currently {@value #frontright}.
	 * @see Button
	 * @see Joystick
	 * */
	public static final int frontright = 2;
		/**
	 * Constant with the button ID of the front left button.
	 * <p>
	 * Currently {@value #frontleft}.
	 * @see Button
	 * @see Joystick
	 * */
	public static final int frontleft = 3;
		/**
	 * Constant with the button ID of the rear right button.
	 * <p>
	 * Currently {@value #rearright}.
	 * @see Button
	 * @see Joystick
	 * */
	public static final int rearright = 4;
		/**
	 * Constant with the button ID of the rear left button.
	 * <p>
	 * Currently {@value #rearleft}.
	 * @see Button
	 * @see Joystick
	 * */
	public static final int rearleft = 5;
		/**
	 * Constant with the button ID of the button that powers the {@link Intake}.
	 * <p>
	 * Currently {@value #intake}.
	 * @see Button
	 * @see Joystick
	 * @see Intake_Subsystem
	 * */
	public static final int intake = 6;
	
		/**
	 * Constant with the button ID of the button that turns the turret.
	 * <p>
	 * Currently {@value #TurnBase}.
	 * @see Button
	 * @see Joystick
	 * */
	
	//Not used currently (Depreciated)
	public static final int TurnBase = 7;
		/**
	 * Constant with the button ID that powers the left flywheel
	 * <p>
	 * Currently {@value #LeftFly}.
	 * @see Button
	 * @see Joystick
	 * */
	public static final int LeftFly = 8;
		/**
	 * Constant with the button ID that powers the right flywheel.
	 * <p>
	 * Currently {@value #RightFly}.
	 * @see Button
	 * @see Joystick
	 * */
	public static final int RightFly = 9;
}
