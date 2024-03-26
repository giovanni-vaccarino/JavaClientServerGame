package polimi.ingsoft;

import java.util.ArrayList;
import java.util.List;

public class Deck<T extends Drawable> extends CardCollection<T> {
    List<Card> cards=new ArrayList<Card>();

    public  Deck(){
    }
    public Card draw(){
        Card temp=cards.getFirst();
        cards.remove(cards.getFirst());
        return temp;
    }
}
