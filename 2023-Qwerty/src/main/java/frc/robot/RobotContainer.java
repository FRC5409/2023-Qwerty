// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.CandleSubsystem.AnimationTypes;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
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
  private final ExampleSubsystem m_exampleSubsystem;
  private final CandleSubsystem sys_candleSubsystem;

  //commands  
  private final ExampleCommand m_autoCommand;
  //private final CandleCommand sys_candleCommand;

  //controller
  private final XboxController sys_controller;
  //private final JoystickButton button_a, button_b;

  //private final CandleCommand cmd_candleYELLOW;
  //private final CandleCommand cmd_candleRED;
  //private final CandleCommand cmd_candleSTATIC;

  //Suppliers
  private final BooleanSupplier dpadRight;
  private final BooleanSupplier dpadLeft;
  private final BooleanSupplier dpadUp;

  // Triggers
  private final Trigger dpadRightTrigger, dpadLeftTrigger, dpadUpTrigger;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {

    //subsystems
    sys_candleSubsystem = new CandleSubsystem();
    m_exampleSubsystem = new ExampleSubsystem();


    //commands
    m_autoCommand = new ExampleCommand(m_exampleSubsystem);
    //sys_candleCommand = new CandleCommand(sys_candleSubsystem, 1, 252, 144, 3);
  
    //cmd_candleRED = new CandleCommand(sys_candleSubsystem, .5, 255, 0, 0, AnimationTypes.ColorFlow);
    //cmd_candleYELLOW = ;
    //cmd_candleYELLOW = new ;

    //cmd_candleSTATIC = ;

    //controller
    sys_controller = new XboxController(0);
    //button_a = new JoystickButton(sys_controller, XboxController.Button.kA.value);
    //button_b = new JoystickButton(sys_controller, XboxController.Button.kB.value);

    dpadRight = () -> sys_controller.getPOV() == 90;
    dpadLeft = () -> sys_controller.getPOV() == 270;
    dpadUp = () -> sys_controller.getPOV() == 0;

    dpadRightTrigger = new Trigger(dpadRight)
                .whenActive(new CandleCommand(sys_candleSubsystem, .5, 252, 144, 3, AnimationTypes.ColorFlow), false);
    dpadLeftTrigger = new Trigger(dpadLeft)
                .whenActive(new CandleCommand(sys_candleSubsystem, .5, 252, 144, 3, AnimationTypes.Rainbow), false);
    dpadUpTrigger = new Trigger(dpadUp)
                .whenActive(new CandleCommand(sys_candleSubsystem, .5, 255, 255, 255, AnimationTypes.Static), false);
    
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
    //button_a.whenPressed(cmd_candleYELLOW);
    //button_b.whenPressed(cmd_candleRED);
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
