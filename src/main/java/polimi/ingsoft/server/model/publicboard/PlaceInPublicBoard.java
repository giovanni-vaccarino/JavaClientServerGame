package polimi.ingsoft.server.model.publicboard;

import polimi.ingsoft.server.model.cards.Drawable;
import polimi.ingsoft.server.model.decks.Deck;

import java.io.Serializable;

/**
 * Class that represents a Public Board's rows - each distinguished from its card type
 * @param <T> Public Board's rows' card type
 */
public class PlaceInPublicBoard<T extends Drawable> implements Serializable, Cloneable {
    /**
     * Enumeration that defines the kind and amount of slots a Public Board's row can have
     */
    public enum Slots {
        DECK, SLOT_A, SLOT_B
    }

    private Deck<T> deck;
    private T slotA;
    private T slotB;

    /**
     * Creates a PlaceInPublicBoard object
     * @param deck the PlaceInPublicBoard's decl
     */
    public PlaceInPublicBoard(Deck<T> deck){
        this.deck = deck;
        this.slotA = deck.draw();
        this.slotB = deck.draw();
    }

    /**
     * Removes a Card of PlaceInPublicBoard's T type from a specified slot and substitutes it
     * @param slot the specified Slot
     * @return a Card of PlaceInPublicBoard's T type from a specified slot
     */
    public T draw(PlaceInPublicBoard.Slots slot) {
        switch (slot) {
            case null -> {
                return null;
            }
            case DECK -> {
                return deck.draw();
            }
            case SLOT_A -> {
                T result = this.slotA;
                this.slotA = deck.draw();
                return result;
            }
            case SLOT_B -> {
                T result = this.slotB;
                this.slotB = deck.draw();
                return result;
            }
        }
    }

    /**
     * Removes a Card of PlaceInPublicBoard's T type from a specified slot without substituting it
     * @param slot the specified Slot
     * @return a Card of PlaceInPublicBoard's T type from a specified slot
     */
    public T get(PlaceInPublicBoard.Slots slot) {
        switch (slot) {
            case null -> {
                return null;
            }
            case DECK -> {
                return deck.show();
            }
            case SLOT_A -> {
                return this.slotA;
            }
            case SLOT_B -> {
                return this.slotB;
            }
        }
    }

    /**
     * Returns TRUE if a PlaceInPublicBoard contains no more cards and no more card could be drawn from PlaceInPublicBoard's relative deck
     * @return TRUE if a PlaceInPublicBoard contains no more cards and no more card could be drawn from PlaceInPublicBoard's relative deck
     */
    public Boolean isPlaceInPublicBoardEmpty(){
        //TODO check if slotA and slotB null are correct
        return deck.isCardCollectionEmpty() && slotA == null && slotB == null;
    }

    @Override
    public PlaceInPublicBoard<T> clone() {
        try {
            PlaceInPublicBoard<T> clone = (PlaceInPublicBoard<T>) super.clone();
            clone.deck = deck.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
