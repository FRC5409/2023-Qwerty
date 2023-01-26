// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.MotorCommutation;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
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
  /** Creates a new ExampleSubsystem. */
  public ArmSubsystem() {
    m_motor1 = new CANSparkMax(Constants.kArmSubsystem.kMotor1ID, MotorType.kBrushless);
    m_motor2 = new CANSparkMax(Constants.kArmSubsystem.kMotor2ID, MotorType.kBrushless);
    m_encoder = new DutyCycleEncoder(Constants.kArmSubsystem.kEncoderChannel);
    m_PidController = new PIDController(0, 0, 0);

    sb_armTab = Shuffleboard.getTab("Arm");

    configMot();


  }


    
  
  public void configMot(){
    m_motor2.follow(m_motor1);
    m_motor1.restoreFactoryDefaults();
    m_motor1.setIdleMode(IdleMode.kBrake);
    m_motor1.burnFlash();
  }

  public void setPIDFvalues(double kP, double kI, double kD){
    
  }

  public boolean atSetpoint(){
    return m_controller.atSetpoint();
  }

  public void disable(){
    m_motor1.disable();
  }

  public void start(){
    m_motor1.set(0.1);
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
