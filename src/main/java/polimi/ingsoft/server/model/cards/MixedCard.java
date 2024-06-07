package polimi.ingsoft.server.model.cards;

import polimi.ingsoft.server.model.cards.cardstructure.Face;
import polimi.ingsoft.server.model.boards.Board;
import polimi.ingsoft.server.model.boards.Coordinates;

/**
 * Represents a card that can be put in a Card Collection
 */
public abstract class MixedCard extends GameCard implements ConditionalPointsCard {

    /**
     * Creates a MixedCard object
     * @param iD the MixedCard's unique ID
     * @param front the MixedCard's front face
     * @param back the MixedCard's back face
     * @param score the MixedCard's score
     */
    public MixedCard(String iD, Face front, Face back, int score) {
        super(iD,front, back,score);
    }

    /**
     * Returns the amount of times a certain MixedCard could be played in a player's board
     * @param board the Board that has to be controller
     * @return the amount of times a certain MixedCard could be played in a player's board
     */
    public abstract int getPlayability(Board board);

    /**
     * Returns the amount of times a Board's played cards fulfill a MixedCard's scoring pattern
     * @param board the Board that has to be controller
     * @param coordinates the coordinates that have to be checked
     * @return the amount of times a Board's played cards fulfill a MixedCard's scoring pattern
     */
    public abstract int getPoints(Board board, Coordinates coordinates);
}
