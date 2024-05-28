package polimi.ingsoft.server.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class SchemePattern implements Pattern, Serializable {

    private ArrayList<Link> order;
    private final int maxSize=2;
    public SchemePattern(@JsonProperty("cost") ArrayList<Link> order){
        this.order=order;
    }
    private int getMatch(HashMap<Coordinates,Boolean> used,boolean setVisited,HashMap<Coordinates,Boolean> visited, Board board, int count, int actualLink, Coordinates actualCoordinates) {
        if (board.getCard(actualCoordinates/*.sum(order.get(actualLink).getPosFromBegin())*/) == null) return count;//0;
        if(setVisited)visited.put(actualCoordinates,true);
        if (setVisited) {
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
            } else if(board.getCard(actualCoordinates).getColor().equals(order.get(actualLink).getColor()) && !used.containsKey(actualCoordinates)){
                    for(int i=0;i<maxSize+1;i++)used.put(actualCoordinates.sub(order.get(actualLink).getPosFromBegin()).sum(order.get(i).getPosFromBegin()),true);
                    return count+1;
                }
            }
        return count;
        }
    @Override
    public int getMatch(Board board,Coordinates coordinates){
        HashMap<Coordinates, Boolean> visited=new HashMap<Coordinates,Boolean>(),used=new HashMap<Coordinates,Boolean>();
        return this.getMatch(used,true,visited,board,0,0,new Coordinates(0,0));
    }
    public ArrayList<Link> getOrder(){return this.order;}
//    public void setCost(@JsonProperty("cost")Object object){
//        //System.out.println(object);
//        if(object.toString()!="Order"){
//            System.out.println(((ArrayList<Link>)object).get(0).getClass());
////            try {
////                System.out.println("HASH"+(((LinkedHashMap) object).entrySet()));
////            }catch(ClassCastException e){
////                System.out.println("LIST"+((ArrayList<Link>)object).toString());
////
////                this.order=((ArrayList<Link>) object);
////                System.out.println(this.order.getFirst().getClass());
////                System.out.println(this.order.get(1).getPosFromBegin()+""+this.order.get(1).getColor());
////            }
//        }
//    }
}