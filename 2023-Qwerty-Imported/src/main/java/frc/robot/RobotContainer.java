// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.Drivetrain;
import frc.robot.Constants.kCANdle.AnimationTypes;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.CandleCommand;
import frc.robot.subsystems.CandleSubsystem;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  //Subsystems
  private static Drivetrain sys_drivetrain;
  private final CandleSubsystem sys_candleSubsystem; 

  //commands
  private static ExampleCommand cmd_exampleCommand;
  private static ArcadeDrive cmd_arcadeDrive;

  //controller
  private final CommandXboxController sys_controller;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    //Controller
    sys_controller = new CommandXboxController(Constants.kController.mainControllerPort);
    //subsystems
    sys_candleSubsystem = new CandleSubsystem();
    sys_drivetrain = new Drivetrain();

    //Commands
    cmd_exampleCommand = new ExampleCommand();
    cmd_arcadeDrive = new ArcadeDrive(sys_drivetrain, sys_controller);

    //controller

    sys_drivetrain.setDefaultCommand(cmd_arcadeDrive);
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    sys_controller.povRight()
        .onTrue(new CandleCommand(sys_candleSubsystem, .5, Constants.kCANdle.kColors.yellow[0], Constants.kCANdle.kColors.yellow[1], Constants.kCANdle.kColors.yellow[2], AnimationTypes.ColorFlow));
    sys_controller.povLeft()
        .onTrue(new CandleCommand(sys_candleSubsystem, 0, Constants.kCANdle.kColors.yellow[0], Constants.kCANdle.kColors.yellow[1], Constants.kCANdle.kColors.yellow[2], AnimationTypes.Clear));
    sys_controller.povUp()
        .onTrue(new CandleCommand(sys_candleSubsystem, .5, Constants.kCANdle.kColors.yellow[0], Constants.kCANdle.kColors.yellow[1], Constants.kCANdle.kColors.yellow[2], AnimationTypes.Static));
    sys_controller.povDown()
        .onTrue(new CandleCommand(sys_candleSubsystem, 1, 0, 0, 0, AnimationTypes.sinWave));
    sys_controller.povUpRight()
        .onTrue(new CandleCommand(sys_candleSubsystem, 1, 0, 0, 0, AnimationTypes.sinFlow));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return cmd_exampleCommand;
  }
}
