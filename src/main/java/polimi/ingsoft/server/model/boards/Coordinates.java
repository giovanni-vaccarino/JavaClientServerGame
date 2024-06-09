package polimi.ingsoft.server.model.boards;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * Object that identifies each and every of a Board's positions, representing a board as a cartesian plane
 */
public class Coordinates implements Serializable, Cloneable {
    private final int x;
    private final int y;

    /**
     * Coordinates creator
     * @param x a card's X position
     * @param y a card's Y position
     */
    public Coordinates(@JsonProperty("x") int x,@JsonProperty("y") int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Returns a card's X position
     * @return a card's X position
     */
    public int getX(){
        return this.x;
    }
    /**
     * Returns a card's Y position
     * @return a card's Y position
     */
    public int getY(){
        return this.y;
    }

    /**
     * Returns a new Coordinates object that represents a card's relative diagonal up-left position
     * @return a new Coordinates object that represents a card's relative diagonal up-left position
     */
    public Coordinates upLeft(){
        return new Coordinates(this.x-1, this.y+1);
    }

    /**
     * Returns a new Coordinates object that represents a card's relative diagonal up-right position
     * @return a new Coordinates object that represents a card's relative diagonal up-right position
     */
    public Coordinates upRight(){
        return new Coordinates(this.x+1, this.y+1);
    }

    /**
     * Returns a new Coordinates object that represents a card's relative diagonal down-left position
     * @return a new Coordinates object that represents a card's relative diagonal down-left position
     */
    public Coordinates downLeft(){
        return new Coordinates(this.x-1, this.y-1);
    }

    /**
     * Returns a new Coordinates object that represents a card's relative diagonal down-right position
     * @return a new Coordinates object that represents a card's relative diagonal down-right position
     */
    public Coordinates downRight(){
        return new Coordinates(this.x+1, this.y-1);
    }

    /**
     * Returns a new Coordinates object that represents a card's relative vertical up position
     * @return a new Coordinates object that represents a card's relative vertical up position
     */
    public Coordinates up(){
        return new Coordinates(this.x, this.y+2);
    }

    /**
     * Returns a new Coordinates object that represents a card's relative vertical down position
     * @return a new Coordinates object that represents a card's relative vertical down position
     */
    public Coordinates down(){
        return new Coordinates(this.x, this.y-2);
    }

    /**
     * Returns a new Coordinates object that represents a card's relative horizontal left position
     * @return a new Coordinates object that represents a card's relative horizontal left position
     */
    public Coordinates left(){
        return new Coordinates(this.x-2, this.y);
    }
    /**
     * Returns a new Coordinates object that represents a card's relative horizontal right position
     * @return a new Coordinates object that represents a card's relative horizontal right position
     */
    public Coordinates right(){
        return new Coordinates(this.x+2, this.y);
    }

    /**
     * Returns a new Coordinates object that represents a card's relative X and Y position
     * incremented of passed Coordinate's X and Y position
     * @param add the coordinates that has to be added to the actual Coordinates object
     * @return new Coordinates object that represents a card's relative X and Y position
     * incremented of passed Coordinate's X and Y position
     */
    public Coordinates sum(Coordinates add){
        return new Coordinates(this.getX()+add.getX(),this.getY()+add.getY());
    }

    /**
     * Returns a new Coordinates object that represents a card's relative X and Y position
     * decremented of passed Coordinate's X and Y position
     * @param add the coordinates that has to be subtracted to the actual Coordinates object
     * @return new Coordinates object that represents a card's relative X and Y position
     * decremented of passed Coordinate's X and Y position
     */
    public Coordinates sub(Coordinates add){
        return new Coordinates(this.getX()-add.getX(),this.getY()-add.getY());
    }


    /**
     * Checks wether two Coordinates' X and Y position are equal
     * @param o the controlled object
     * @return true when the two Coordinates' X and Y position are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return x == that.x && y == that.y;
    }

    /**
     * returns Coordinate's hashcode
     * @return Coordinate's hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public Coordinates clone() {
        try {
            Coordinates clone = (Coordinates) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
