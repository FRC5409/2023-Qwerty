package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.kArm;

public class Arm extends SubsystemBase {

    private final CANSparkMax motor1;
    private final CANSparkMax motor2;

    public Arm() {
        motor1 = new CANSparkMax(kArm.motor1, MotorType.kBrushless);
        motor2 = new CANSparkMax(kArm.motor2, MotorType.kBrushless);

        motor1.restoreFactoryDefaults();
        motor2.restoreFactoryDefaults();

        motor1.setIdleMode(IdleMode.kBrake);
        motor2.setIdleMode(IdleMode.kBrake);

        motor1.setSmartCurrentLimit(40);
        motor2.setSmartCurrentLimit(40);

        motor2.follow(motor1);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        
    }

    @Override
    public void simulationPeriodic() {}

    public void setSpeed(double speed) {
        motor1.set(speed);
    }

    public void stop() {
        motor1.set(0);
    }

}
