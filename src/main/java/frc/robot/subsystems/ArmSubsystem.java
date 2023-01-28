// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.Constants.kArm;
import frc.robot.Constants.kDrivetrain.kEncoder;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase
{
  private final CANSparkMax motor_1;
  private final CANSparkMax motor_2;

  private final DutyCycleEncoder abs_encoder;

  public ArmSubsystem()
  {
    motor_1 = new CANSparkMax(kArm.kMotorId1, MotorType.kBrushless);
    motor_2 = new CANSparkMax(kArm.kMotorId2, MotorType.kBrushless);

    motor_1.restoreFactoryDefaults();
    motor_2.restoreFactoryDefaults();

    motor_1.setIdleMode(IdleMode.kBrake);
    motor_2.setIdleMode(IdleMode.kBrake);

    motor_2.follow(motor_1);

    abs_encoder = new DutyCycleEncoder(kEncoder.kEncoderChannelId);
  }

  public void setTurnSpeed(double speed)
  {
    motor_1.set(speed);
  }

  public double getAbsPos()
  {
    return abs_encoder.get();
  }

  public void reset()
  {
    abs_encoder.reset();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
