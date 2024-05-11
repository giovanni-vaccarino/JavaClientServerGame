package polimi.ingsoft.server.model;

public class ResourceCard extends MixedCard {

    public ResourceCard(String iD,Face front, Face back,int score) {
        super(iD,front, back,score);
    }

    @Override
    public int getPlayability(Board board) {
        return 1;
    }

    @Override
    public int getPoints(Board board,Coordinates coordinates) {
        return 1;
    }


}
