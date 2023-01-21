// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.nio.file.attribute.AclEntry;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.opencv.features2d.FlannBasedMatcher;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drivetrain extends SubsystemBase {
  //Creating Motor Objects
  WPI_TalonSRX m_frontLeftMotor = new WPI_TalonSRX(Constants.kDrivetrain.kMotors.frontLeftMotor_CAN); //*
  WPI_TalonSRX m_frontRightMotor = new WPI_TalonSRX(Constants.kDrivetrain.kMotors.frontRightMotor_CAN);

  WPI_TalonSRX m_rearLeftMotor = new WPI_TalonSRX(Constants.kDrivetrain.kMotors.rearLeftMotor_CAN);
  WPI_TalonSRX m_rearRightMotor = new WPI_TalonSRX(Constants.kDrivetrain.kMotors.rearRightMotor_CAN);

  //Creating differential drive object
  private final DifferentialDrive m_differentialDrive;

  /** Creates a new Drivetrain. */
  public Drivetrain() {
    //Motor config
    m_rearLeftMotor.follow(m_frontLeftMotor);
    m_rearRightMotor.follow(m_frontRightMotor);

    m_frontLeftMotor.setInverted(true);
    m_frontRightMotor.setInverted(false);
    m_rearLeftMotor.setInverted(true);
    m_rearRightMotor.setInverted(false);

    m_frontLeftMotor.setNeutralMode(NeutralMode.Brake);
    m_frontRightMotor.setNeutralMode(NeutralMode.Brake);
    m_rearLeftMotor.setNeutralMode(NeutralMode.Brake);
    m_rearRightMotor.setNeutralMode(NeutralMode.Brake);

    m_frontLeftMotor.configOpenloopRamp(Constants.kDrivetrain.kMotors.rampRate);
    m_frontRightMotor.configOpenloopRamp(Constants.kDrivetrain.kMotors.rampRate);
    m_rearLeftMotor.configOpenloopRamp(Constants.kDrivetrain.kMotors.rampRate);
    m_rearRightMotor.configOpenloopRamp(Constants.kDrivetrain.kMotors.rampRate);

    m_differentialDrive = new DifferentialDrive(m_frontLeftMotor, m_frontRightMotor);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void arcadeDrive(double accelerate, double decelerate, double turn) {
    double xSpeed = accelerate - decelerate;
    driveShuffleBoard(xSpeed, turn);
    m_differentialDrive.arcadeDrive(xSpeed, turn);
  }

  public void driveShuffleBoard(double xSpeed, double turn){
    SmartDashboard.putNumber("Throttle", xSpeed);
    SmartDashboard.putNumber("Turn", turn);
  } 
}
