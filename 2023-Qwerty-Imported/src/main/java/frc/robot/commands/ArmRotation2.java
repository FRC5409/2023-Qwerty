// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.ArmPIDSubsystem;

public class ArmRotation2 extends CommandBase {
  private final ArmPIDSubsystem sys_arm;
  private double speed;
  private double setpoint;

  /** Creates a new ArmRotation2. */
  public ArmRotation2(ArmPIDSubsystem armPIDSubsystem, double speed, double setpoint) {
    sys_arm = armPIDSubsystem;
    this.speed = speed;
    this.setpoint = setpoint;

    addRequirements(sys_arm);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    sys_arm.useOutput(speed, setpoint);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    sys_arm.useOutput(0,setpoint);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
