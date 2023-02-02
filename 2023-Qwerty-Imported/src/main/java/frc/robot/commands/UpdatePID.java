// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.ArmPIDSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class UpdatePID extends CommandBase {
  private final ArmPIDSubsystem sys_arm;
  edu.wpi.first.networktables.GenericEntry kP,kI,kD;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public UpdatePID(ArmPIDSubsystem armPIDSubsystem) {
    sys_arm = armPIDSubsystem;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(sys_arm);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //Get PID values from shuffleboard
    sys_arm.setPIDFvalues(kP.getDouble(0), kI.getDouble(0), kD.getDouble(0));
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
