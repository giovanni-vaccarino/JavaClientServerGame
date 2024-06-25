package polimi.ingsoft.client.ui.gui;

/**
 * This class represents a pair of coordinates (x, y) for the GUI.
 */
public class CoordinatesGUI {
    private int x;
    private int y;

    /**
     * Constructs a CoordinatesGUI object with the specified x and y values.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public CoordinatesGUI(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the x coordinate.
     *
     * @return the x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the x coordinate.
     *
     * @param x the x coordinate to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets the y coordinate.
     *
     * @return the y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the y coordinate.
     *
     * @param y the y coordinate to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Increases the x coordinate by the specified increment.
     *
     * @param increment the amount to add to the x coordinate
     */
    public void addX(int increment) {
        this.x += increment;
    }

    /**
     * Increases the y coordinate by the specified increment.
     *
     * @param increment the amount to add to the y coordinate
     */
    public void addY(int increment) {
        this.y += increment;
    }
}
