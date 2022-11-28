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

    public Limelight(XboxController joystick) {
        m_joystick = joystick;
        NetworkTableInstance.getDefault().startClientTeam(5409);
        turnOffLight();//turns off limelight
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        dpad = m_joystick.getPOV(0);
        if (dpad == 270) {//left
            turningDir = -1;
        } else if (dpad == 90) {//right
            turningDir = 1;
        }
        
        double x = getXOffset();
        double y = getYOffset();
        double a = getTargetArea();
        boolean v = getVisable();

        if (v) {//if its visable

            //from limelight documentation

            double angleGoal = (kLimelight.mountAngle + y) * (3.14159 / 180);//getting the angle to the goal in radians (tan requires radians to work)

            distanceToTarget = (kLimelight.targetHeight - kLimelight.heightOffFloor) / Math.tan(angleGoal);//getting distance to target

            //
        

            int index = closestPoint();
            try {
                //returns a point based on the closest 2 points
                SmartDashboard.putNumber("Shooter speed: ", getInterpolatedSpeed(kLimelight.shooterDataX[index], kLimelight.shooterDataY[index], kLimelight.shooterDataX[index + 1], kLimelight.shooterDataY[index + 1], getTargetDistance()));
            } catch (Exception e) {
                //if its outside the data use the highest point of data
                SmartDashboard.putNumber("Shooter speed: ", kLimelight.shooterDataY[index]);
            }
        }

        SmartDashboard.putNumber("Limelight x offset: ", x);
        SmartDashboard.putNumber("Limelight y offset: ", y);
        SmartDashboard.putNumber("Limelight target area: ", a);
        SmartDashboard.putBoolean("Can see target: ", v);
        SmartDashboard.putBoolean("LedMode: ", isOn);

        SmartDashboard.putNumber("Distance to target", distanceToTarget);
    }

    @Override
    public void simulationPeriodic() {}

    public void turnOnLight() {//turns on the limelight
        isOn = true;
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);//turning on limelight
    }

    public void turnOffLight() {//turns off the limelight
        isOn = false;
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);//turning off limelight
    }

    public void toggle() {//toggles the limelight
        if (isOn) {
            turnOffLight();
        } else {
            turnOnLight();
        }
    }

    public double getXOffset() {//gets the x pos
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(-1);//getting the x offset of the target
    }

    public double getYOffset() {//gets the y pos
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(-1);
    }

    public double getTargetArea() {//gets the target area
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(-1);
    }

    public boolean getVisable() {//returns true or false if its visable or not
        if (NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(-1) == 1) {
            return true;
        } else {
            return false;
        }
    }

    public void setDir(double dir) {//sets the turning direction
        turningDir = dir;
    }

    public double getDir() {//gets the dir
        return turningDir;
    }

    public double getTargetDistance() {//gets the target distance
        return distanceToTarget;
    }

    public int closestPoint() {//finds the closest point at index x
        double closest = 999999;
        int index = -1;
        double dis = getTargetDistance();
        for (int i = 0; i < kLimelight.shooterDataX.length; i++) {
            if (Math.abs(kLimelight.shooterDataX[i] - dis) < closest) {
                closest = Math.abs(kLimelight.shooterDataX[i] - dis);
                index = i;
            } else {// data must be in order for this part to work, if not in order remove this
                break;
            }
        }
        return index;
    }

    public double getInterpolatedSpeed(double x1, double y1, double x2, double y2, double x) {//gets the new interpolated speed
        // Y = ( ( X - X1 )( Y2 - Y1) / ( X2 - X1) ) + Y1
        // Y: finding interpolated Y value
        // X: Target X cordinant
        // X1, Y1: first point
        // X2, Y2: second point

        return ((x - x1) * (y2 - y1) / (x2 - x1)) + y1;
    }
}
