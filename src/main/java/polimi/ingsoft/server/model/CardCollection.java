package polimi.ingsoft.server.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/**
 * Collection of drawable cards
 * @param <T> an object of that extends the drawable interface
 */
public abstract class CardCollection<T extends Drawable> implements Serializable {
    List<T> cards = new ArrayList<>();

    /**
     * changes the cards' draw order
     */
    public void shuffle(){
        Collections.shuffle(cards);
    }

    /**
     * returns true no card is present in the collection
     * @return true no card is present in the collection
     */
    public Boolean isCardCollectionEmpty(){
        return cards.isEmpty();
    }
}
