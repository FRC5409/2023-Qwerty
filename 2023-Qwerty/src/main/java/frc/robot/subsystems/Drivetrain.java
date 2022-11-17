// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {
  private static final double countsPerRevolution = 1;

  //defining motors
private final VictorSPX victorMotor_0 = new VictorSPX(0); //placeholder
private final VictorSPX victorMotor_1 = new VictorSPX(1);
private final VictorSPX victorMotor_2 = new VictorSPX(2);
private final VictorSPX victorMotor_3 = new VictorSPX(3);




  /** Creates a new Drivetrain. */
  public Drivetrain() {
    //following
    victorMotor_0.follow(victorMotor_1);
    victorMotor_2.follow(victorMotor_3);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
