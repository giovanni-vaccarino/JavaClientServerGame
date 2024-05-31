package polimi.ingsoft.client.ui.gui;

public class CoordinatesGUI {
    private int x;
    private int y;

    public CoordinatesGUI(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void addX(int increment) {
        this.x += increment;
    }

    public void addY(int increment) {
        this.y += increment;
    }
}
