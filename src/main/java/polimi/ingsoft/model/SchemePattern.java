package polimi.ingsoft.model;

import java.util.ArrayList;

public class SchemePattern implements Pattern{
    ArrayList<Link> order;
    private final int maxSize=3;
    public SchemePattern(ArrayList<Link> order){
        this.order=order;
    }
    private int getMatch(Board board,int count,int actualLink,Coordinates actualCoordinates) {
        if (board.getCard(actualCoordinates.sum(order.get(actualLink).getPosFromBegin())) == null) return 0;
        if (actualCoordinates.equals(new Coordinates(0, 0))||board.getCard(actualCoordinates).getColor().getFirst()!=order.get(actualLink).getColor()) {
            if (board.getCard(actualCoordinates).getUpLeftCorner() != null)
                count = count + (getMatch(board, count, actualLink, actualCoordinates.sum(new Coordinates(-1, 1))));
            if (board.getCard(actualCoordinates).getUpRightCorner() != null)
                count = count + (getMatch(board, count, actualLink, actualCoordinates.sum(new Coordinates(1, 1))));
            if (board.getCard(actualCoordinates).getBottomLeftCorner() != null)
                count = count + (getMatch(board, count, actualLink, actualCoordinates.sum(new Coordinates(-1, -1))));
            if (board.getCard(actualCoordinates).getBottomRightCorner() != null)
                count = count + (getMatch(board, count, actualLink, actualCoordinates.sum(new Coordinates(1, -1))));
            }
        else if(board.getCard(actualCoordinates).getColor().getFirst()==order.get(actualLink).getColor()){
            if(actualLink==0&&order.get(1).getPosFromBegin().equals(new Coordinates(1,1))&&board.getCard(actualCoordinates.sum(new Coordinates(1,1))).getColor().getFirst()==order.get(actualLink).getColor())getMatch(board,count,actualLink,actualCoordinates);
            if(actualLink==0&&order.get(1).getPosFromBegin().equals(new Coordinates(-1,-1))&&board.getCard(actualCoordinates.sum(new Coordinates(-1,-1))).getColor().getFirst()==order.get(actualLink).getColor())getMatch(board,count,actualLink,actualCoordinates);
            if(actualLink==0&&order.get(1).getPosFromBegin().equals(new Coordinates(-1,1))&&board.getCard(actualCoordinates.sum(new Coordinates(-1,1))).getColor().getFirst()==order.get(actualLink).getColor())getMatch(board,count,actualLink,actualCoordinates);
            if(actualLink==0&&order.get(1).getPosFromBegin().equals(new Coordinates(1,1))&&board.getCard(actualCoordinates.sum(new Coordinates(1,-1))).getColor().getFirst()==order.get(actualLink).getColor())getMatch(board,count,actualLink,actualCoordinates);
            else if(actualLink<maxSize)count=count+getMatch(board,count,actualLink+1,actualCoordinates.sum(order.get(actualLink).getPosFromBegin()));
            else if(actualLink==maxSize)return 1;
            }
        return count;
        }

    public int getMatch(Board board){
        return this.getMatch(board,0,0,new Coordinates(0,0));
    }

}
