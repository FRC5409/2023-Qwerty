package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;

public class ArmMotion extends CommandBase {

    private final Arm sys_arm;
    private double speed;

    public ArmMotion(Arm subsystem, double speed) {
        sys_arm = subsystem;
        this.speed = speed;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(sys_arm);
        
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        sys_arm.armControl(speed);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        sys_arm.armControl(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        
        return false;
    }

}