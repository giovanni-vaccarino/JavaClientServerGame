package polimi.ingsoft.server.model;

import java.util.List;

// TODO change generic to MixedCards
public class PlayerHand<T extends Drawable> extends CardCollection<T> {
    public PlayerHand(){

    }
    public PlayerHand(ResourceCard firstResourceCard, ResourceCard secondResourceCard, GoldCard goldCard){
        //this.cards.addLast(firstResourceCard);
        //this.cards.add(secondResourceCard);
        //this.cards.add(goldCard);
    }
    public T get(int index){
        return this.cards.get(index);
    }

    public void add(T card) {
        this.cards.addLast(card);
    }
}
