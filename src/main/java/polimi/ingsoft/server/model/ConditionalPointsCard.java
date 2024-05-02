package polimi.ingsoft.server.model;

public interface ConditionalPointsCard {
    Pattern getPattern();

    int getMatches(Board board);
}
