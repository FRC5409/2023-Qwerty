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

public final class Constants
{
    public static class kDrivetrain
    {
        public static class kMotors
        {
            public static int frontLeftMotor_CAN = 1;
            public static int frontRightMotor_CAN = 3;
            public static int rearLeftMotor_CAN = 2;
            public static int rearRightMotor_CAN = 4;
            public static double rampRate = 0.2; 
        }

        public static class kEncoder
        {
            public static int kEncoderChannelId = 1;
            public static int countsPerRevolution = 0; //temp
        }
    }

    public static class kArm
    {
        public static int kMotorId1 = 20;
        public static int kMotorId2 = 21;
    }

    public static class kController
    {
        public static int mainControllerPort = 0; 
        public static boolean invertTurnOnReverse = false;
    }

    public static class kCANdle
    {
        public static class kConfig
        {
            public final static int CANdleCAN = 19;
            public final static int LEDCount = 68;
        }

        public static class kColors
        {
            public final static int[] yellow = {252, 144 , 3};
            public final static int[] red = {255, 0, 0};
            public final static int[] green = {0, 255, 0};
        }

        public enum AnimationTypes
        {
            Static,
            Rainbow,
            ColorFlow,
            Larson,
            Twinkle,
            Clear
        }
    }
}