package polimi.ingsoft.server.model;

public interface ConditionalPointsCard {

    int getMatches(Board board);
    ItemPattern getPlayPattern();
    Pattern getPointPattern();
}
