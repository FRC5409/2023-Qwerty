// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.commands.ArmRotation;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.newArmRotation;
import frc.robot.subsystems.ArmPIDSubsystem;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.Constants.kCANdle.AnimationTypes;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

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
  private final ArmPIDSubsystem m_ArmPIDSubsystem;

  //commands  
  private final ExampleCommand m_autoCommand;
  //private final CandleCommand sys_candleCommand;

  //controller
  private final CommandXboxController sys_controller;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {

    //subsystems

    m_exampleSubsystem = new ExampleSubsystem();
    m_ArmPIDSubsystem = new ArmPIDSubsystem();


    //commands
    m_autoCommand = new ExampleCommand(m_exampleSubsystem);


    //controller
    sys_controller = new CommandXboxController(0);
    configureButtonBindings();

  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings(){
    sys_controller.y().onTrue(new newArmRotation(m_ArmPIDSubsystem, 0.4, 0.75));
    sys_controller.a().onTrue(new newArmRotation(m_ArmPIDSubsystem, -0.4, 0.5)); 
  
   // sys_controller.y().onTrue(new ArmRotation(0.65, m_ArmSubsystem));
   // sys_controller.a().onTrue(new ArmRotation(0.75, m_ArmSubsystem));


  
  }
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }}

