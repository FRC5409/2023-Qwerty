package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;

public class TurretTarget extends CommandBase {

    private final Drivetrain m_drivetrain;
    private final Limelight m_limelight;

    public TurretTarget(Drivetrain drivetrain, Limelight limelight) {
        // Use addRequirements() here to declare subsystem dependencies.
        m_drivetrain = drivetrain;
        m_limelight = limelight;
        
        addRequirements(m_drivetrain, limelight);
        
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        m_limelight.turnOnLight();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double xOff = m_limelight.getXOffset();
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_limelight.turnOffLight();
        m_drivetrain.arcadeDrive(0, 0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        
        return false;
    }

}