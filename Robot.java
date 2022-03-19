/*
  2022 everybot code
  written by carson graf 
  don't email me, @ me on discord
*/

/*
  This is catastrophically poorly written code for the sake of being easy to follow
  If you know what the word "refactor" means, you should refactor this code
*/

package frc.robot;

import javax.lang.model.util.ElementScanner6;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser; 

public class Robot extends TimedRobot {
  
  //Definitions for the hardware. Change this if you change what stuff you have plugged in
  WPI_TalonSRX driveLeftA = new WPI_TalonSRX(0);
  WPI_TalonSRX driveLeftB = new WPI_TalonSRX(1);
  WPI_TalonSRX driveRightA = new WPI_TalonSRX(4);
  WPI_TalonSRX driveRightB = new WPI_TalonSRX(5);
  CANSparkMax arm = new CANSparkMax(2, MotorType.kBrushless);
  WPI_TalonSRX intake = new WPI_TalonSRX(3);

  Joystick driverController = new Joystick(0);
  Joystick manipulatorController = new Joystick(1);

  Encoder leftEncoder = new Encoder(1, 2);
  Encoder rightEncoder = new Encoder(3, 4);

  private final SendableChooser<Boolean> autChooser = new SendableChooser<Boolean>();

  //Constants for controlling the arm. consider tuning these for your particular robot
  final double armHoldUp = 0.08;
  final double armHoldDown = 0.13;
  final double armTravel = 0.5;

  final double armTimeUp = 0.5;
  final double armTimeDown = 0.35;

  //Varibles needed for the code
  boolean armUp = true; //Arm initialized to up because that's how it would start a match
  boolean burstMode = false;
  double lastBurstTime = 0;
  double driveSpeed = 1;

  double autoStart = 0;
  boolean getExtraBall = true; //SET TO RUN AUTO1 OR AUTO2!!


  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    //Configure motors to turn correct direction. You may have to invert some of your motors
    driveLeftA.setInverted(false);
    driveLeftB.setInverted(false);
    driveRightA.setInverted(true);
    driveRightB.setInverted(true);

    autChooser.addOption("Auto1", true);
    autChooser.addOption("Auto2", false);
    SmartDashboard.putData(autChooser);
    
    arm.setInverted(true);
    //arm.setIdleMode(IdleMode.kBrake);

