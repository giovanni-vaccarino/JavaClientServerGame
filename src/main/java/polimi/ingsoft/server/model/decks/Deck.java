package polimi.ingsoft.server.model.decks;

import polimi.ingsoft.server.model.cards.Drawable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents a collection of drawable cards
 * @param <T> Cards collection type
 */
public class Deck<T extends Drawable> extends CardCollection<T> implements  Serializable, Cloneable {

    /**
     * Creates a shuffled card collection of T type
     * @param cards list of T type cards
     */
    public Deck(ArrayList<T> cards){
        this.cards=cards;
        super.shuffle();
    }

    /**
     * Standard creator - Creates a shuffled card collection of T type with NULL attributes
     */
    public Deck(){
    }

    /**
     * returns the first card of a card collection and removes it from the collection
     * @return the first card of a card collection and removes it from the collection
     */
    public T draw(){
        if (!this.cards.isEmpty()) {
            return this.cards.removeFirst();
        } else return null;
    }

    /**
     * returns the first card of a card collection
     * @return the first card of a card collection
     */
    public T show() {
        if (!this.cards.isEmpty()) {
            return this.cards.getFirst();
        } else return null;
    }

    @Override
    public Deck<T> clone() {
        try {
            Deck<T> deck = (Deck<T>) super.clone();
            deck.cards = (ArrayList<T>) this.cards.clone();
            return deck;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
