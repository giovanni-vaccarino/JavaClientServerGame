package polimi.ingsoft.server.model;

public class ResourceCard extends MixedCard {

    public ResourceCard(Face front, Face back,int score) {
        super(front, back,score);
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
