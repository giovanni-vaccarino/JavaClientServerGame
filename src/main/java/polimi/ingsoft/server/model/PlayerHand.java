package polimi.ingsoft.server.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PlayerHand extends CardCollection<MixedCard> implements Serializable {
    private ArrayList<Boolean> isFlipped;
    public PlayerHand(ResourceCard firstResourceCard, ResourceCard secondResourceCard, GoldCard goldCard){
        this.cards.add(firstResourceCard);
        this.cards.add(secondResourceCard);
        if(goldCard!=null)this.cards.add(goldCard);
        this.isFlipped=new ArrayList<>();
        isFlipped.add(false);
        isFlipped.add(false);
        isFlipped.add(false);
    }
    public MixedCard get(int index){
        return this.cards.get(index);
    }

    public List<MixedCard> getCards() {
        return this.cards;
    }

    public void add(MixedCard card) {
        this.cards.addLast(card);
        this.isFlipped.add(false);
    }

    public void remove(MixedCard card){
        int index=this.cards.indexOf(card);
        this.cards.remove(card);
        this.isFlipped.remove(index);
    }
    public void flip(int i){
        Boolean flip = !isFlipped.get(i);
        isFlipped.remove(i);
        isFlipped.add(i, flip);
    }
    public ArrayList<Boolean> getIsFlipped(){
        return this.isFlipped;
    }
}
