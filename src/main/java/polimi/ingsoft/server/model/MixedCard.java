package polimi.ingsoft.server.model;


public abstract class MixedCard extends GameCard {
    public MixedCard(Face front, Face back,int score) {
        super(front, back,score);
    }
    public abstract int getPlayability(Board board);
    public abstract int getPoints(Board board, Coordinates coordinates);
}
