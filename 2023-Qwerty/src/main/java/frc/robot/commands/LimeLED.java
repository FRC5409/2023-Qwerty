package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Limelight;

public class LimeLED extends CommandBase {

    private final Limelight m_limelight;
    private boolean LEDon;

    public LimeLED(Limelight limelight, boolean on) {
        // Use addRequirements() here to declare subsystem dependencies.
        m_limelight = limelight;
        LEDon = on; 

        addRequirements(m_limelight);
        
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        if (LEDon) {
            m_limelight.turnOnLight();
        } else {
            m_limelight.turnOffLight();
        }
    }


    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return true;
    }

}