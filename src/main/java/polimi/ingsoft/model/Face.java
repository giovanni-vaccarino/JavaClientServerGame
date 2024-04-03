package polimi.ingsoft.model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Face face = (Face) o;
        return Objects.equals(upLeft, face.upLeft) && Objects.equals(upRight, face.upRight) && Objects.equals(bottomLeft, face.bottomLeft) && Objects.equals(bottomRight, face.bottomRight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(upLeft, upRight, bottomLeft, bottomRight);
    }
}
