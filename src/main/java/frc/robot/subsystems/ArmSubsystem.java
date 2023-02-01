// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.Constants.kArm;
import frc.robot.Constants.kDrivetrain.kEncoder;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase
{
  private final CANSparkMax motor_1;
  private final CANSparkMax motor_2;

  private final DutyCycleEncoder absEncoder;

  private final ShuffleboardTab armTab;
  private final GenericEntry entry_absEncPos;

  public ArmSubsystem()
  {
    motor_1 = new CANSparkMax(kArm.kMotorId1, MotorType.kBrushless);
    motor_2 = new CANSparkMax(kArm.kMotorId2, MotorType.kBrushless);

    motor_1.restoreFactoryDefaults();
    motor_2.restoreFactoryDefaults();

    motor_1.setIdleMode(IdleMode.kBrake);
    motor_2.setIdleMode(IdleMode.kBrake);

    motor_1.setSmartCurrentLimit(40);
    motor_2.setSmartCurrentLimit(40);

    motor_2.follow(motor_1);

    absEncoder = new DutyCycleEncoder(kEncoder.kEncoderChannelId);

    armTab = Shuffleboard.getTab("Arm");
    entry_absEncPos = armTab.add("Absolute Position", getAbsPos()).getEntry();
  }

  public double getAbsPos()
  {
    double curr_encVal = absEncoder.getAbsolutePosition();
    if (curr_encVal < 0.3)
    {
      return curr_encVal + 1;
    }
    else
    {
      return curr_encVal;
    }
  }

  public void setTurnSpeed(double speed)
  {
    motor_1.set(speed);
  }

  @Override
  public void periodic()
  {
    entry_absEncPos.setDouble(getAbsPos());
  }
}
