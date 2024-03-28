package polimi.ingsoft.model;

import java.util.HashMap;

// TODO refactor
public class Board {
    private final HashMap<Coordinates, PlayedCard> cards = new HashMap<>();

    public boolean add(Coordinates position, GameCard card, boolean facingUp) {
        if(this.check(position)) {
            this.cards.put(position, new PlayedCard(card, facingUp));
            if (cards.containsKey(position.downRight())) cards.get(position.downRight()).setUpLeft();
            if (cards.containsKey(position.upRight())) cards.get(position.upRight()).setDownLeft();
            if (cards.containsKey(position.upLeft())) cards.get(position.upLeft()).setDownRight();
            if (cards.containsKey(position.downLeft())) cards.get(position.downLeft()).setUpRight();
            return true;
        }
        return false;
    }

    public boolean check(Coordinates position){
        boolean verify=false;
        if(cards.containsKey(position))return verify;
        if(!(cards.containsKey(position.downLeft()) || cards.containsKey(position.upLeft())
                || cards.containsKey(position.upRight()) || cards.containsKey(position.downRight())))return verify;

        if(cards.containsKey(position.downRight()) && cards.get(position.downRight()).getUpLeftCorner()==null)return verify;
        if(cards.containsKey(position.upRight()) && cards.get(position.upRight()).getBottomLeftCorner()==null)return verify;
        if(cards.containsKey(position.upLeft()) && cards.get(position.upLeft()).getBottomRightCorner()==null)return verify;
        if(cards.containsKey(position.downLeft()) && cards.get(position.downLeft()).getUpRightCorner()==null)return verify;
        return !verify;
    }
}