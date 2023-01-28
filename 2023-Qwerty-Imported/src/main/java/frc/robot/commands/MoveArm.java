package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;

public class MoveArm extends CommandBase {

    private final Arm m_arm;
    private final double speed;

    public MoveArm(Arm arm, double speed) {
        // Use addRequirements() here to declare subsystem dependencies.
        m_arm = arm;
        this.speed = speed;
        addRequirements(m_arm);
        
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        m_arm.setSpeed(speed);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {}

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_arm.stop();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }

}
