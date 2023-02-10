// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.led.CANdle;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.kCANdle;
import frc.robot.Constants.kCANdle.AnimationTypes;
import frc.robot.Constants.kCANdle.kColors;

import com.ctre.phoenix.led.*;
import com.ctre.phoenix.led.ColorFlowAnimation.Direction;
import com.ctre.phoenix.led.LarsonAnimation.BounceMode;
import com.ctre.phoenix.led.TwinkleAnimation.TwinklePercent;
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

  private int currentAnimationSlot = -1;
  
  private double timer = 0;
  private double animationTime = 0;

  private int[] LEDOff = {0, 0, 0};

  private int currentChargeLocation = 0;
  private int maxCharge = 0;

  public CandleSubsystem() {
    //setting Candle CANID
    candle = new CANdle(Constants.kCANdle.kConfig.CANdleCAN);

    CANdleConfiguration configCandle = new CANdleConfiguration();
    configCandle.stripType = LEDStripType.RGB;
    configCandle.brightnessScalar = 0.5;
    candle.configAllSettings(configCandle);
    candle.animate(null, 0);
    candle.setLEDs(0, 0, 0, 0, 0, 8);
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
        break;
      case sinWave:
        candle.animate(null, currentAnimationSlot);
        currentAnimationSlot = 5;
        LEDOff[0] = r;
        LEDOff[1] = g;
        LEDOff[2] = b;
        break;
      case sinFlow:
        candle.animate(null, currentAnimationSlot);
        currentAnimationSlot = 6;
        LEDOff[0] = r;
        LEDOff[1] = g;
        LEDOff[2] = b;
        break;
      case chargedUp:
        candle.animate(null, currentAnimationSlot);
        currentAnimationSlot = 7;
        animationTime = 0;
        for (int i = 0; i < 3; i++) {
          LEDOff[i] = 0;
        }
        break;
    }
    //setting the LEDs on the candle to off
    candle.setLEDs(0, 0, 0, 0, 0, 8);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    timer++;
    double brightness = map(candle.getTemperature(), 20, 70, 1, 0, true);
    if (currentAnimationSlot == -1) {
      for (int i = 0; i < 5; i++) {
        candle.animate(null, i);
      }
    }
    if (currentAnimationSlot == 5) {
      //update sin wave (not a sin funcction, Lex did a sin last year and so it should do the same just my way)
      animationTime++;
      if (animationTime % kCANdle.kColors.kSpeed == 0) {
        for (int i = 8; i < kCANdle.kConfig.LEDCount; i++) {
          if ((i + Math.floor(animationTime / kCANdle.kColors.kSpeed)) % (kCANdle.kColors.LEDSinCount * 2) < kCANdle.kColors.LEDSinCount) {
            candle.setLEDs((int) (kColors.idle[0] * brightness), (int) (kColors.idle[1] * brightness), (int) (kColors.idle[2] * brightness), 0, i, 1);
          } else {
            candle.setLEDs((int) (LEDOff[0] * brightness), (int) (LEDOff[1] * brightness), (int) (LEDOff[2] * brightness), 0, i, 1);
          }
        }
      }
    } else if (currentAnimationSlot == 6) {
      //old animation
      // animationTime = Math.round(Math.sin(timer * kCANdle.kColors.kFrequency) * kCANdle.kColors.kFrequencySpeed);
      // //find how to get these values
      // if (Math.abs(animationTime) == 19) {
      //   timer += 10;
      // }
      // for (int i = 8; i < kCANdle.kConfig.LEDCount; i++) {
      //   if ((i + animationTime) % (kCANdle.kColors.LEDSinCount * 2) < kCANdle.kColors.LEDSinCount) {
      //     candle.setLEDs((int) (kColors.idle[0] * brightness), (int) (kColors.idle[1] * brightness), (int) (kColors.idle[2] * brightness), 0, i, 1);
      //   } else {
      //     candle.setLEDs((int) (LEDOff[0] * brightness), (int) (LEDOff[1] * brightness), (int) (LEDOff[2] * brightness), 0, i, 1);
      //   }
      // }


      //new animation for robot arm

      animationTime = Math.round(Math.sin(timer * kCANdle.kColors.kFrequency) * kCANdle.kColors.kFrequencySpeed);
      // animationTime -= 0.5;

      int offset = 23;

      if (Math.abs(animationTime) == 19) {
        timer += 10;
      }

      for (int i = 8; i < kCANdle.kConfig.LEDOutter + 8; i++) {
        if ((i + animationTime) % (kCANdle.kColors.LEDSinCount * 2) < kCANdle.kColors.LEDSinCount) {
          LEDTurnOnAt(i, brightness);
        } else {
          LEDTurnOffAt(i, brightness);
        }
      }

      for (int i = offset; i < kCANdle.kConfig.LEDOutter + offset; i++) {
        if ((i + animationTime) % (kCANdle.kColors.LEDSinCount * 2) < kCANdle.kColors.LEDSinCount) {
          LEDTurnOnAt(i, brightness);
        } else {
          LEDTurnOffAt(i, brightness);
        }
      }

      //inner two

      for (int i = kCANdle.kConfig.LEDOutter + offset; i < kCANdle.kConfig.LEDInnerRight + kCANdle.kConfig.LEDOutter + offset; i++) {
        if ((i + animationTime) % (kCANdle.kColors.LEDSinCount * 2) < kCANdle.kColors.LEDSinCount) {
          LEDTurnOnAt(i, brightness);
        } else {
          LEDTurnOffAt(i, brightness);
        }
      }

      for (int i = kCANdle.kConfig.LEDOutter + offset + (kCANdle.kConfig.LEDInnerRight - kCANdle.kConfig.LEDInnerLeft); i < kCANdle.kConfig.LEDInnerRight + kCANdle.kConfig.LEDOutter + offset; i++) {
        if ((i + animationTime) % (kCANdle.kColors.LEDSinCount * 2) < kCANdle.kColors.LEDSinCount) {
          LEDTurnOnAt((kCANdle.kConfig.LEDInnerRight + kCANdle.kConfig.LEDOutter + offset - 2) * 2 - i, brightness);
        } else {
          LEDTurnOffAt((kCANdle.kConfig.LEDInnerRight + kCANdle.kConfig.LEDOutter + offset - 2) * 2 - i, brightness);
        }
      }

      for (int i = kCANdle.kConfig.LEDInnerRight + kCANdle.kConfig.LEDOutter + offset; i < kCANdle.kConfig.LEDInnerRight + kCANdle.kConfig.LEDOutter + offset + kCANdle.kConfig.LEDInnerLeft; i++) {
        if ((i + animationTime) % (kCANdle.kColors.LEDSinCount * 2) < kCANdle.kColors.LEDSinCount) {
          LEDTurnOnAt((kCANdle.kConfig.LEDInnerRight + kCANdle.kConfig.LEDOutter + offset + 26 - 2) * 2 - i, brightness);
        } else {
          LEDTurnOffAt((kCANdle.kConfig.LEDInnerRight + kCANdle.kConfig.LEDOutter + offset + 26 - 2) * 2 - i, brightness);
        }
      }

  } else if (currentAnimationSlot == 7) {
    //charged up
    if (animationTime % kCANdle.kColors.kChargeSpeed == 0) {
      LEDTurnOffAt((kCANdle.kConfig.LEDOutter * 2 + kCANdle.kConfig.LEDInnerRight + 9) - currentChargeLocation, 1);
      LEDTurnOffAt(kCANdle.kConfig.LEDOutter * 2 + kCANdle.kConfig.LEDInnerRight + 2 + currentChargeLocation, 1);
      if ((kCANdle.kConfig.LEDOutter + 9) - currentChargeLocation >= 8) {
        LEDTurnOffAt((kCANdle.kConfig.LEDOutter + 6) - currentChargeLocation, 1);
        LEDTurnOffAt((kCANdle.kConfig.LEDOutter * 2 + 10) - currentChargeLocation, 1);
      }
      currentChargeLocation++;
      LEDTurnOnAt((kCANdle.kConfig.LEDOutter * 2 + kCANdle.kConfig.LEDInnerRight + 9) - currentChargeLocation, 1);
      LEDTurnOnAt(kCANdle.kConfig.LEDOutter * 2 + kCANdle.kConfig.LEDInnerRight + 2 + currentChargeLocation, 1);
      if ((kCANdle.kConfig.LEDOutter + 9) - currentChargeLocation >= 8) {
        LEDTurnOnAt((kCANdle.kConfig.LEDOutter + 6) - currentChargeLocation, 1);
        LEDTurnOnAt((kCANdle.kConfig.LEDOutter * 2 + 10) - currentChargeLocation, 1);
      }
    if (currentChargeLocation == kCANdle.kConfig.LEDInnerLeft - maxCharge + 1) {
        currentChargeLocation = 1;
        animationTime = 1;
        maxCharge++;
    } else if (currentChargeLocation > kCANdle.kConfig.LEDInnerLeft) {
        maxCharge = 0;
        currentChargeLocation = 1;
        candle.setLEDs(0, 0, 0, 0, 8, kCANdle.kConfig.LEDCount);
      }
    } else {
      animationTime++;
    }
  }
  SmartDashboard.putNumber("CANdle Temp: ", candle.getTemperature());
  SmartDashboard.putNumber("Candle Brightness", brightness);
}

  public void LEDTurnOnAt(int index, double brightness) {
    candle.setLEDs((int) (kColors.idle[0] * brightness), (int) (kColors.idle[1] * brightness), (int) (kColors.idle[2] * brightness), 0, index, 1);
  }

  public void LEDTurnOffAt(int index, double brightness) {
    candle.setLEDs((int) (LEDOff[0] * brightness), (int) (LEDOff[1] * brightness), (int) (LEDOff[2] * brightness), 0, index, 1);
  }

  public double map(double n, double start1, double stop1, double start2, double stop2, boolean withinBounds) {
    double newval = (n - start1) / (stop1 - start1) * (stop2 - start2) + start2;
    if (!withinBounds) {
      return newval;
    }
    if (start2 < stop2) {
      return constrain(newval, start2, stop2);
    } else {
      return constrain(newval, stop2, start2);
    }
  }

  public double constrain(double n, double low, double high) {
    return Math.max(Math.min(n, high), low);
  }

  public void setBlue() {
    configBrightness(1);
    setAnimation(AnimationTypes.sinFlow, 0, 0, 255);
  }

  public void setRed() {
    configBrightness(1);
    setAnimation(AnimationTypes.sinFlow, 255, 0, 0);
  }

  public void setCharged() {
    configBrightness(1);
    setAnimation(AnimationTypes.chargedUp, 0, 0, 0);
  }

  public void normalAnimation() {
    configBrightness(1);
    // if (DriverStation.getAlliance() == Alliance.Red) {
    //   setAnimation(AnimationTypes.Larson, 200, 0, 0);
    // } else {
    //   setAnimation(AnimationTypes.Larson, 0, 0, 255);
    // }
    candle.setLEDs(0, 0, 0, 0, 8, kCANdle.kConfig.LEDCount);
    setCharged();
  }
}
