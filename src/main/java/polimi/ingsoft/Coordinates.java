package polimi.ingsoft;

public class Coordinates {
    private final int x;
    private final int y;

    public Coordinates(int x, int y){
        this.x = x;
        this.y = y;
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
}
