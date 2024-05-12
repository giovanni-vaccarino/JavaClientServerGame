package polimi.ingsoft.server.model;

public interface ConditionalPointsCard {

    int getMatches(Board board);
    Pattern getPlayPattern();
    Pattern getPointPattern();
}
