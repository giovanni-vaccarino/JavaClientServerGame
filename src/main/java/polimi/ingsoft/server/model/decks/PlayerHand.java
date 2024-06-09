package polimi.ingsoft.server.model.decks;

import polimi.ingsoft.server.model.cards.GoldCard;
import polimi.ingsoft.server.model.cards.MixedCard;
import polimi.ingsoft.server.model.cards.ResourceCard;
import polimi.ingsoft.server.model.decks.CardCollection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that represent a player's hand
 */
public class PlayerHand extends CardCollection<MixedCard> implements Serializable, Cloneable {
    private ArrayList<Boolean> isFlipped;

    /**
     * Creates a player hand with its initial cards
     * @param firstResourceCard a player's first initial ResourceCard
     * @param secondResourceCard a player's second initial ResourceCard
     * @param goldCard a player's initial GoldCard
     */
    public PlayerHand(ResourceCard firstResourceCard, ResourceCard secondResourceCard, GoldCard goldCard){
        this.cards.add(firstResourceCard);
        this.cards.add(secondResourceCard);
        if(goldCard!=null)this.cards.add(goldCard);
        this.isFlipped=new ArrayList<>();
        isFlipped.add(false);
        isFlipped.add(false);
        isFlipped.add(false);
    }

    /**
     * Returns the card at specified index
     * @param index the index of the card that should be returned
     * @return the card at specified index
     */
    public MixedCard get(int index){
        return this.cards.get(index);
    }

    /**
     * Returns all of a player's cards
     * @return all of a player's cards
     */
    public List<MixedCard> getCards() {
        return this.cards;
    }

    /**
     * Adds a card to a player's hand
     * @param card the card that's to be added in player's hand
     */
    public void add(MixedCard card) {
        this.cards.addLast(card);
        this.isFlipped.add(false);
    }

    /**
     * Removes a specified card from a player's hand
     * @param card the card that's to be removed
     */
    public void remove(MixedCard card){
        int index=this.cards.indexOf(card);
        this.cards.remove(card);
        this.isFlipped.remove(index);
    }

    /**
     * Changes the flipped state of a card in order to print its other face
     * @param i the index of the card that's to be flipped
     */
    public void flip(int i){
        Boolean flip = !isFlipped.get(i);
        isFlipped.remove(i);
        isFlipped.add(i, flip);
    }

    /**
     * Returns PlayerHand cards' flip situation
     * @return PlayerHand cards' flip situation
     */
    public ArrayList<Boolean> getIsFlipped(){
        return this.isFlipped;
    }

    @Override
    public PlayerHand clone() {
        try {
            PlayerHand clone = (PlayerHand) super.clone();
            clone.cards = (ArrayList<MixedCard>) cards.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
