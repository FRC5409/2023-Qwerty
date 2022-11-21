package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight extends SubsystemBase {

    NetworkTable limeTable = NetworkTableInstance.getDefault().getTable("limelight");

    NetworkTableEntry limelightTx = limeTable.getEntry("Tx");//limelight offset x
    NetworkTableEntry limelightTa = limeTable.getEntry("Ta");//Targeted area
    NetworkTableEntry limelightTv = limeTable.getEntry("Tv");//can see a target

    double turningDir = 1;
    double dpad;

    XboxController m_joystick;

    boolean isOn = false;

    //limelight LED state: 0 LED pipeline, 1: force off, 2: force blink, 3: force on

    //cam mode 0 for image processing

    public Limelight() {
        m_joystick = new XboxController(0);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        dpad = m_joystick.getPOV(0);
        if (dpad != -1) {
            if (dpad == 270) {//left
                turningDir = -1;
            } else if (dpad == 90) {//right
                turningDir = 1;
            }
        }

        double limeX = limelightTx.getDouble(0);
        double limeA = limelightTa.getDouble(0);
        double limeV = limelightTv.getDouble(0);

        SmartDashboard.putNumber("Limelight x offset: ", limeX);
        SmartDashboard.putNumber("Limelight target area: ", limeA);
        SmartDashboard.putBoolean("Can see target: ", (limeV == 1) ? true : false);//returning true of false depending on it its 0 or 1

        SmartDashboard.putBoolean("Lime Light LED: ", true);
    }

    @Override
    public void simulationPeriodic() {}

    public void turnOnLight() {
        isOn = true;
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);//turning on limelight
    }

    public void turnOffLight() {
        isOn = false;
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);//turning off limelight
    }

    public void toggle() {
        if (isOn) {
            turnOffLight();
        } else {
            turnOnLight();
        }
    }

    public double getXOffset() {
        return limelightTx.getDouble(0);//getting the x offset of the target
    }

    public boolean getVisable() {
        if (limelightTv.getDouble(0) == 1) {
            return true;
        } else {
            return false;
        }
    }

    public void setDir(double dir) {
        turningDir = dir;
    }

    public double getDir() {
        return turningDir;
    }
}
