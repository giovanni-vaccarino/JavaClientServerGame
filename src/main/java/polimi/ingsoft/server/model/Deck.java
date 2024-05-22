package polimi.ingsoft.server.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Deck<T extends Drawable> extends CardCollection<T> {


    public Deck(ArrayList<T> cards){
        this.cards=cards;
        super.shuffle();
    }
    public Deck(){
    }

    public T draw(){
        if (!this.cards.isEmpty()) {
            return this.cards.removeFirst();
        } else return null;
    }
}
