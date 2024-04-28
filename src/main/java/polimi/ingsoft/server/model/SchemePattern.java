package polimi.ingsoft.server.model;
import java.util.ArrayList;
import java.util.HashMap;

public class SchemePattern implements Pattern<ArrayList<Link>> {
    //ArrayList<Link> order;
    private final int maxSize=2;
//    public SchemePattern(ArrayList<Link> order){
//        this.order=order;
//    }
    private int getMatch(ArrayList<Link> order,HashMap<Coordinates,Boolean> used,boolean setvisited,HashMap<Coordinates,Boolean> visited, Board board, int count, int actualLink, Coordinates actualCoordinates) {
        if (board.getCard(actualCoordinates/*.sum(order.get(actualLink).getPosFromBegin())*/) == null) return count;//0;
        if(setvisited)visited.put(actualCoordinates,true);
        if (setvisited) {
            if (board.getCard(actualCoordinates).getUpLeftCorner()!=null
                    &&!visited.containsKey(actualCoordinates.sum(new Coordinates(-1, 1))))
                count = /*count +*/ (getMatch(order,used,true,visited,board, count, actualLink, actualCoordinates.sum(new Coordinates(-1, 1))));
            if (board.getCard(actualCoordinates).getUpRightCorner() != null
                    &&!visited.containsKey(actualCoordinates.sum(new Coordinates(1, 1))))
                count = /*count +*/ (getMatch(order,used,true,visited,board, count, actualLink, actualCoordinates.sum(new Coordinates(1, 1))));
            if (board.getCard(actualCoordinates).getBottomLeftCorner() != null
                    &&!visited.containsKey(actualCoordinates.sum(new Coordinates(-1, -1))))
                count = /*count +*/ (getMatch(order,used,true,visited,board, count, actualLink, actualCoordinates.sum(new Coordinates(-1, -1))));
            if (board.getCard(actualCoordinates).getBottomRightCorner() != null
                    &&!visited.containsKey(actualCoordinates.sum(new Coordinates(1, -1))))
                count = /*count +*/ (getMatch(order,used,true,visited,board, count, actualLink, actualCoordinates.sum(new Coordinates(1, -1))));
            }
        if(!actualCoordinates.equals(new Coordinates(0,0))
                &&board.getCard(actualCoordinates).getColor().equals(order.get(actualLink).getColor())
                    &&!used.containsKey(actualCoordinates)){
            if(actualLink<maxSize) { //rivedere riguardo le diagonali
//                if (order.get(1).getPosFromBegin().equals(new Coordinates(1, 1))) {
//                    if (board.getCard(actualCoordinates.sum(new Coordinates(1, 1)))!=null&&board.getCard(actualCoordinates.sum(new Coordinates(1, 1))).getColor() == order.get(actualLink).getColor()
//                            &&visited.get(actualCoordinates.sub(new Coordinates(1,1)))==null&&used.get(actualCoordinates.sub(new Coordinates(1,1)))==null)
//                        count=count+getMatch(used,false,visited,board, count, actualLink, actualCoordinates.sum(new Coordinates(1, 1)));
//                    else if(visited.get(actualCoordinates.sub(new Coordinates(1,1))))count = count + getMatch(used,false,visited,board, count, actualLink + 1, actualCoordinates.sub(new Coordinates(1, 1)));
//                }
//                else if (order.get(1).getPosFromBegin().equals(new Coordinates(-1, -1))) {
//                    if (board.getCard(actualCoordinates.sum(new Coordinates(-1, -1)))!=null&&board.getCard(actualCoordinates.sum(new Coordinates(-1, -1))).getColor() == order.get(actualLink).getColor()
//                            &&visited.get(actualCoordinates.sub(new Coordinates(-1,-1)))==null&&used.get(actualCoordinates.sub(new Coordinates(-1,-1)))==null)
//                        count=count+getMatch(used,false,visited,board, count, actualLink, actualCoordinates.sum(new Coordinates(-1, -1)));
//                    else if(visited.get(actualCoordinates.sub(new Coordinates(-1,-1)))) count = count + getMatch(used,false,visited,board, count, actualLink + 1, actualCoordinates.sub(new Coordinates(-1, -1)));
//                }
//                else if (order.get(1).getPosFromBegin().equals(new Coordinates(-1, 1))) {
//                    if (board.getCard(actualCoordinates.sum(new Coordinates(-1, 1)))!=null&&board.getCard(actualCoordinates.sum(new Coordinates(-1, 1))).getColor() == order.get(actualLink).getColor()
//                            &&visited.get(actualCoordinates.sub(new Coordinates(-1,1)))==null&&used.get(actualCoordinates.sub(new Coordinates(-1,1)))==null)
//                        count=count+getMatch(used,false,visited,board, count, actualLink, actualCoordinates.sum(new Coordinates(-1, 1)));
//                    else if(visited.get(actualCoordinates.sub(new Coordinates(-1,1))))count = count + getMatch(used,false,visited,board, count, actualLink + 1, actualCoordinates.sub(new Coordinates(-1, 1)));
//                }
//                else if (order.get(1).getPosFromBegin().equals(new Coordinates(1, -1))) {
//                    if (board.getCard(actualCoordinates.sum(new Coordinates(1, -1)))!=null&&board.getCard(actualCoordinates.sum(new Coordinates(1, -1))).getColor()== order.get(actualLink).getColor()
//                            &&visited.get(actualCoordinates.sub(new Coordinates(1,-1)))==null&&used.get(actualCoordinates.sub(new Coordinates(1,-1)))==null)
//                        count=count+getMatch(used,false,visited,board, count, actualLink, actualCoordinates.sum(new Coordinates(1, -1)));
//                    else if(visited.get(actualCoordinates.sub(new Coordinates(1,-1))))count = count + getMatch(used,false,visited,board, count, actualLink + 1, actualCoordinates.sub(new Coordinates(1, -1)));
//                }
              /*else*//* count = count +*/ return /*count+*/getMatch(order,used,false, visited, board, count, actualLink + 1, actualCoordinates.sub(order.get(actualLink).getPosFromBegin()).sum(order.get(actualLink + 1).getPosFromBegin()));
            } else if(board.getCard(actualCoordinates).getColor().equals(order.get(actualLink).getColor())&&!used.containsKey(board.getCard(actualCoordinates))){
                    //if(order.get(1).getPosFromBegin().equals(new Coordinates(1, -1))||order.get(1).getPosFromBegin().equals(new Coordinates(-1, 1))||order.get(1).getPosFromBegin().equals(new Coordinates(-1, -1))||order.get(1).getPosFromBegin().equals(new Coordinates(1, 1))){
                        //for(int i=0;i<maxSize+1;i++)used.put(actualCoordinates.sum(order.get(i).getPosFromBegin()),true);
                    //}else {
                        for(int i=0;i<maxSize+1;i++)used.put(actualCoordinates.sub(order.get(actualLink).getPosFromBegin()).sum(order.get(i).getPosFromBegin()),true);
                     //}
                /**///System.out.println(actualCoordinates.getX()+","+actualCoordinates.getY());
                    return count+1;
                }//else return 0;
            }//else if(!setvisited)return 0;
        //return setvisited? count : 0;
        return count;
        }
    @Override
    public int getMatch(Board board,ArrayList<Link> order){
        HashMap<Coordinates, Boolean> visited=new HashMap<Coordinates,Boolean>(),used=new HashMap<Coordinates,Boolean>();
        return this.getMatch(order,used,true,visited,board,0,0,new Coordinates(0,0));
    }

}