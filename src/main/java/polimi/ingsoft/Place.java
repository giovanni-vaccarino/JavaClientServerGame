package polimi.ingsoft;

public class Place {
    Deck deck;
    Card places[]=new Card[2];
    public Place(){
        deck=new Deck();

    }
    public Card getCard(int pos) throws IndexOutOfBoundsException{
        Card temp= deck.draw();
        if(pos==0)return temp;
        Card temp2=places[pos];
        places[pos]=temp;
        return temp2;
        }
    }
}
