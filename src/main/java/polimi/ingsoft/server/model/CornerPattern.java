package polimi.ingsoft.server.model;

import java.io.Serializable;

/**
 * Class that represent's Card's PointPattern that requires Corner(s) coverage
 */
public class CornerPattern implements Pattern, Serializable {

    /**
     * Return the amount of corners covered from a certain card
     * @param board the player's board that has to be checked
     * @param coordinates the played card's coordinates
     * @return the amount of corners covered from a certain card
     */
    @Override
    public int getMatch(Board board,Coordinates coordinates){
        int count=0;
        if(board.getCard(coordinates.sum(new Coordinates(1,1)))!=null)count++;
        if(board.getCard(coordinates.sum(new Coordinates(-1,1)))!=null)count++;
        if(board.getCard(coordinates.sum(new Coordinates(1,-1)))!=null)count++;
        if(board.getCard(coordinates.sum(new Coordinates(-1,-1)))!=null)count++;
        return count;
    }

    /**
     * Function that helps the JSON parser work
     * @param s JSON string
     */
    public void setCost(String s){ //DON'T REMOVE THIS FUNCTION! IT'S WHAT KEEPS THE JSON WORKING !!!
    }
}
