// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.CandleCommand;
import frc.robot.subsystems.CandleSubsystem;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  //subsystems
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final CandleSubsystem sys_candleSubsystem = new CandleSubsystem();

  //commands  
  private final ExampleCommand m_autoCommand;
  //private final CandleCommand sys_candleCommand;

  //controller
  private final XboxController sys_controller;
  private final JoystickButton button_a, button_b;

  private final CandleCommand cmd_candleYELLOW;
  private final CandleCommand cmd_candleRED;
  
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    //commands
    m_autoCommand = new ExampleCommand(m_exampleSubsystem);
    //sys_candleCommand = new CandleCommand(sys_candleSubsystem, 1, 252, 144, 3);
    cmd_candleRED = new CandleCommand(sys_candleSubsystem, 1, 255, 0, 0);
    cmd_candleYELLOW = new CandleCommand(sys_candleSubsystem, 1, 252, 144, 3);

    //controller
    sys_controller = new XboxController(0);
    button_a = new JoystickButton(sys_controller, XboxController.Button.kA.value);
    button_b = new JoystickButton(sys_controller, XboxController.Button.kB.value);

    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    //CANdle
    //button_a.whenPressed(() -> sys_candleSubsystem.configBrightness(1));
    //button_b.whenPressed(() -> sys_candleSubsystem.configBrightness(.5));
    button_a.whenPressed(cmd_candleYELLOW);
    button_b.whenPressed(cmd_candleRED);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
}
