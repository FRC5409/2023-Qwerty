// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.Constants.kArm;

public class ArmRotation extends CommandBase
{
  private ArmSubsystem arm;
  private double speed;

  private boolean hasStarted;

  public ArmRotation(ArmSubsystem _arm, double _speed)
  {
    arm = _arm;
    speed = _speed;

    addRequirements(arm);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize()
  {
    if (speed > 0 && arm.getAbsPos() >= kArm.kForwardSoftLimit)
    {
      // nothing();
    }
    else if (speed < 0 && arm.getAbsPos() <= kArm.kBackwardSoftLimit)
    {
      // nothing();
    }
    else
    {
      arm.setTurnSpeed(speed);
    }

    hasStarted = true;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted)
  {
    arm.setTurnSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished()
  {
    while (hasStarted == false)
    {
      // nothing();
    }

    return (arm.getAbsPos() <= kArm.kBackwardSoftLimit || arm.getAbsPos() >= kArm.kForwardSoftLimit);
  }
}