package polimi.ingsoft.model;
public class Face {
    private CornerSpace upLeft,upRight,bottomLeft,bottomRight;

    public CornerSpace getUpLeft(){
        return upLeft;
    }
    public void setUpLeft(CornerSpace upLeft) {
        this.upLeft = upLeft;
    }
    public CornerSpace getUpRight() {
        return upRight;
    }
    public void setUpRight(CornerSpace upRight) {
        this.upRight = upRight;
    }
    public CornerSpace getBottomLeft() {
        return bottomLeft;
    }
    public void setBottomLeft(CornerSpace bottomLeft) {
        this.bottomLeft = bottomLeft;
    }
    public CornerSpace getBottomRight() {
        return bottomRight;
    }
    public void setBottomRight(CornerSpace bottomRight) {
        this.bottomRight = bottomRight;
    }

}
