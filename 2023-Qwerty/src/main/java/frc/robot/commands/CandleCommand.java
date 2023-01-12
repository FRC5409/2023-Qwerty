// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.CandleSubsystem;;

public class CandleCommand extends CommandBase {
  private final CandleSubsystem sys_candlesubystem;
  private double brightness;
  /** Creates a new CandleCommand. */

  public CandleCommand(CandleSubsystem candleSubsystem, double setBrightness){
      sys_candlesubystem = candleSubsystem; //* CREATE wrappers in order to access the candle configurator subsystem\
      brightness = setBrightness;
      addRequirements(sys_candlesubystem);
    }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    sys_candlesubystem.configBrightness(brightness);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
