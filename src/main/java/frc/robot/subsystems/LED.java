package frc.robot.subsystems;

import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Utils.Color;
import frc.robot.Utils.Convert;
import frc.robot.Utils.NT4;

public class LED extends SubsystemBase {

    public enum kStates {
        kSolid(0), kBlink(1), kSinWave(2), kSinFlow(3), kRainbowCycle(4), kRainbowBlink(5);

        kStates(int value) {
            state = value;
        }

        public final int state;
    }

    private static LED instance = null;

    private final ShuffleboardTab sb_tab;

    private final Color primaryColor;
    private final Color secondaryColor;
    private int state;
    private double multivariate;

    private LED() {
        state = kStates.kSinWave.state;
        multivariate = -1;
        primaryColor = Color.kGold;
        secondaryColor = Color.kBlack;

        sb_tab = NT4.getInstance("LED_COMMUNICATION_TAB");
        sb_tab.addInteger("STATE", () -> getState());
        sb_tab.addIntegerArray("PRIMARY_COLOR", () -> Convert.intToLong(getPrimaryColor()));
        sb_tab.addIntegerArray("SECONDARY_COLOR", () -> Convert.intToLong(getSecondaryColor()));
        sb_tab.addDouble("MULTIVARIATE", () -> getMultivariate());
    }

    // Get subsystem
    public static LED getInstance() {
        if (instance == null) instance = new LED();

        return instance;
    }

    /**
     * Sets the current LED State
     */
    public void setState(int state) {
        this.state = state;
    }

    /**
     * Sets the current LED State
     */
    public void setState(kStates state) {
        this.state = state.state;
    }

    /**
     * Gets the current LED State
     */
    public int getState() {
        return state;
    }

    /**
     * Sets the primary color to a three long int array
     */
    public void setPrimaryColor(int[] color) {
        primaryColor.setColor(color[0], color[1], color[2]);
    }

    /**
     * Sets the primary color to a color object
     */
    public void setPrimaryColor(Color color) {
        primaryColor.setColor(color);
    }

    /**
     * Sets the secondary color to a three long int array
     */
    public void setSecondaryColor(int[] color) {
        secondaryColor.setColor(color[0], color[1], color[2]);
    }

    /**
     * Sets the secondary color to a color object
     */
    public void setSecondaryColor(Color color) {
        secondaryColor.setColor(color);
    }

    /**
     * Returns the primary color as an int array
     */
    public int[] getPrimaryColor() {
        return primaryColor.getColor();
    }

    /**
     * Returns the secondary color as an int array
     */
    public int[] getSecondaryColor() {
        return secondaryColor.getColor();
    }

    /**
     * Sets the multivariate var to parameter
     * This for example changes the blinking speed on LEDs when the LEDs state is set to kBlink
     */
    public void setMultivariate(double val) {
        multivariate = val;
    }

    /**
     * Get the multivariate var
     */
    public double getMultivariate() {
        return multivariate;
    }

    public void changeLED(kStates state, Color primary, Color secondary) {
        setState(state);
        setPrimaryColor(primary);
        setSecondaryColor(secondary);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
        
    }

}