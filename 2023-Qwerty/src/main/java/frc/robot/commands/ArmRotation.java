// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.ArmSubsystem;

public class ArmRotation extends CommandBase
{
  private ArmSubsystem arm;
  private CommandXboxController controller;

  public ArmRotation(ArmSubsystem _arm, CommandXboxController _controller)
  {
    arm = _arm;
    controller = _controller;

    addRequirements(arm);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute()
  {
    if (controller.a().getAsBoolean())
    {
      arm.setTurnSpeed(-0.05);
    }
    else if (controller.y().getAsBoolean())
    {
      arm.setTurnSpeed(0.05);
    }
    else
    {
      arm.setTurnSpeed(0);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
