package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.kDrivetrain;
import frc.robot.subsystems.Drivetrain;

public class ArcadeDrive extends CommandBase {

    private final Drivetrain m_drivetrain;
    private final XboxController m_joystick;
    public static double m_forwardSpeed;
    public static double m_turn;

    public ArcadeDrive(Drivetrain drivetrain, XboxController controller) {
        // Use addRequirements() here to declare subsystem dependencies.
        m_drivetrain = drivetrain;
        m_joystick = controller;


        addRequirements(m_drivetrain);
        
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        m_forwardSpeed = m_joystick.getRightTriggerAxis() - m_joystick.getLeftTriggerAxis();

        m_turn = m_joystick.getLeftX();

        m_drivetrain.arcadeDrive(m_forwardSpeed * kDrivetrain.speedOffset, m_turn * kDrivetrain.speedOffset);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }

}