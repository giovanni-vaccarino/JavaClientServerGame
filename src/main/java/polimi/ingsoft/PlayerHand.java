package polimi.ingsoft;

import java.util.ArrayList;
import java.util.List;

// TODO change generic to MixedCards
public class PlayerHand<T extends Drawable> extends CardCollection<T>{
    public T get(int index){
        return this.cards.get(index);
    }

    public void add(T card) {
        this.cards.addLast(card);
    }
}
