package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight extends SubsystemBase {
    NetworkTableInstance inst;
    NetworkTable limeTable;

    NetworkTableEntry limelightTx;// = limeTable.getEntry("Tx");;//limelight offset x
    NetworkTableEntry limelightTa;// = limeTable.getEntry("Ta");//Targeted area
    NetworkTableEntry limelightTv;// = limeTable.getEntry("Tv");//can see a target

    double turningDir = 1;
    double dpad;

    XboxController m_joystick;

    //limelight LED state: 0 LED pipeline, 1: force off, 2: force blink, 3: force on

    //cam mode 0 for image processing

    public Limelight() {
        m_joystick = new XboxController(0);
        inst = NetworkTableInstance.getDefault();
        limeTable = inst.getTable("limelight");
        inst.startClientTeam(5409);

        limelightTx = limeTable.getEntry("Tx");;//limelight offset x
        limelightTa = limeTable.getEntry("Ta");//Targeted area
        limelightTv = limeTable.getEntry("Tv");//can see a target
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
        //try .exists in to see in shuffleboard if it can find the value or not
        double x = NetworkTableInstance.getDefault().getTable("limelight").getEntry("Tx").getDouble(-1);//doesn't grab values returns -1
        double a = NetworkTableInstance.getDefault().getTable("limelight").getEntry("Ta").getDouble(-1);//doesn't grab values returns -1
        double v = NetworkTableInstance.getDefault().getTable("limelight").getEntry("Tv").getDouble(-1);//doesn't grab values returns -1

        SmartDashboard.putNumber("Limelight x offset: ", x);
        SmartDashboard.putNumber("Limelight target area: ", a);
        SmartDashboard.putBoolean("Can see target: ", (v == 1) ? true : false);//returning true of false depending on it its 0 or 1

        SmartDashboard.putBoolean("x exists?", NetworkTableInstance.getDefault().getTable("limelight").getEntry("Tx").exists());
        SmartDashboard.putBoolean("a exists?", NetworkTableInstance.getDefault().getTable("limelight").getEntry("Ta").exists());
        SmartDashboard.putBoolean("v exists?", NetworkTableInstance.getDefault().getTable("limelight").getEntry("Tv").exists());

        SmartDashboard.putNumber("LedMode: ", NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").getDouble(-1));
    }

    @Override
    public void simulationPeriodic() {}

    public void turnOnLight() {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);//turning on limelight
    }

    public void turnOffLight() {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);//turning off limelight
    }

    public double getXOffset() {
        return limelightTx.getDouble(-1);//getting the x offset of the target
    }

    public boolean getVisable() {
        if (limelightTv.getDouble(-1) == 1) {
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
