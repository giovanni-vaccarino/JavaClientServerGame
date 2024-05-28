package polimi.ingsoft.server.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Deck<T extends Drawable> extends CardCollection<T> implements  Serializable {


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

    public T show() {
        if (!this.cards.isEmpty()) {
            return this.cards.getFirst();
        } else return null;
    }
}
