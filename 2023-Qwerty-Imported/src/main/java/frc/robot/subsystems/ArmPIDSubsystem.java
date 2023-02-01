// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import frc.robot.Constants;

public class ArmPIDSubsystem extends PIDSubsystem {
  private final CANSparkMax m_motor1 = new CANSparkMax(Constants.kArmSubsystem.kMotor1ID, MotorType.kBrushless);
  private final CANSparkMax m_motor2 = new CANSparkMax(Constants.kArmSubsystem.kMotor2ID, MotorType.kBrushless);
  private final DutyCycleEncoder m_encoder = new DutyCycleEncoder(Constants.kArmSubsystem.kEncoderChannel);
  private final ShuffleboardTab sb_armTab;

  /** Creates a new ArmPIDSubsystem. */
  public ArmPIDSubsystem() {
    super(
        // The PIDController used by the subsystem
        new PIDController(Constants.kArmSubsystem.kP,Constants.kArmSubsystem.kI, Constants.kArmSubsystem.kP));
        
  }

  @Override
  public void useOutput(double output, double setpoint) {
    // Use the output here
  }

  @Override
  public double getMeasurement() {
    // Return the process variable measurement here
    return 0;
  }
}
