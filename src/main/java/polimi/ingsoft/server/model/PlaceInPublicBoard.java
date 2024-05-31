package polimi.ingsoft.server.model;

import java.io.Serializable;

public class PlaceInPublicBoard<T extends Drawable> implements Serializable {
    public enum Slots {
        DECK, SLOT_A, SLOT_B
    }

    private final Deck<T> deck;
    private T slotA;
    private T slotB;

    public PlaceInPublicBoard(Deck<T> deck){
        this.deck = deck;
        this.slotA = deck.draw();
        this.slotB = deck.draw();
    }

    public T draw(PlaceInPublicBoard.Slots slot) {
        switch (slot) {
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
        return null;
    }

    public T get(PlaceInPublicBoard.Slots slot) {
        switch (slot) {
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
        return null;
    }

    public Boolean isPlaceInPublicBoardEmpty(){
        //TODO check if slotA and slotB null are correct
        return deck.isCardCollectionEmpty() && slotA == null && slotB == null;
    }
}
