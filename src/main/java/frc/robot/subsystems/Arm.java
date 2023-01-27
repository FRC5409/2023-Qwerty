package frc.robot.subsystems;

import java.util.HashMap;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Arm extends SubsystemBase {

    private final DutyCycleEncoder s_encoder;
    
    private final CANSparkMax m_leader;
    private final CANSparkMax m_follower;

    private final HashMap<String, GenericEntry> shuffleboardFields;


    public Arm() {
        s_encoder = new DutyCycleEncoder(Constants.kArm.kEncoderChannel);
        // s_encoder.setDutyCycleRange(1/1025, 1024/1025);

        m_leader = new CANSparkMax(Constants.kArm.LEAD_CAN_ID, MotorType.kBrushless);
            m_leader.restoreFactoryDefaults();
            m_leader.setSmartCurrentLimit(30);

        m_follower = new CANSparkMax(Constants.kArm.FOLLOWER_CAN_ID, MotorType.kBrushless);
            m_follower.restoreFactoryDefaults();
            m_follower.follow(m_leader);
            m_follower.setSmartCurrentLimit(30);

        shuffleboardFields = new HashMap<String, GenericEntry>();

        ShuffleboardLayout encoder = Shuffleboard.getTab("Arm").getLayout("Arm Encoder", BuiltInLayouts.kList);

        shuffleboardFields.put("AbsEncoder", encoder.add("Absolute Encoder", getAbsolutePos()).withWidget(BuiltInWidgets.kTextView).getEntry());
    }

    public double getAbsolutePos() {
        return s_encoder.get();
    }

    public void armControl(double speed) {
        m_leader.set(speed);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        
        shuffleboardFields.get("AbsEncoder").setDouble(getAbsolutePos());
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
        
    }

}