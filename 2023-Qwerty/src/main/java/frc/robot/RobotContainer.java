// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.commands.ArcadeDrive;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.Drivetrain;
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
  //Attaching controller on port 0
  private final XboxController m_controller;
  private final JoystickButton button_A, bumper_Left, bumper_Right, trigger_Left, trigger_Right;

  //Subsystems
  private static Drivetrain sys_drivetrain;

  //commands
  private static ExampleCommand cmd_exampleCommand;
  private static ArcadeDrive cmd_arcadeDrive;

  // The robot's subsystems and commands are defined here...
  //subsystems
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final CandleSubsystem sys_candleSubsystem = new CandleSubsystem();

  //commands  
  private final ExampleCommand m_autoCommand;
  private final CandleCommand sys_candleCommand;

  //controller
  private final XboxController sys_controller;
  private final JoystickButton button_a, button_b;

  private final CandleCommand cmd_candle = new CandleCommand(sys_candleSubsystem, 1);
  private final CandleCommand cmd_candleDim = new CandleCommand(sys_candleSubsystem, .5);
  
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    //commands
    m_autoCommand = new ExampleCommand(m_exampleSubsystem);
    sys_candleCommand = new CandleCommand(sys_candleSubsystem, 1);

    //controller
    sys_controller = new XboxController(0);
    button_a = new JoystickButton(sys_controller, XboxController.Button.kA.value);
    button_b = new JoystickButton(sys_controller, XboxController.Button.kB.value);

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
    
    //CANdle
    //button_a.whenPressed(() -> sys_candleSubsystem.configBrightness(1));
    //button_b.whenPressed(() -> sys_candleSubsystem.configBrightness(.5));
    button_a.whenPressed(cmd_candle);
    button_b.whenPressed(cmd_candleDim);
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
