// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.Constants.kCANdle.AnimationTypes;
import frc.robot.subsystems.CandleSubsystem;

public class CandleCommand extends CommandBase {
  private final CandleSubsystem sys_candlesubystem;

  private double brightness;
  private int RGB_R, RGB_G, RGB_B;
  private AnimationTypes sys_toChange; 

  /** Creates a new CandleCommand. */
  public CandleCommand(CandleSubsystem candleSubsystem, double setBrightness, int r, int g, int b, AnimationTypes tochange){
    //Reconfiguring the brightness of the LED's
    sys_toChange = tochange;
    sys_candlesubystem = candleSubsystem; 
    brightness = setBrightness;
    RGB_R = r;
    RGB_G = g;
    RGB_B = b;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    sys_candlesubystem.configBrightness(brightness);
    sys_candlesubystem.setAnimation(sys_toChange, RGB_R, RGB_G, RGB_B);
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
    return true;
  }
}
