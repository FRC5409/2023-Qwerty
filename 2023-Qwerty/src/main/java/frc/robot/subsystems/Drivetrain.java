package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.kDrivetrain;

public class Drivetrain extends SubsystemBase {
    private WPI_TalonSRX m_lFrontMot;
    private WPI_TalonSRX m_rFrontMot;
    private WPI_TalonSRX m_lBackMot;
    private WPI_TalonSRX m_rBackMot;

    private DifferentialDrive m_diffDrive;


    public Drivetrain() {
        m_lFrontMot = new WPI_TalonSRX(kDrivetrain.leftFrontMotID);
        m_lBackMot = new WPI_TalonSRX(kDrivetrain.leftBackMotID);
        m_rFrontMot = new WPI_TalonSRX(kDrivetrain.rightFrontMotID);
        m_rBackMot = new WPI_TalonSRX(kDrivetrain.rightBackMotID);

        configMots();

        m_diffDrive = new DifferentialDrive(m_lFrontMot, m_rFrontMot);
    }

    public void periodic() {
        
    }

    public void arcadeDrive(double forwardSpeed, double rotationalSpeed) {
        setMotRampRate(kDrivetrain.rampRate);

        m_diffDrive.arcadeDrive(forwardSpeed, rotationalSpeed);
    }

    public void simulationPeriodic() {}

    public void configMots() {
        m_lBackMot.follow(m_lFrontMot);
        m_rBackMot.follow(m_rFrontMot);

        m_rFrontMot.setInverted(true);
        m_rBackMot.setInverted(true);

        //don't belive there is break mode
        //don't belive there is current limiting
    }


    public void setMotRampRate(double rampRate) {
        //no current ramprate I can find
    }
}