package polimi.ingsoft.server.model;
import java.util.ArrayList;
import java.util.HashMap;

public class SchemePattern implements Pattern{
    private final ArrayList<Link> order;
    private final int maxSize=2;
    public SchemePattern(ArrayList<Link> order){
        this.order=order;
    }
    private int getMatch(HashMap<Coordinates,Boolean> used,boolean setvisited,HashMap<Coordinates,Boolean> visited, Board board, int count, int actualLink, Coordinates actualCoordinates) {
        if (board.getCard(actualCoordinates/*.sum(order.get(actualLink).getPosFromBegin())*/) == null) return count;//0;
        if(setvisited)visited.put(actualCoordinates,true);
        if (setvisited) {
            if (board.getCard(actualCoordinates).getUpLeftCorner()!=null
                    &&!visited.containsKey(actualCoordinates.sum(new Coordinates(-1, 1))))
                count = (getMatch(used,true,visited,board, count, actualLink, actualCoordinates.sum(new Coordinates(-1, 1))));
            if (board.getCard(actualCoordinates).getUpRightCorner() != null
                    &&!visited.containsKey(actualCoordinates.sum(new Coordinates(1, 1))))
                count = (getMatch(used,true,visited,board, count, actualLink, actualCoordinates.sum(new Coordinates(1, 1))));
            if (board.getCard(actualCoordinates).getBottomLeftCorner() != null
                    &&!visited.containsKey(actualCoordinates.sum(new Coordinates(-1, -1))))
                count = (getMatch(used,true,visited,board, count, actualLink, actualCoordinates.sum(new Coordinates(-1, -1))));
            if (board.getCard(actualCoordinates).getBottomRightCorner() != null
                    &&!visited.containsKey(actualCoordinates.sum(new Coordinates(1, -1))))
                count =(getMatch(used,true,visited,board, count, actualLink, actualCoordinates.sum(new Coordinates(1, -1))));
            }
        if(!actualCoordinates.equals(new Coordinates(0,0))
                &&board.getCard(actualCoordinates).getColor().equals(order.get(actualLink).getColor())
                    &&!used.containsKey(actualCoordinates)){
            if(actualLink<maxSize) {
                return getMatch(used,false, visited, board, count, actualLink + 1, actualCoordinates.sub(order.get(actualLink).getPosFromBegin()).sum(order.get(actualLink + 1).getPosFromBegin()));
            } else if(board.getCard(actualCoordinates).getColor().equals(order.get(actualLink).getColor()) && !used.containsKey(board.getCard(actualCoordinates))){
                    for(int i=0;i<maxSize+1;i++)used.put(actualCoordinates.sub(order.get(actualLink).getPosFromBegin()).sum(order.get(i).getPosFromBegin()),true);
                    return count+1;
                }
            }
        return count;
        }
    @Override
    public int getMatch(Board board){
        HashMap<Coordinates, Boolean> visited=new HashMap<Coordinates,Boolean>(),used=new HashMap<Coordinates,Boolean>();
        return this.getMatch(used,true,visited,board,0,0,new Coordinates(0,0));
    }

}