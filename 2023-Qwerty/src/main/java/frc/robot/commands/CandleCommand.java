// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.CandleSubsystem;;

public class CandleCommand extends CommandBase {
  /** Creates a new CandleCommand. */
  private final CandleSubsystem m_candleSubsystem;

  public CandleCommand(CandleSubsystem candleSubsystem, double setBrightness){
      m_candleSubsystem = candleSubsystem;
      m_candleSubsystem.configCandle.setBrightness(setBrightness); //* CREATE wrappers in order to access the candle configurator subsystem

      addRequirements(candleSubsystem);
    }
  }


  public CandleCommand() {
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

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
