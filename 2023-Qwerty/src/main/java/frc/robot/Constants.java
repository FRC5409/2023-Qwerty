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
    public static final class kDrivetrain {
        public static int leftFrontMotID = 1;//CAN IDs
        public static int leftBackMotID = 2;//CAN IDs
        public static int rightFrontMotID = 3;//CAN IDs
        public static int rightBackMotID = 4;//CAN IDs

        public static double rampRate = 0.5;//seconds it take from 0 to full throtle
        
        public static int currentLimit = 60;//current limit in amps

        public static double speedOffset = 0.5;//limiting forward speed
        public static double speedTurnOffset = 0.7;//limiting rotational speed

        public static double targetSpeed = 0.5;//the speed the robot turns at when targetting
        public static double targetPlay = 7;//the amount the robot can differ from the target

        public static double scanningSpeed = 0.6;
    }

    public static final class kLimelight {
        public static double mountAngle = 45;
        public static double targetHeight = 40;//inches
        public static double heightOffFloor = 9;//inches

        public static double[] shooterDataX = {5, 10, 15, 20, 25, 30, 35, 40, 45, 50};//distance to target
        public static double[] shooterDataY = {10, 16, 24, 36, 48, 58, 70, 96, 104, 120};//speed
    }
}
