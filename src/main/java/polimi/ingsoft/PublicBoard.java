package polimi.ingsoft;

public class PublicBoard {
    Place[] place=new Place[3];

    public PublicBoard(){
        for (Place x : place)x=new Place();
        }
    public Card getCard(int space,int pos) throws IndexOutOfBoundsException{
        if(pos<0 || pos>2)return null;
        return this.place[space].getCard(pos);

    }
}

