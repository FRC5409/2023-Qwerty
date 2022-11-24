// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.sql.Driver;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.LimeLED;
import frc.robot.commands.TurretTarget;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  // private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  // private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */

  private final XboxController m_joystick;

  private final Drivetrain m_drivetrain;
  private final ArcadeDrive m_defaultDrive;

  private final Limelight m_limelight;

  private final JoystickButton bumper_left, yButton;

  public RobotContainer() {
    // Configure the button bindings
    m_joystick = new XboxController(0);

    m_drivetrain = new Drivetrain();
    m_defaultDrive = new ArcadeDrive(m_drivetrain, m_joystick);

    m_limelight = new Limelight();

    bumper_left = new JoystickButton(m_joystick, XboxController.Button.kLeftBumper.value);
    yButton = new JoystickButton(m_joystick, XboxController.Button.kY.value);

    


    m_drivetrain.setDefaultCommand(m_defaultDrive);

    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    bumper_left.whenPressed(new TurretTarget(m_drivetrain, m_limelight));//when held target
    bumper_left.whenReleased(new LimeLED(m_limelight, 0));//when released turn off limelight

    yButton.whenPressed(new LimeLED(m_limelight, -1));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  // public Command getAutonomousCommand() {
  //   // An ExampleCommand will run in autonomous
  //   return m_autoCommand;
  // }
}
