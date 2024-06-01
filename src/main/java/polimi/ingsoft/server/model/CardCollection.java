package polimi.ingsoft.server.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public abstract class CardCollection<T extends Drawable> implements Serializable {
    List<T> cards = new ArrayList<>();
    public void shuffle(){
        Collections.shuffle(cards);
    }

    public Boolean isCardCollectionEmpty(){
        return cards.isEmpty();
    }
}
