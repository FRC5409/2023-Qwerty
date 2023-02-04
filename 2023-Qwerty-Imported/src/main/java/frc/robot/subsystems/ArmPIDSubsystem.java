// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import frc.robot.Constants;
import edu.wpi.first.networktables.GenericEntry;

public class ArmPIDSubsystem extends PIDSubsystem {
  private final CANSparkMax m_motor1;
  private final CANSparkMax m_motor2;
  private final DutyCycleEncoder m_encoder;
  private final ShuffleboardTab sb_armTab;
  private final GenericEntry kP,kI,kD,AbsolutePosition;
  // fix the genericentry import it

  /** Creates a new ArmPIDSubsystem. */
  public ArmPIDSubsystem() {
    super(new PIDController(Constants.kArmSubsystem.kP,Constants.kArmSubsystem.kI, Constants.kArmSubsystem.kD));

    m_motor1 = new CANSparkMax(Constants.kArmSubsystem.kMotor1ID, MotorType.kBrushless);
    m_motor2 = new CANSparkMax(Constants.kArmSubsystem.kMotor2ID, MotorType.kBrushless);
    m_encoder = new DutyCycleEncoder(Constants.kArmSubsystem.kEncoderChannel);

    getController().setTolerance(1);//This is your error and should be a constant that we tweak
    m_motor1.restoreFactoryDefaults();
    m_motor1.setIdleMode(IdleMode.kBrake);
    m_motor1.setSmartCurrentLimit(Constants.kArmSubsystem.kLimit);

    m_motor2.restoreFactoryDefaults();
    m_motor2.follow(m_motor1);
    m_motor2.setIdleMode(IdleMode.kBrake);
    m_motor2.setSmartCurrentLimit(Constants.kArmSubsystem.kLimit);

    sb_armTab = Shuffleboard.getTab("Arm");
    kP = sb_armTab.add("kP", 0).getEntry();
    kI = sb_armTab.add("kI", 0).getEntry();
    kD = sb_armTab.add("kD", 0).getEntry();
    AbsolutePosition = sb_armTab.add("AbsolutePosition", 0).getEntry();
    
  }

  @Override
  public void useOutput(double speed, double setpoint) {
    m_motor1.set(speed);
    m_controller.setSetpoint(setpoint);
  }

  @Override
  public double getMeasurement() {
    double ecd_value = m_encoder.get(); // absolute

    if (ecd_value < 0.3){
      AbsolutePosition.setDouble(ecd_value);
      return ecd_value + 1;
    }else{
      AbsolutePosition.setDouble(ecd_value);
      return ecd_value;
    }
    // Return the process variable measurement here 
  }

  public void setPIDFvalues(double kP, double kI, double kD){ // note theres no FF
    m_controller.setP(kP);
    m_controller.setI(kI);
    m_controller.setD(kD);

  }

  public void setPIDfromshuffleboard(){
    setPIDFvalues(kP.getDouble(0), kI.getDouble(0), kD.getDouble(0));
    System.out.println("kP:" + kP.getDouble(0) + " kI:" + kI.getDouble(0) + " kD:" + kD.getDouble(0));

  }

  public void resetEncoder(){
    m_encoder.reset();
  }

  //public PIDController geController(){
  //  return m_controller;
  //}
  
}



// notes
// make a command that gets and sets pid in the subsystem
// make three buttons, one that enables and sets to a position, one that sets it to another position, one that disables it 
// use if not isenable, enable for the button 

