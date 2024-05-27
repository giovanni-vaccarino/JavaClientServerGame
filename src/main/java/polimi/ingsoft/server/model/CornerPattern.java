package polimi.ingsoft.server.model;

import java.io.Serializable;

public class CornerPattern implements Pattern, Serializable {

    @Override
    public int getMatch(Board board,Coordinates coordinates){
        int count=0;
        if(board.getCard(coordinates.sum(new Coordinates(1,1)))!=null)count++;
        if(board.getCard(coordinates.sum(new Coordinates(-1,1)))!=null)count++;
        if(board.getCard(coordinates.sum(new Coordinates(1,-1)))!=null)count++;
        if(board.getCard(coordinates.sum(new Coordinates(-1,-1)))!=null)count++;
        return count;
    }
    public void setCost(String s){ //DON'T REMOVE THIS FUNCTION! IT'S WHAT KEEPS THE JSON WORKING !!!
    }
}
