package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.kLimelight;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight extends SubsystemBase {
    NetworkTableInstance inst;
    NetworkTable limeTable;

    double turningDir = 1;
    double dpad;

    XboxController m_joystick;

    boolean isOn = false;

    double distanceToTarget;

    //limelight LED state: 0 LED pipeline, 1: force off, 2: force blink, 3: force on

    //cam mode 0 for image processing

    public Limelight() {
        m_joystick = new XboxController(0);
        NetworkTableInstance.getDefault().startClientTeam(5409);
        turnOffLight();
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
        
        double x = getXOffset();
        double y = getYOffset();
        double a = getTargetArea();
        boolean v = getVisable();

        //from limelight documentation

        double angleGoal = (kLimelight.mountAngle + y) * (3.14159 / 180);//getting the angle to the goal in radians (tan requires radians to work)

        distanceToTarget = (kLimelight.targetHeight - kLimelight.heightOffFloor) / Math.tan(angleGoal);//getting distance to target

        //

        SmartDashboard.putNumber("Limelight x offset: ", x);
        SmartDashboard.putNumber("Limelight y offset: ", y);
        SmartDashboard.putNumber("Limelight target area: ", a);
        SmartDashboard.putBoolean("Can see target: ", v);
        SmartDashboard.putBoolean("LedMode: ", isOn);

        SmartDashboard.putNumber("Distance to target", distanceToTarget);
        


        int index = closetPoint();
        try {
            //returns a point based on the closest 2 points
            SmartDashboard.putNumber("Shooter speed: ", getInterpolatedSpeed(kLimelight.shooterDataX[index], kLimelight.shooterDataY[index], kLimelight.shooterDataX[index + 1], kLimelight.shooterDataY[index + 1], getTargetDistance()));
        } catch (Exception e) {
            //if its outside the data use the highest point of data
            SmartDashboard.putNumber("Shooter speed: ", kLimelight.shooterDataY[index]);
        }
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
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(-1);//getting the x offset of the target
    }

    public double getYOffset() {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(-1);
    }

    public double getTargetArea() {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(-1);
    }

    public boolean getVisable() {
        if (NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(-1) == 1) {
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

    public double getTargetDistance() {
        return distanceToTarget;
    }

    public int closetPoint() {
        double closet = 999999;
        int index = -1;
        double dis = getTargetDistance();
        for (int i = 0; i < kLimelight.shooterDataX.length; i++) {
            if (Math.abs(kLimelight.shooterDataX[i] - dis) < closet) {
                closet = Math.abs(kLimelight.shooterDataX[i] - dis);
                index = i;
            } else {
                break;
            }
        }
        return index;
    }

    public double getInterpolatedSpeed(double x1, double y1, double x2, double y2, double x) {
        // Y = ( ( X - X1 )( Y2 - Y1) / ( X2 - X1) ) + Y1
        // Y: finding interpolated Y value
        // X: Target X cordinant
        // X1, Y1: first point
        // x2, y2: second point

        return ((x - x1) * (y2 - y1) / (x2 - x1)) + y1;
    }
}
