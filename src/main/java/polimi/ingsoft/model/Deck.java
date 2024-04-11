package polimi.ingsoft.model;

public class Deck<T extends Drawable> extends CardCollection<T> {

    public T draw(){
        if (!this.cards.isEmpty()) {
            return this.cards.remove(0);
        } else return null;
    }
}
