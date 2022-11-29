// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class ArcadeDrive extends CommandBase {
  private final double m_joystickForward;
  private final double m_joystickBackward;
  private final double m_joystickTurn;
  private final Drivetrain m_diffDrivetrain;
  //private final XboxController m_joystick;

  public ArcadeDrive(ArcadeDrive arcadeDrive, XboxController controller) {
    //declaring sub system dependancy 
    addRequirements(m_diffDrivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //Retrieving controller inputs*
    
    //Run Arcadedrive*

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
