package polimi.ingsoft.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

public class Coordinates implements Serializable {
    private final int x;
    private final int y;

    public Coordinates(@JsonProperty("x") int x,@JsonProperty("y") int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public Coordinates upLeft(){
        return new Coordinates(this.x-1, this.y+1);
    }
    public Coordinates upRight(){
        return new Coordinates(this.x+1, this.y+1);
    }
    public Coordinates downLeft(){
        return new Coordinates(this.x-1, this.y-1);
    }
    public Coordinates downRight(){
        return new Coordinates(this.x+1, this.y-1);
    }
    public Coordinates up(){
        return new Coordinates(this.x, this.y+2);
    }
    public Coordinates down(){
        return new Coordinates(this.x, this.y-2);
    }
    public Coordinates left(){
        return new Coordinates(this.x-2, this.y);
    }
    public Coordinates right(){
        return new Coordinates(this.x+2, this.y);
    }
    public Coordinates sum(Coordinates add){
        return new Coordinates(this.getX()+add.getX(),this.getY()+add.getY());
    }

    public Coordinates sub(Coordinates add){
        return new Coordinates(this.getX()-add.getX(),this.getY()-add.getY());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
