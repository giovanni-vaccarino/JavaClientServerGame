package polimi.ingsoft.server.model;

import java.io.Serializable;

public class PlayerHand extends CardCollection<MixedCard> implements Serializable {
    public PlayerHand(){

    }
    public PlayerHand(ResourceCard firstResourceCard, ResourceCard secondResourceCard, GoldCard goldCard){
        this.cards.add(firstResourceCard);
        this.cards.add(secondResourceCard);
        this.cards.add(goldCard);
    }
    public MixedCard get(int index){
        return this.cards.get(index);
    }

    public void add(MixedCard card) {
        this.cards.addLast(card);
    }
}
