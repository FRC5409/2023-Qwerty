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
    arm.setTurnSpeed(speed);
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
    return (arm.getAbsPos() <= kArm.kBackwardLimit || arm.getAbsPos() >= kArm.kForwardLimit);
  }
}