// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.led.CANdle;

import edu.wpi.first.hal.CANData;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.ctre.phoenix.led.*;
import com.ctre.phoenix.led.CANdle.VBatOutputMode;
import com.ctre.phoenix.led.CANdle.LEDStripType;

public class CandleSubsystem extends SubsystemBase {
  /** Creates a new CandleSubsystem. */
  private final CANdle candle;

  public CandleSubsystem() {
    //setting Candle CANID
    candle = new CANdle(Constants.CANdle.CANdleCAN);

    CANdleConfiguration configCandle = new CANdleConfiguration();
    configCandle.stripType = LEDStripType.RGB;
    configCandle.brightnessScalar = 0.5;
    candle.configAllSettings(configCandle);
  }

  public void configBrightness(double brightness) {
    CANdleConfiguration configCandle = new CANdleConfiguration();
    configCandle.brightnessScalar = brightness; 
    candle.configAllSettings(configCandle);   
  } 


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
