package polimi.ingsoft;

import java.util.ArrayList;
import java.util.List;

public class PlayerHand <T extends Drawable> extends CardCollection<T>{
    List<Card> cards=new ArrayList<Card>();

    public Card draw(){
        Card temp=cards.get();
        cards.remove(cards.get());
        return temp;
        }
}
