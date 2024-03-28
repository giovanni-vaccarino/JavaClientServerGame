package polimi.ingsoft.model;

// TODO change generic to MixedCards
public class PlayerHand<T extends Drawable> extends CardCollection<T> {
    public T get(int index){
        return this.cards.get(index);
    }

    public void add(T card) {
        this.cards.addLast(card);
    }
}
