package polimi.ingsoft;
import java.util.HashMap;
public class Board {
    private final HashMap<Coordinates,PlayedCard> placedCards;

    public Board() {
        this.placedCards = new HashMap<Coordinates, PlayedCard>();
    }

    public void add(Coordinates position,PlayedCard card){
        if(this.verifica(position)) {
            this.placedCards.put(position, card);
            if(placedCards.containsKey(position.downRight()))placedCards.get(position.downRight()).setUpLeft();
            if(placedCards.containsKey(position.upRight()))placedCards.get(position.upRight()).setDownLeft();
            if(placedCards.containsKey(position.upLeft()))placedCards.get(position.upLeft()).setDownRight();
            if(placedCards.containsKey(position.downLeft()))placedCards.get(position.downLeft()).setUpRight();
            }
    }

    public boolean verifica(Coordinates position){
        boolean verify=false;
        if(placedCards.containsKey(position))return verify;
        if(!(placedCards.containsKey(position.downLeft()) || placedCards.containsKey(position.upLeft())
                || placedCards.containsKey(position.upRight()) || placedCards.containsKey(position.downRight())))return verify;
        if(placedCards.containsKey(position.downRight()) && placedCards.get(position.downRight()).getUpLeftCorner()==null)return verify;
        if(placedCards.containsKey(position.upRight()) && placedCards.get(position.upRight()).getBottomLeftCorner()==null)return verify;
        if(placedCards.containsKey(position.upLeft()) && placedCards.get(position.upLeft()).getBottomRightCorner()==null)return verify;
        if(placedCards.containsKey(position.downLeft()) && placedCards.get(position.downLeft()).getUpRightCorner()==null)return verify;
        return !verify;
        }



    }
}
