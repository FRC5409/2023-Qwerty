// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import javax.sound.midi.ControllerEventListener;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.Controller;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class ArcadeDrive extends CommandBase {
  private final Drivetrain m_diffDrivetrain;
  private final XboxController m_controller;

  public ArcadeDrive(Drivetrain diffDrivetrain, XboxController controller) {
    //declaring sub system dependancy 
    m_diffDrivetrain = diffDrivetrain;
    m_controller = controller;

    addRequirements(m_diffDrivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //Retrieving controller inputs*
    double m_joystickForward = m_controller.getRightTriggerAxis();
    double m_joystickBackward = m_controller.getLeftTriggerAxis();
    double m_joystickTurn = m_controller.getLeftX();
    
    //Run Arcadedrive
    m_diffDrivetrain.arcadeDrive(m_joystickForward, m_joystickBackward, m_joystickTurn);

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
