package polimi.ingsoft.server.model;

import java.util.Objects;

public class Face {
    private CornerSpace upLeft,upRight,bottomLeft,bottomRight;
    private CenterSpace center;
    public CenterSpace getCenter(){
        return center;
    }

    public Face(){
        upLeft=null;
        upRight=null;
        bottomLeft=null;
        bottomRight=null;
        center=null;
    }
    public Face(CornerSpace upLeft,CornerSpace upRight, CornerSpace bottomLeft, CornerSpace bottomRight){
        this.upLeft=upLeft;
        this.upRight=upRight;
        this.bottomRight=bottomRight;
        this.bottomLeft=bottomLeft;
        this.center=null;
    }
    public Face(CornerSpace upLeft,CornerSpace upRight, CornerSpace bottomLeft, CornerSpace bottomRight,CenterSpace center){
        this.upLeft=upLeft;
        this.upRight=upRight;
        this.bottomRight=bottomRight;
        this.bottomLeft=bottomLeft;
        this.center=center;
    }
    public void setCenter(CenterSpace center){
        this.center=center;
    }
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
