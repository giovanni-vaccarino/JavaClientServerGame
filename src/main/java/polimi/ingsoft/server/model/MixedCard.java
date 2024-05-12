package polimi.ingsoft.server.model;


public abstract class MixedCard extends GameCard {
    public MixedCard(String iD,Face front, Face back,int score) {
        super(iD,front, back,score);
    }
    public abstract int getPlayability(Board board);
    public abstract int getPoints(Board board, Coordinates coordinates);
    public abstract ItemPattern getPlayPattern();
    public abstract Pattern getPointPattern();
}
