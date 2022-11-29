// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drivetrain extends SubsystemBase {
  private static final double countsPerRevolution = 1;

  //Creating Motor Objects
  WPI_TalonSRX m_frontLeftMotor = new WPI_TalonSRX(Constants.DrivetrainConst.CanID.frontLeftMotor_CAN); //*
  WPI_TalonSRX m_frontRightMotor = new WPI_TalonSRX(Constants.DrivetrainConst.CanID.frontLeftMotor_CAN);

  WPI_TalonSRX m_rearLeftMotor = new WPI_TalonSRX(Constants.DrivetrainConst.CanID.rearRightMotor_Can);
  WPI_TalonSRX m_rearRightMotor = new WPI_TalonSRX(Constants.DrivetrainConst.CanID.rearRightMotor_Can);

  //Creating differential drive object
  //private DifferentialDrive m_differentialDrive;

  /** Creates a new Drivetrain. */
  public Drivetrain() {
    
    //setting up follow 
    m_rearLeftMotor.follow(m_frontLeftMotor);
    m_rearRightMotor.follow(m_frontRightMotor);

    DifferentialDrive m_differentialDrive = new DifferentialDrive(m_frontLeftMotor, m_frontRightMotor);

    //Definoing motors
    //private final VictorSPX victorMotor_0 = new VictorSPX(0); //placeholder
    //private final VictorSPX victorMotor_1 = new VictorSPX(1);
    //private final VictorSPX victorMotor_2 = new VictorSPX(2);
    //private final VictorSPX victorMotor_3 = new VictorSPX(3);

    //setting up follow
    //victorMotor_0.follow(victorMotor_1);
    //victorMotor_2.follow(victorMotor_3);

    //setting invert
    //victorMotor_0.setInverted(false);
    //victorMotor_2.setInverted(true);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
