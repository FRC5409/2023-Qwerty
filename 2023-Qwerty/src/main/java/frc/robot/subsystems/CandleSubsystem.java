// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.led.CANdle;

import edu.wpi.first.hal.CANData;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import pabeles.concurrency.ConcurrencyOps.NewInstance;

import javax.tools.ForwardingFileObject;

import com.ctre.phoenix.led.*;
import com.ctre.phoenix.led.CANdle.VBatOutputMode;
import com.ctre.phoenix.led.ColorFlowAnimation.Direction;
import com.ctre.phoenix.led.CANdle.LEDStripType;

public class CandleSubsystem extends SubsystemBase {
  /** Creates a new CandleSubsystem. */
  private final CANdle candle;
  //private final Animation animation;
  ColorFlowAnimation colorFlowAnimation;
  RainbowAnimation rainbowAnimation;

  public enum AnimationTypes{
    Static,
    Rainbow,
    ColorFlow
  }
  //Animation clearAnimation = new Animation.clearAnimation();
  //Animation clearAnimation = new Animation.clearAnimation(1);

  public CandleSubsystem() {
    //setting Candle CANID
    candle = new CANdle(Constants.CANdle.CANdleCAN);

    CANdleConfiguration configCandle = new CANdleConfiguration();
    configCandle.stripType = LEDStripType.GRBW;
    configCandle.brightnessScalar = .5;
    candle.configAllSettings(configCandle);
    candle.clearAnimation(0);
    //candle.setLEDs(252, 144, 3);
  }

  public void configBrightness(double brightness) {
    //CANdleConfiguration configCandle = new CANdleConfiguration();
    //configCandle.brightnessScalar = brightness; 
    //candle.configAllSettings(configCandle);   
    candle.configBrightnessScalar(brightness);
    System.out.printf("RGB brightness has been set to: %f%n", brightness);
  } 

  public void configColor(int r, int g, int b) {
    candle.setLEDs(r, g, b);
    System.out.printf("RGB color has been set to: %d, %d, %d%n", r, g, b);
  }

  public void setAnimation(AnimationTypes tochange) {
    switch(tochange){
      case Static: 
        candle.clearAnimation(0);
        candle.clearAnimation(1);
      case Rainbow: 
        rainbowAnimation = new RainbowAnimation(1, .5, Constants.CANdle.LEDCount);
        candle.clearAnimation(0);
        candle.animate(rainbowAnimation, 1);
      case ColorFlow:
        colorFlowAnimation = new ColorFlowAnimation(252, 144, 3, 0, .1, Constants.CANdle.LEDCount, Direction.Forward); 
        candle.clearAnimation(1);
        candle.animate(colorFlowAnimation, 0);
    }
     
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
