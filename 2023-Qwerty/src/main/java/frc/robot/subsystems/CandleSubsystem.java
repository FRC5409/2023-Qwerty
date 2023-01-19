// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.led.CANdle;

import edu.wpi.first.hal.CANData;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.kCANdle.AnimationTypes;
import pabeles.concurrency.ConcurrencyOps.NewInstance;

import java.sql.Time;

import javax.tools.ForwardingFileObject;

import com.ctre.phoenix.led.*;
import com.ctre.phoenix.led.CANdle.VBatOutputMode;
import com.ctre.phoenix.led.ColorFlowAnimation.Direction;
import com.ctre.phoenix.led.LarsonAnimation.BounceMode;
import com.ctre.phoenix.led.TwinkleAnimation.TwinklePercent;
import com.ctre.phoenix.led.TwinkleOffAnimation.TwinkleOffPercent;
import com.ctre.phoenix.led.CANdle.LEDStripType;
import com.ctre.phoenix.led.TwinkleAnimation;

public class CandleSubsystem extends SubsystemBase {
  /** Creates a new CandleSubsystem. */
  private final CANdle candle;
  //private final Animation animation;
  private ColorFlowAnimation colorFlowAnimation;
  private RainbowAnimation rainbowAnimation;
  private LarsonAnimation larsonAnimation;
  private TwinkleAnimation twinkleAnimation;

  private int currentAnimationSlot; 

  public CandleSubsystem() {
    //setting Candle CANID
    candle = new CANdle(Constants.kCANdle.kConfig.CANdleCAN);

    CANdleConfiguration configCandle = new CANdleConfiguration();
    configCandle.stripType = LEDStripType.GRBW;
    configCandle.brightnessScalar = .5;
    candle.configAllSettings(configCandle);
    candle.animate(null, 0);
  }

  public void configBrightness(double brightness) {
    candle.configBrightnessScalar(brightness);
    System.out.printf("RGB brightness has been set to: %f%n", brightness);
  } 

  public void configColor(int r, int g, int b) {
    candle.setLEDs(r, g, b);
    System.out.printf("RGB color has been set to: %d, %d, %d%n", r, g, b);
  }

  public void setAnimation(AnimationTypes tochange, int r, int g, int b) {
    switch(tochange){
      case Static: 
        candle.animate(null, currentAnimationSlot);
        currentAnimationSlot = 0; 
        Timer.delay(.1); //Candle cant keep up with script. Delay required in this situation
        configColor(r, g, b);
        break;
      case Clear:
        candle.animate(null, currentAnimationSlot);
        currentAnimationSlot = 0; 
        Timer.delay(.1);
        configColor(0, 0, 0);
        break;
      case Rainbow: 
        rainbowAnimation = new RainbowAnimation(1, .5, Constants.kCANdle.kConfig.LEDCount);
        candle.animate(null, currentAnimationSlot);
        currentAnimationSlot = 1;
        configColor(0, 0, 0);
        candle.animate(rainbowAnimation, 1);
        break;
      case ColorFlow:
        colorFlowAnimation = new ColorFlowAnimation(r, g, b, 0, .1, Constants.kCANdle.kConfig.LEDCount, Direction.Forward);
        candle.animate(null, currentAnimationSlot);
        currentAnimationSlot = 2;
        configColor(0, 0, 0);
        candle.animate(colorFlowAnimation, 2);
        break;
      case Larson:
        larsonAnimation = new LarsonAnimation(r, g, b, 0, .3, Constants.kCANdle.kConfig.LEDCount, BounceMode.Front, 3);
        candle.animate(null, currentAnimationSlot);
        currentAnimationSlot = 3;
        configColor(0, 0, 0);
        candle.animate(larsonAnimation, 3);
        break;
      case Twinkle:
        twinkleAnimation = new TwinkleAnimation(r, g, b, 0, .3, Constants.kCANdle.kConfig.LEDCount, TwinklePercent.Percent100);
        candle.animate(null, currentAnimationSlot);
        currentAnimationSlot = 4;
        configColor(0, 0, 0);
        candle.animate(twinkleAnimation, 4);
    }
    candle.setLEDs(0, 0, 0, 0, 0, 8);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
