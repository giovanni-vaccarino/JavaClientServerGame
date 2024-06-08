package polimi.ingsoft.server.model.boards;
import polimi.ingsoft.server.model.items.Object;
import polimi.ingsoft.server.model.player.PlayerColor;
import polimi.ingsoft.server.model.items.Resource;
import polimi.ingsoft.server.model.cards.GameCard;
import polimi.ingsoft.server.model.items.Item;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO refactor

/**
 * Player's Board: every card placed from a player ends in this object
 */
public class Board implements Serializable {
    private final Boolean isFirstPlayer;
    private final PlayerColor color;
    private int score;
    private final Map<Coordinates, PlayedCard> cards;
    private final Map<Item,Integer> resources;

    private Coordinates printingCoordinates;

    /**
     * Board builder
     * @param initialCard defines the player's first card, always to be put at coordinates 0,0
     * @param isFaceUp defines initialcard's orientation
     * @param isFirstPlayer defines wether a player is the first player or not
     * @param color defines player's color
     */
    public Board(GameCard initialCard, boolean isFaceUp, Boolean isFirstPlayer, PlayerColor color){
        this.isFirstPlayer = isFirstPlayer;
        this.color = color;
        this.cards=new HashMap<>();
        resources=new HashMap<>();
        resources.put(Resource.WOLF,0);
        resources.put(Resource.LEAF,0);
        resources.put(Resource.MUSHROOM,0);
        resources.put(Resource.BUTTERFLY,0);
        resources.put(Object.SCROLL,0);
        resources.put(Object.POTION,0);
        resources.put(Object.FEATHER,0);
        this.add(new Coordinates(0,0),initialCard,isFaceUp);
        this.score=0;
        this.printingCoordinates=new Coordinates(0,0);
    }

    /**
     * Returns card at given coordinates
     * @param coordinates defines the position
     * @return the card at the given coordinates
     */
    public PlayedCard getCard(Coordinates coordinates){
        return cards.get(coordinates);
    }

    /**
     * Returns all the board's cards
     * @return the board's cards' hashmap
     */
    public Map<Coordinates, PlayedCard> getCards(){
        return this.cards;
    }

    /**
     * Returns a Board's resources
     * @return a Board's resources
     */
    public Map<Item, Integer> getResources() {
        return resources;
    }

    /**
     * Adds a card in board after proper controls
     * @param position defines coordinates where the card would be put
     * @param card defines the card that should be put in board
     * @param facingUp defines card's orientation
     * @return true if card has been placed in board,otherwise false
     */
    public boolean add(Coordinates position, GameCard card, boolean facingUp) {
        if(this.check(position)) {
            this.cards.put(position, new PlayedCard(card, facingUp,cards.size()+1));
            PlayedCard playedCard=this.cards.get(position);
            if(playedCard.getUpLeftCorner()==null)playedCard.setUpLeft();
            if(playedCard.getUpRightCorner()==null)playedCard.setUpRight();
            if(playedCard.getBottomLeftCorner()==null)playedCard.setDownLeft();
            if(playedCard.getBottomRightCorner()==null)playedCard.setDownRight();

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

            if(cards.get(position).getBottomLeftCorner()!=null&&!cards.get(position).getBottomLeftCorner().getItems().isEmpty())
                resources.put(cards.get(position).getBottomLeftCorner().getItems().getFirst(),resources.get(cards.get(position).getBottomLeftCorner().getItems().getFirst())+1);

            if(cards.get(position).getBottomRightCorner()!=null&&!cards.get(position).getBottomRightCorner().getItems().isEmpty())
                resources.put(cards.get(position).getBottomRightCorner().getItems().getFirst(),resources.get(cards.get(position).getBottomRightCorner().getItems().getFirst())+1);

            if(cards.get(position).getUpLeftCorner()!=null&&!cards.get(position).getUpLeftCorner().getItems().isEmpty())
                resources.put(cards.get(position).getUpLeftCorner().getItems().getFirst(),resources.get(cards.get(position).getUpLeftCorner().getItems().getFirst())+1);

            if(cards.get(position).getUpRightCorner() != null && !cards.get(position).getUpRightCorner().getItems().isEmpty())
                resources.put(cards.get(position).getUpRightCorner().getItems().getFirst(), resources.get(cards.get(position).getUpRightCorner().getItems().getFirst())+1);

            if(cards.get(position).isFacingUp())
                for(int i = 0 ; i < cards.get(position).getCenter().getItems().size(); i++)
                    resources.put(cards.get(position).getCenter().getItems().get(i), resources.get(cards.get(position).getCenter().getItems().get(i))+1);

            return true;
        }
        return false;
    }

    /**
     * Controls whether a certain position(coordinate) is available for placing cards
     * @param position the position to check
     * @return true if a card can be put in the defined position, otherwise false
     */
    public boolean check(Coordinates position){
        boolean verify=false;

        if(position.equals( new Coordinates(0,0)) && !cards.containsKey(position))
            return !verify;

        if(cards.containsKey(position))
            return verify;

        if(!(cards.containsKey(position.downLeft()) || cards.containsKey(position.upLeft())
                || cards.containsKey(position.upRight()) || cards.containsKey(position.downRight())))
            return verify;

        if(cards.containsKey(position.downRight()) && !cards.get(position.downRight()).isFreeUpLeft())
            return verify;

        if(cards.containsKey(position.upRight()) && !cards.get(position.upRight()).isFreeDownLeft())
            return verify;

        if(cards.containsKey(position.upLeft()) && !cards.get(position.upLeft()).isFreeDownRight())
            return verify;

        if(cards.containsKey(position.downLeft()) && !cards.get(position.downLeft()).isFreeUpRight())
            return verify;

        return !verify;
    }

