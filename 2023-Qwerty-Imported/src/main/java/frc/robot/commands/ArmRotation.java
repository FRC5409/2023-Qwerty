// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.ArmSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ArmRotation extends PIDCommand {
  /** Creates a new ArmRotation. */
  public ArmRotation(double speed, ArmSubsystem m_arm) {
    super(
        // The controller that the command will use
        new PIDController(0, 0, 0),
        // This should return the measurement
        
        m_arm::getPos,
        // This should return the setpoint (can also be a constant)
        speed,
        // This uses the output
        output -> m_arm.start(), 
          // Use the output here
        m_arm);   
    addRequirements(m_arm);  
   // getController().enableContinuousInput(speed, speed); going to use
   getController().setTolerance(speed);
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return getController().atSetpoint();
  }
}
