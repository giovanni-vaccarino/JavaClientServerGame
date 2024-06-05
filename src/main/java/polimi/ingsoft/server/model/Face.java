package polimi.ingsoft.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a card's face-Each card has two
 */
public class Face implements Serializable {
    private CornerSpace upLeft,upRight,bottomLeft,bottomRight;
    private CenterSpace center;

    /**
     * Returns face's centerspace, if the centerspace is null, the calling face is the BACK face
     * @return face's centerspace, if the centerspace is null, the calling face is the BACK face
     */
    public CenterSpace getCenter(){
        return center;
    }

    /**
     * Creates a face item initializing all attributes with null value
     */
    public Face(){
        upLeft=null;
        upRight=null;
        bottomLeft=null;
        bottomRight=null;
        center=null;
    }

    /**
     * Creates a face item with specified attributes values, creating a CenterSpace with null value
     * @param upLeft the face's up-left CornerSpace
     * @param upRight the face's up-right CornerSpace
     * @param bottomLeft the face's bottom-left CornerSpace
     * @param bottomRight the face's bottom-right CornerSpace
     */
    public Face(CornerSpace upLeft,CornerSpace upRight,CornerSpace bottomLeft,CornerSpace bottomRight){
        this.upLeft=upLeft;
        this.upRight=upRight;
        this.bottomRight=bottomRight;
        this.bottomLeft=bottomLeft;
        this.center=null;
    }

    /**
     * Creates a face item with specified attributes values
     * @param upLeft the face's up-left CornerSpace
     * @param upRight the face's up-right CornerSpace
     * @param bottomLeft the face's bottom-left CornerSpace
     * @param bottomRight the face's bottom-right CornerSpace
     * @param center the face's CenterSpace
     */
    public Face(@JsonProperty("upLeft") CornerSpace upLeft,@JsonProperty("upRight") CornerSpace upRight,@JsonProperty("bottomLeft") CornerSpace bottomLeft, @JsonProperty("bottomRight")CornerSpace bottomRight,@JsonProperty("center")CenterSpace center){
        this.upLeft=upLeft;
        this.upRight=upRight;
        this.bottomRight=bottomRight;
        this.bottomLeft=bottomLeft;
        this.center=center;
    }

    /**
     * Changes the face's actual CenterSpace
     * @param center the new face's CenterSpace
     */
    public void setCenter(CenterSpace center){
        this.center=center;
    }

    /**
     * Return face's up-left CornerSpace
     * @return face's up-left CornerSpace
     */
    public CornerSpace getUpLeft(){
        return upLeft;
    }

    /**
     * Changes the face's actual up-left CornerSpace
     * @param upLeft the new face's up-left CornerSpace
     */
    public void setUpLeft(CornerSpace upLeft) {
        this.upLeft = upLeft;
    }
    /**
     * Return face's up-right CornerSpace
     * @return face's up-right CornerSpace
     */
    public CornerSpace getUpRight() {
        return upRight;
    }

    /**
     * Changes the face's actual up-right CornerSpace
     * @param upRight the new face's up-right CornerSpace
     */
    public void setUpRight(CornerSpace upRight) {
        this.upRight = upRight;
    }

    /**
     * Return face's bottom-left CornerSpace
     * @return face's bottom-left CornerSpace
     */
    public CornerSpace getBottomLeft() {
        return bottomLeft;
    }

    /**
     * Changes the face's actual bottom-left CornerSpace
     * @param bottomLeft the new face's bottom-left CornerSpace
     */
    public void setBottomLeft(CornerSpace bottomLeft) {
        this.bottomLeft = bottomLeft;
    }
    /**
     * Return face's bottom-right CornerSpace
     * @return face's bottom-right CornerSpace
     */
    public CornerSpace getBottomRight() {
        return bottomRight;
    }

    /**
     * Changes the face's actual bottom-right CornerSpace
     * @param bottomRight the new face's bottom-right CornerSpace
     */
    public void setBottomRight(CornerSpace bottomRight) {
        this.bottomRight = bottomRight;
    }

    /**
     * Returns true if the actual face and a certain object are equal
     * @param o the controlled object
     * @return true if the actual face and a certain object are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Face face = (Face) o;
        return Objects.equals(upLeft, face.upLeft) && Objects.equals(upRight, face.upRight) && Objects.equals(bottomLeft, face.bottomLeft) && Objects.equals(bottomRight, face.bottomRight);
    }

    /**
     * Returns face's hashCode
     * @return face's hashCode
     */
    @Override
    public int hashCode() {
        return Objects.hash(upLeft, upRight, bottomLeft, bottomRight);
    }
}
