// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  //Attaching controller on port 0
  private final XboxController m_controller;
  private final JoystickButton button_A, bumper_Left, bumper_Right, trigger_Left, trigger_Right;

  //Subsystems
  private static Drivetrain sys_drivetrain;

  //commands
  private static ExampleCommand cmd_exampleCommand;
  private static ArcadeDrive cmd_arcadeDrive;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    m_controller = new XboxController(Constants.ControlConfig.mainControllerPort);
      //Digital
    button_A = new JoystickButton(m_controller, XboxController.Button.kA.value);
    bumper_Left = new JoystickButton(m_controller, XboxController.Button.kLeftBumper.value);
    bumper_Right = new JoystickButton(m_controller, XboxController.Button.kRightBumper.value);
      //Analog
    trigger_Left = new JoystickButton(m_controller, XboxController.Button.kRightBumper.value);
    trigger_Right = new JoystickButton(m_controller, XboxController.Button.kRightBumper.value);

    //subsystems
    sys_drivetrain = new Drivetrain();

    //Commands
    cmd_exampleCommand = new ExampleCommand();
    cmd_arcadeDrive = new ArcadeDrive(sys_drivetrain, m_controller);

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
    //Binding controller inputs
    //trigger_Left.whenPressed(ArcadeDrive());
    //trigger_Right.whenPressed(ArcadeDrive());
    
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
