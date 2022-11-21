package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

        //putting motor temp to shuffleboard
        SmartDashboard.putNumber("Left Front Motor temp: ", m_lFrontMot.getTemperature());
        SmartDashboard.putNumber("Right Front Motor temp: ", m_rFrontMot.getTemperature());
        SmartDashboard.putNumber("Left Back Motor temp: ", m_lBackMot.getTemperature());
        SmartDashboard.putNumber("Right Back Motor temp: ", m_rBackMot.getTemperature());
        
    }

    public void arcadeDrive(double forwardSpeed, double rotationalSpeed) {
        setMotRampRate(kDrivetrain.rampRate);//setting ramprate to both motors

        m_diffDrive.arcadeDrive(forwardSpeed, rotationalSpeed);
    }

    public void simulationPeriodic() {}

    public void configMots() {
        //tellimg the back motors to follow the front motors
        m_lBackMot.follow(m_lFrontMot);
        m_rBackMot.follow(m_rFrontMot);

        m_rFrontMot.setInverted(true);//inverting the right side of the drivetrain
        m_rBackMot.setInverted(true);

        m_lFrontMot.configPeakCurrentLimit(kDrivetrain.currentLimit);//setting a current limit to the motors
        m_lBackMot.configPeakCurrentLimit(kDrivetrain.currentLimit);
        m_rFrontMot.configPeakCurrentLimit(kDrivetrain.currentLimit);
        m_rBackMot.configPeakCurrentLimit(kDrivetrain.currentLimit);
    }


    public void setMotRampRate(double rampRate) {
        m_lFrontMot.configClosedloopRamp(rampRate);//setting ramprate
        m_rFrontMot.configClosedloopRamp(rampRate);
    }
}