    //add a thing on the dashboard to turn off auto if needed
  
  }

  @Override
  public void autonomousInit() {
    //get a time for auton start to do events based on time later
    autoStart = Timer.getFPGATimestamp();

  }

  /** This function is called periodically during autonomous. */
  //@Override
  public void autonomousPeriodic() {
    //arm control code. same as in teleop
    
    if(armUp){
      if(Timer.getFPGATimestamp() - lastBurstTime < armTimeUp){
        arm.set(0.5);
    }
      else{
        arm.set(armHoldUp);
      }
    }
    else{
      if(Timer.getFPGATimestamp() - lastBurstTime < armTimeDown){
        arm.set(-0.5);
      }
      else{
        arm.set(-armHoldUp);
      }
    }
    
    //get time since start of auto
    double autoTimeElapsed = Timer.getFPGATimestamp() - autoStart;

    //series of timed events making up the flow of auto
    Boolean m_autChooser = autChooser.getSelected();

    if(m_autChooser == true){

      if(autoTimeElapsed < 1){
        //spit out the ball for three seconds
        //intake.set(ControlMode.PercentOutput, -1);
      }
    
      else if(autoTimeElapsed < 1.75){
        //stop spitting out the ball and drive backwards *slowly* for three seconds
        //intake.set(ControlMode.PercentOutput, 0);
        driveLeftA.set(-0.614);
        driveLeftB.set(-0.614);
        driveRightA.set(-0.6);
        driveRightB.set(-0.6);
      }
    
      else if(autoTimeElapsed < 2.9){
        driveLeftA.set(-0.35);
        driveLeftB.set(-0.35);
        driveRightA.set(0.35);
        driveRightB.set(0.35);
      }

      else if(autoTimeElapsed < 3.9){
        driveLeftA.set(0.614);
        driveLeftB.set(0.614);
        driveRightA.set(0.6);
        driveRightB.set(0.6);
      }

      else if(autoTimeElapsed < 5.5){
        driveLeftA.set(-0.35);
        driveLeftB.set(-0.35);
        driveRightA.set(0.35);
        driveRightB.set(0.35);
      }

      else if(autoTimeElapsed < 6.75){
        driveLeftA.set(0.614);
        driveLeftB.set(0.614);
        driveRightA.set(0.6);
        driveRightB.set(0.6);
      }

      else if(autoTimeElapsed < 7.6){
        driveLeftA.set(-0.414);
        driveLeftB.set(-0.414);
        driveRightA.set(-0.4);
        driveRightB.set(-0.4);
      }

      else if(autoTimeElapsed < 9.1){
        driveLeftA.set(-0.35);
        driveLeftB.set(-0.35);
        driveRightA.set(0.35);
        driveRightB.set(0.35);
      }

      else if(autoTimeElapsed < 9.85){
        driveLeftA.set(0.614);
        driveLeftB.set(0.614);
        driveRightA.set(0.6);
        driveRightB.set(0.6);
      }

      else if(autoTimeElapsed < 10.35){
        driveLeftA.set(0.314);
        driveLeftB.set(0.314);
        driveRightA.set(0.3);
        driveRightB.set(0.3);
      }

      else{
        //do nothing for the rest of auto
        //intake.set(ControlMode.PercentOutput, 0);
        driveLeftA.set(0);
        driveLeftB.set(0);
        driveRightA.set(0);
        driveRightB.set(0);
      }

    }

    else if(m_autChooser == false){

      if(autoTimeElapsed < 1){
        //spit out the ball for three seconds
        //intake.set(ControlMode.PercentOutput, -1);
      }
    
      else if(autoTimeElapsed < 1.75){
        //stop spitting out the ball and drive backwards *slowly* for three seconds
        //intake.set(ControlMode.PercentOutput, 0);
        driveLeftA.set(-0.614);
        driveLeftB.set(-0.614);
        driveRightA.set(-0.6);
        driveRightB.set(-0.6);
      }

      else {
        //do nothing for the rest of auto
        driveLeftA.set(0);
        driveLeftB.set(0);
        driveRightA.set(0);
        driveRightB.set(0);
      }

    }
  }
    
    
    
    
  
  

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    //Set up arcade steer
    double forward = -driverController.getRawAxis(1);
    double turn = driverController.getRawAxis(4);

    //Change wheel speed
    if (driverController.getRawButton(5)){
      //Sets speed to full speed.
      driveSpeed = 0.2;
    } else if (driverController.getRawButton(6)){
      //Sets speed to percent of full speed.
      driveSpeed = 0.7;
    } else {
      driveSpeed = 1;
    }

    driveLeftA.set(ControlMode.PercentOutput, (forward + turn) * driveSpeed);
    driveLeftB.set(ControlMode.PercentOutput, (forward + turn) * driveSpeed);
    driveRightA.set(ControlMode.PercentOutput, (forward - turn) * driveSpeed);
    driveRightB.set(ControlMode.PercentOutput, (forward - turn) * driveSpeed);

    //Intake controls
    if(manipulatorController.getRawButton(6)){
      intake.set(ControlMode.PercentOutput, 1);
    }
    else if(manipulatorController.getRawButton(5)){
      intake.set(ControlMode.PercentOutput, -1);
    }
    else{
      intake.set(ControlMode.PercentOutput, 0);
    }

    //Arm Controls
    double armPower = manipulatorController.getRawAxis(1);

    arm.set(armPower * 0.4);
    
  }

      
  
  

  @Override
  public void disabledInit() {
    //On disable turn off everything
    //done to solve issue with motors "remembering" previous setpoints after reenable
    driveLeftA.set(ControlMode.PercentOutput, 0);
    driveLeftB.set(ControlMode.PercentOutput, 0 );
    driveRightA.set(ControlMode.PercentOutput, 0 );
    driveRightB.set(ControlMode.PercentOutput, 0 );
    arm.set( 0 );
    intake.set(ControlMode.PercentOutput, 0);
  }
    
}