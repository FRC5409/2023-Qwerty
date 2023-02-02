// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.MotorCommutation;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ArmSubsystem extends SubsystemBase {
  private final CANSparkMax m_motor1;
  private final CANSparkMax m_motor2;
  private final DutyCycleEncoder m_encoder;
  private final PIDController m_PidController;
  private final ShuffleboardTab sb_armTab;
  private final GenericEntry kP, kI,kD;
  
  /** Creates a new ExampleSubsystem. */
  public ArmSubsystem() {
    m_motor1 = new CANSparkMax(Constants.kArmSubsystem.kMotor1ID, MotorType.kBrushless);
    m_motor1.restoreFactoryDefaults();
    m_motor1.setIdleMode(IdleMode.kCoast);
    m_motor1.burnFlash();
    m_motor1.setSmartCurrentLimit(Constants.kArmSubsystem.kLimit);

    m_motor2 = new CANSparkMax(Constants.kArmSubsystem.kMotor2ID, MotorType.kBrushless);
    m_motor2.restoreFactoryDefaults();
    m_motor2.follow(m_motor1);
    m_motor2.setIdleMode(IdleMode.kCoast);
    m_motor2.burnFlash();
    m_motor2.setSmartCurrentLimit(Constants.kArmSubsystem.kLimit);
    
    m_encoder = new DutyCycleEncoder(Constants.kArmSubsystem.kEncoderChannel);
    m_PidController = new PIDController(0, 0, 0);

    sb_armTab = Shuffleboard.getTab("Arm");

    kP = sb_armTab.add("kP",0).getEntry();



  }

  public void setPIDFvalues(double kP, double kI, double kD){
    m_PidController.setP(kP);
    m_PidController.setI(kI);
    m_PidController.setD(kD);
    
  }

  public double getPos(){
    return m_encoder.get();
  }

  public void start(){
    m_motor1.set(Constants.kArmSubsystem.kSpeed);
  }

  public void disable(){
    m_motor1.disable();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}