    /**
     * Updates player's score
     * @param points defines the amount of points that should be added to the board's player's score
     */
    public void updatePoints(int points){
        score += points;
    }

    /**
     * Returns the amount of points a player has
     * @return the amount of points a player has
     */
    public Integer getScore(){return this.score;}

    /**
     * Returns player's color
     * @return player's color
     */
    public PlayerColor getColor(){return this.color;}

    /**
     * Return a list containing all positions available for card placing in board
     * @return a list containing all positions available for card placing in board
     */
    public List<Coordinates> getAvailablePlaces(){
        List<Coordinates> freePlaces = new ArrayList<>();
        HashMap<Coordinates,Boolean> visited = new HashMap<>();

        return this.getAvailablePlaces(visited, freePlaces, new Coordinates(0,0));
    }

    /**
     * Private call of public getAvailablePlaces, return a list containing all positions available for card placing in board
     * @param visited defines all the already visited positions
     * @param freePlaces defines the already found free position
     * @param actualCoordinates defines the position that is now being checked
     * @return a list containing all positions available for card placing in board
     */
    private List<Coordinates>getAvailablePlaces(HashMap<Coordinates,Boolean> visited, List<Coordinates>freePlaces, Coordinates actualCoordinates){
        visited.put(actualCoordinates, true);
        Coordinates next = actualCoordinates.sum(new Coordinates(-1,1));

        if(cards.containsKey(next) && !visited.containsKey(next))
            freePlaces = getAvailablePlaces(visited, freePlaces, next);

        if(check(next))
            if(!freePlaces.contains(next))
                    freePlaces.add(next);

        next=actualCoordinates.sum(new Coordinates(1,1));
        if(cards.containsKey(next) && !visited.containsKey(next))
            freePlaces=getAvailablePlaces(visited,freePlaces,next);

        if(check(next))
            if(!freePlaces.contains(next))
                    freePlaces.add(next);

        next = actualCoordinates.sum(new Coordinates(-1,-1));
        if(cards.containsKey(next) && !visited.containsKey(next))
            freePlaces=getAvailablePlaces(visited,freePlaces,next);

        if(check(next))
            if(!freePlaces.contains(next))
                    freePlaces.add(next);

        next=actualCoordinates.sum(new Coordinates(1,-1));
        if(cards.containsKey(next) && !visited.containsKey(next))
            freePlaces=getAvailablePlaces(visited,freePlaces,next);

        if(check(next))
            if(!freePlaces.contains(next))
                    freePlaces.add(next);

        return freePlaces;
    }


    /**
     * Returns true if a board still has available positions, otherwise false
     * @return true if a board still has available positions, otherwise false
     */
    public boolean isNotBlocked(){
        return !getAvailablePlaces().isEmpty();
    }


    /**
     * Returns the amount of cards correctly placed in board
     * @return the amount of cards correctly placed in board
     */
    public int getNumCards(){return this.cards.size();}


    /**
     * Returns the amount of WOLF resources a player has
     * @return the amount of WOLF resources a player has
     */
    public int getWolfs(){
        return resources.get(Resource.WOLF);
    }


    /**
     * Returns the amount of LEAF resources a player has
     * @return the amount of LEAF resources a player has
     */
    public int getLeaves(){
        return resources.get(Resource.LEAF);
    }


    /**
     * Returns the amount of MUSHROOM resources a player has
     * @return the amount of MUSHROOM resources a player has
     */
    public int getMushrooms(){
        return resources.get(Resource.MUSHROOM);
    }


    /**
     * Returns the amount of BUTTERFLY resources a player has
     * @return the amount of BUTTERFLY resources a player has
     */
    public int getButterflies(){
        return resources.get(Resource.BUTTERFLY);
    }


    /**
     * Returns the amount of FEATHER objects a player has
     * @return the amount of FEATHER objects a player has
     */
    public int getFeathers(){
        return resources.get(Object.FEATHER);
    }


    /**
     * Returns the amount of SCROLL objects a player has
     * @return the amount of SCROLL objects a player has
     */
    public int getScrolls(){
        return resources.get(Object.SCROLL);
    }


    /**
     * Returns the amount of POTION objects a player has
     * @return the amount of POTION objects a player has
     */
    public int getPotions(){
        return resources.get(Object.POTION);
    }


    /**
     * Returns true if board's player is the first player, otherwise false
     * @return true if board's player is the first player, otherwise false
     */
    public Boolean getFirstPlayer() {
        return isFirstPlayer;
    }

    /**
     * returns board's printing coordinates
     * @return board's printing coordinates
     */
    public Coordinates getPrintingCoordinates(){
        return this.printingCoordinates;
    }

    /**
     * Changes board's printing coordinates adding specified X and Y values
     * @param x parameter to add printingCoordinates' position X
     * @param y parameter to add printingCoordinates' position Y
     */
    public void updatePrintingCoordinates(int x,int y){
        this.printingCoordinates=this.printingCoordinates.sum(new Coordinates(x,y));
    }
}