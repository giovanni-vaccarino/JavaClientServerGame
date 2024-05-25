package polimi.ingsoft.server.model;

public class CornerPattern implements Pattern {

    @Override
    public int getMatch(Board board,Coordinates coordinates){
        int count=0;
        if(board.getCard(coordinates.sum(new Coordinates(1,1)))!=null)count++;
        if(board.getCard(coordinates.sum(new Coordinates(-1,1)))!=null)count++;
        if(board.getCard(coordinates.sum(new Coordinates(1,-1)))!=null)count++;
        if(board.getCard(coordinates.sum(new Coordinates(-1,-1)))!=null)count++;
        return count;
    }
    public String getCost(){return "Corner";}
}
