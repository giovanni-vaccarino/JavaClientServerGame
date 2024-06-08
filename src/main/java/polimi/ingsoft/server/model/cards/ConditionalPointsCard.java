package polimi.ingsoft.server.model.cards;

import polimi.ingsoft.server.model.patterns.ItemPattern;
import polimi.ingsoft.server.model.patterns.Pattern;
import polimi.ingsoft.server.model.boards.Board;

/**
 * Interface implemented from all cards that could grant points
 */
public interface ConditionalPointsCard {

    /**
     * Returns the amount of times a certain card could be played in a player's board
     * @param board the Board that has to be controller
     * @return the amount of times a certain card could be played in a player's board
     */
    int getMatches(Board board);

    /**
     * Returns the pattern representing the conditions for a card's placement
     * @return the pattern representing the conditions for a card's placement
     */
    ItemPattern getPlayPattern();

    /**
     * Returns the card's scoring pattern
     * @return the card's scoring pattern
     */
    Pattern getPointPattern();
}
