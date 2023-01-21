// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.commands.ArcadeDrive;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.Drivetrain;
import frc.robot.Constants.kCANdle.AnimationTypes;
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
  private final CandleSubsystem sys_candleSubsystem; 

  //private final CandleCommand sys_candleCommand;

  //controller
  private final XboxController sys_controller;

  //Suppliers
  private final BooleanSupplier dpadRight, dpadLeft, dpadUp;

  // Triggers
  private final Trigger dpadRightTrigger, dpadLeftTrigger, dpadUpTrigger;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {

    //subsystems
    sys_candleSubsystem = new CandleSubsystem();

    //commands

    //controller
    sys_controller = new XboxController(0);
    dpadRight = () -> sys_controller.getPOV() == 90;
    dpadLeft = () -> sys_controller.getPOV() == 270;
    dpadUp = () -> sys_controller.getPOV() == 0;

    dpadRightTrigger = new Trigger(dpadRight)
                .whenActive(new CandleCommand(sys_candleSubsystem, .5, Constants.kCANdle.kColors.yellow[0], Constants.kCANdle.kColors.yellow[1], Constants.kCANdle.kColors.yellow[2], AnimationTypes.ColorFlow), false);
    dpadLeftTrigger = new Trigger(dpadLeft)
                .whenActive(new CandleCommand(sys_candleSubsystem, 0, Constants.kCANdle.kColors.yellow[0], Constants.kCANdle.kColors.yellow[1], Constants.kCANdle.kColors.yellow[2], AnimationTypes.Clear), false);
    dpadUpTrigger = new Trigger(dpadUp)
                .whenActive(new CandleCommand(sys_candleSubsystem, .5, Constants.kCANdle.kColors.yellow[0], Constants.kCANdle.kColors.yellow[1], Constants.kCANdle.kColors.yellow[2], AnimationTypes.Static), false);
    
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
    return cmd_exampleCommand;
  }
}
