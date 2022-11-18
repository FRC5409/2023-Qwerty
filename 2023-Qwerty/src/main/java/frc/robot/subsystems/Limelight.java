package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight extends SubsystemBase {

    NetworkTable limeTable = NetworkTableInstance.getDefault().getTable("limelight");

    NetworkTableEntry limelightTx = limeTable.getEntry("Tx");
    NetworkTableEntry limelightTa = limeTable.getEntry("Ta");
    NetworkTableEntry limelightTv = limeTable.getEntry("Tv");

    //limelight LED state: 0 LED pipeline, 1: force off, 2: force blink, 3: force on

    //cam mode 0 for image processing

    public Limelight() {
        
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        double limeX = limelightTx.getDouble(0);
        double limeA = limelightTa.getDouble(0);
        double limeV = limelightTv.getDouble(0);

        SmartDashboard.putNumber("Limelight x offset: ", limeX);
        SmartDashboard.putNumber("Limelight target area: ", limeA);
        SmartDashboard.putBoolean("Can see target: ", (limeV == 1) ? true : false);//returning true of false depending on it its 0 or 1
    }

    @Override
    public void simulationPeriodic() {}

    public void turnOnLight() {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);
    }

    public void turnOffLight() {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
    }

    public double getXOffset() {
        return limelightTx.getDouble(0);
    }
}
