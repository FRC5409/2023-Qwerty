// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.ArmFeedforward;
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
 // private final ArmFeedforward m_ArmFeedforward;
  // fix the genericentry import it

  /** Creates a new ArmPIDSubsystem. */
  public ArmPIDSubsystem() {
    super(new PIDController(Constants.kArmSubsystem.kPID.kP,Constants.kArmSubsystem.kPID.kI, Constants.kArmSubsystem.kPID.kD));

    m_motor1 = new CANSparkMax(Constants.kArmSubsystem.kMotor1ID, MotorType.kBrushless);
    m_motor2 = new CANSparkMax(Constants.kArmSubsystem.kMotor2ID, MotorType.kBrushless);
    m_encoder = new DutyCycleEncoder(Constants.kArmSubsystem.kEncoderChannel);
  //  m_ArmFeedforward = new ArmFeedforward(getSetpoint(), getSetpoint(), getMeasurement(), getSetpoint());

    ArmFeedforward feedforward = new ArmFeedforward(getSetpoint(), getSetpoint(), getMeasurement(), getSetpoint());

    getController().setTolerance(0.1);//This is your error and should be a constant that we tweak
    m_motor1.restoreFactoryDefaults();
    m_motor1.setIdleMode(IdleMode.kBrake);
    m_motor1.setSmartCurrentLimit(Constants.kArmSubsystem.kCurrentLimit);

    m_motor2.restoreFactoryDefaults();
    m_motor2.follow(m_motor1);
    m_motor2.setIdleMode(IdleMode.kBrake);
    m_motor2.setSmartCurrentLimit(Constants.kArmSubsystem.kCurrentLimit);

    sb_armTab = Shuffleboard.getTab("Arm");
    kP = sb_armTab.add("kP", Constants.kArmSubsystem.kPID.kP).getEntry();
    kI = sb_armTab.add("kI", Constants.kArmSubsystem.kPID.kI).getEntry();
    kD = sb_armTab.add("kD", Constants.kArmSubsystem.kPID.kD).getEntry();
    AbsolutePosition = sb_armTab.add("AbsolutePosition", 0).getEntry();
    setPIDFvalues(Constants.kArmSubsystem.kPID.kP, Constants.kArmSubsystem.kPID.kI, Constants.kArmSubsystem.kPID.kD);
    
  }

  @Override
  public void useOutput(double voltage, double setpoint) {
    if (voltage > Constants.kArmSubsystem.kVoltageLimit){
      m_motor1.setVoltage(Constants.kArmSubsystem.kVoltageLimit);
    }
    else if (voltage < -Constants.kArmSubsystem.kVoltageLimit){
      m_motor1.setVoltage(-Constants.kArmSubsystem.kVoltageLimit);
    }
    else{
      m_motor1.setVoltage(voltage); // + m_ArmFeedforward.calculate(setpoint);
    }
    System.out.println(voltage);
  }

  @Override
  public double getMeasurement() {
    double ecd_value = m_encoder.getAbsolutePosition(); // absolute

    if (ecd_value < 0.3){
      AbsolutePosition.setDouble(ecd_value + 1);
      return ecd_value +1;
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
    System.out.println("kP:" + m_controller.getP() + " kI:" + kI.getDouble(0) + " kD:" + kD.getDouble(0));
  }

  public void resetEncoder(){
    m_encoder.reset();
  }

  public void givevoltage(double speed){
    m_motor1.setVoltage(getSetpoint());
    m_motor1.set(speed);
  }



  @Override
  public void periodic() {
      // TODO Auto-generated method stub
      super.periodic();
      getMeasurement();
  }

  //public PIDController geController(){
  //  return m_controller;
  //}
  
}



// notes
// convert speed to voltage 
// make a feed forward 
// do sisid ask jason
