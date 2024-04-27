package polimi.ingsoft.server.model;
import polimi.ingsoft.server.enumerations.Object;
import polimi.ingsoft.server.enumerations.Resource;

import java.util.HashMap;

// TODO refactor
public class Board {
    private final HashMap<Coordinates, PlayedCard> cards;
    private HashMap<Item,Integer> resources;
    public Board(GameCard initialcard,boolean isFaceUp){
        this.cards=new HashMap<Coordinates,PlayedCard>();
        resources=new HashMap<Item,Integer>();
        resources.put(Resource.WOLF,0);
        resources.put(Resource.LEAF,0);
        resources.put(Resource.MUSHROOM,0);
        resources.put(Resource.BUTTERFLY,0);
        resources.put(Object.SCROLL,0);
        resources.put(Object.POTION,0);
        resources.put(Object.FEATHER,0);
        this.add(new Coordinates(0,0),initialcard,isFaceUp);
    }
    public PlayedCard getCard(Coordinates coordinates){
        return cards.get(coordinates);
    }

    public boolean add(Coordinates position, GameCard card, boolean facingUp) {

        if(this.check(position)) {
            this.cards.put(position, new PlayedCard(card, facingUp));
            if (cards.containsKey(position.downRight())){
                cards.get(position.downRight()).setUpLeft();
                if(!cards.get(position.downRight()).getFace().getUpLeft().getItems().isEmpty()&&
                        resources.get(cards.get(position.downRight()).getFace().getUpLeft().getItems().getFirst())>0)
                    resources.put(cards.get(position.downRight()).getFace().getUpLeft().getItems().getFirst(),resources.get(cards.get(position.downRight()).getFace().getUpLeft().getItems().getFirst())-1);
            }
            if (cards.containsKey(position.upRight())){
                cards.get(position.upRight()).setDownLeft();
                if(!cards.get(position.upRight()).getFace().getBottomLeft().getItems().isEmpty()&&
                        resources.get(cards.get(position.upRight()).getFace().getBottomLeft().getItems().getFirst())>0)
                    resources.put(cards.get(position.upRight()).getFace().getBottomLeft().getItems().getFirst(),resources.get(cards.get(position.upRight()).getFace().getBottomLeft().getItems().getFirst())-1);
            }
            if (cards.containsKey(position.upLeft())){
                cards.get(position.upLeft()).setDownRight();
                if(!cards.get(position.upLeft()).getFace().getBottomRight().getItems().isEmpty()&&
                        resources.get(cards.get(position.upLeft()).getFace().getBottomRight().getItems().getFirst())>0)
                    resources.put(cards.get(position.upLeft()).getFace().getBottomRight().getItems().getFirst(),resources.get(cards.get(position.upLeft()).getFace().getBottomRight().getItems().getFirst())-1);
            }
            if (cards.containsKey(position.downLeft())){
                cards.get(position.downLeft()).setUpRight();
                if(!cards.get(position.downLeft()).getFace().getUpRight().getItems().isEmpty()&&
                        resources.get(cards.get(position.downLeft()).getFace().getUpRight().getItems().getFirst())>0)
                    resources.put(cards.get(position.downLeft()).getFace().getUpRight().getItems().getFirst(),resources.get(cards.get(position.downLeft()).getFace().getUpRight().getItems().getFirst())-1);
            }
            if(cards.get(position).getBottomLeftCorner()!=null&&!cards.get(position).getBottomLeftCorner().getItems().isEmpty())resources.put(cards.get(position).getBottomLeftCorner().getItems().getFirst(),resources.get(cards.get(position).getBottomLeftCorner().getItems().getFirst())+1);
            if(cards.get(position).getBottomRightCorner()!=null&&!cards.get(position).getBottomRightCorner().getItems().isEmpty())resources.put(cards.get(position).getBottomRightCorner().getItems().getFirst(),resources.get(cards.get(position).getBottomRightCorner().getItems().getFirst())+1);
            if(cards.get(position).getUpLeftCorner()!=null&&!cards.get(position).getUpLeftCorner().getItems().isEmpty())resources.put(cards.get(position).getUpLeftCorner().getItems().getFirst(),resources.get(cards.get(position).getUpLeftCorner().getItems().getFirst())+1);
            if(cards.get(position).getUpRightCorner()!=null&&!cards.get(position).getUpRightCorner().getItems().isEmpty())resources.put(cards.get(position).getUpRightCorner().getItems().getFirst(),resources.get(cards.get(position).getUpRightCorner().getItems().getFirst())+1);
            if(cards.get(position).isFacingUp())for(int i=0;i<cards.get(position).getCenter().getItems().size();i++)resources.put(cards.get(position).getCenter().getItems().get(i),resources.get(cards.get(position).getCenter().getItems().get(i))+1); //iterare per aggiunegre tutti gli elementi del center
            return true;
        }
        return false;
    }
    public boolean check(Coordinates position){
        boolean verify=false;
        if(position.equals(new Coordinates(0,0)))return !verify;
        if(cards.containsKey(position))return verify;
        if(!(cards.containsKey(position.downLeft()) || cards.containsKey(position.upLeft())
                || cards.containsKey(position.upRight()) || cards.containsKey(position.downRight())))return verify;

        if(cards.containsKey(position.downRight()) && !cards.get(position.downRight()).isFreeUpLeft())return verify;
        if(cards.containsKey(position.upRight()) && !cards.get(position.upRight()).isFreeDownLeft())return verify;
        if(cards.containsKey(position.upLeft()) && !cards.get(position.upLeft()).isFreeDownRight())return verify;
        if(cards.containsKey(position.downLeft()) && !cards.get(position.downLeft()).isFreeUpRight())return verify;
        return !verify;
    }
    public int getWolfs(){
        return resources.get(Resource.WOLF);
    }
    public int getLeaves(){
        return resources.get(Resource.LEAF);
    }
    public int getMushrooms(){
        return resources.get(Resource.MUSHROOM);
    }
    public int getButterflies(){
        return resources.get(Resource.BUTTERFLY);
    }
    public int getFeathers(){
        return resources.get(Object.FEATHER);
    }
    public int getScrolls(){
        return resources.get(Object.SCROLL);
    }
    public int getPotions(){
        return resources.get(Object.POTION);
    }
}