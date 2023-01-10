package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.kDrivetrain;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;

public class TurretTarget extends CommandBase {

    private final Drivetrain m_drivetrain;
    private final Limelight m_limelight;
    private final XboxController m_joystick;

    public TurretTarget(Drivetrain drivetrain, Limelight limelight, XboxController joystick) {
        // Use addRequirements() here to declare subsystem dependencies.
        m_drivetrain = drivetrain;
        m_limelight = limelight;
        m_joystick = joystick;

        
        addRequirements(m_drivetrain, limelight);
        
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        m_limelight.turnOnLight();//turning on limelight
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double xOff = m_limelight.getXOffset();//grabbing the offset of the target
        double forwardSpeed = m_joystick.getRightTriggerAxis() - m_joystick.getLeftTriggerAxis();
        if (m_limelight.getVisable()) {
            if (Math.abs(xOff) >= kDrivetrain.targetPlay) {
                m_drivetrain.arcadeDrive(forwardSpeed, (xOff / Math.abs(xOff)) * kDrivetrain.targetSpeed);//turning based on if its right or left
            }
        } else {
            m_drivetrain.arcadeDrive(forwardSpeed, m_limelight.getDir() * kDrivetrain.scanningSpeed);
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        // m_limelight.turnOffLight();//turns off limelight
        m_drivetrain.arcadeDrive(0, 0);
        m_limelight.turnOffLight();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
        // return Math.abs(m_limelight.getXOffset()) < kDrivetrain.targetPlay && m_limelight.getVisable();//returning false because the command is ran when button is held
    }

}