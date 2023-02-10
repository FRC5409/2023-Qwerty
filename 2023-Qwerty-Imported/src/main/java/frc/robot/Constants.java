// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */

public final class Constants {
    public static class kDrivetrain{
        public static class kMotors{
            public final static int frontLeftMotor_CAN = 1;
            public final static int frontRightMotor_CAN = 3;
            public final static int rearLeftMotor_CAN = 2;
            public final static int rearRightMotor_CAN = 4;
            public final static double rampRate = 0.2; 
        }
        public static class kEncoder{
            public final static int countsPerRevolution = 0; //temp
        }
    }
    public static class kController{
        public final static int mainControllerPort = 0; 
        public final static boolean invertTurnOnReverse = false;
    }

    public static class kCANdle {
        public final static int staticTime = 750;
        public static class kConfig {
            public final static int CANdleCAN = 19;
            public final static int LEDCount = 94;

            public final static int LEDInnerRight = 30;
            public final static int LEDInnerLeft = 26;
            public final static int LEDOutter = 15;
        }
        public static class kColors {
            public final static int[] idle = {255, 114 , 0}; //{252, 144 , 3}
            // public final static int[] idleOff = {0, 0, 255}; //alliance station
            public final static int[] cube = {142, 39, 245};
            public final static int[] cone = {237, 120, 0};

            public final static int LEDSinCount = 8;
            public final static int kSpeed = 2;

            public final static double kFrequency = 0.025;
            public final static double kFrequencySpeed = 20;

            public final static int kChargeSpeed = 10;
        }

        public enum AnimationTypes {
            Static,
            Rainbow,
            ColorFlow,
            Larson,
            Twinkle,
            Clear,
            //custom
            sinWave,
            sinFlow,
            chargedUp
        }
    }

    public static class kArm {
        public final static int motor1 = 20;
        public final static int motor2 = 21;

        public final static double kSpeed = 0.8;
    }
}
