package polimi.ingsoft.server.model.cards;

import com.fasterxml.jackson.annotation.JsonProperty;
import polimi.ingsoft.server.model.cards.cardstructure.Face;
import polimi.ingsoft.server.model.patterns.ItemPattern;
import polimi.ingsoft.server.model.patterns.Pattern;
import polimi.ingsoft.server.model.boards.Board;
import polimi.ingsoft.server.model.boards.Coordinates;

/**
 * Object that represents game's Resource Cards
 */
public class ResourceCard extends MixedCard {
    /**
     * Creates a new ResourceCard Object
     * @param iD the ResourceCard's unique ID
     * @param front the ResourceCard's front face
     * @param back the ResourceCard's back face
     * @param score the ResourceCard's score
     */
    public ResourceCard(@JsonProperty("id") String iD, @JsonProperty("front") Face front, @JsonProperty("back") Face back, @JsonProperty("score")int score) {
        super(iD,front, back,score);
    }

    /**
     * Returns the amount of times a certain ResourceCard could be played in a player's board
     * @param board the Board that has to be controlled
     * @return the amount of times a certain ResourceCard could be played in a player's board
     */
    @Override
    public int getPlayability(Board board) {
        return 1;
    }

    /**
     * Returns the amount of times a Board's played cards fulfill a ResourceCard's scoring pattern
     * @param board the Board that has to be controlled
     * @param coordinates the coordinates that have to be checked
     * @return the amount of times a Board's played cards fulfill a ResourceCard's scoring pattern
     */
    @Override
    public int getPoints(Board board, Coordinates coordinates) {
        return 1;
    }

    /**
     * Returns the pattern representing the conditions for a ResourceCard's placement
     * @return the pattern representing the conditions for a ResourceCard's placement
     */
    public ItemPattern getPlayPattern() {
        return null;
    }
    /**
     * Returns the ResourceCard's scoring pattern
     * @return the ResourceCard's scoring pattern
     */
    public Pattern getPointPattern() {
        return null;
    }
    /**
     * Returns the amount of times a certain ResourceCard could be played in a player's board
     * @param board the Board that has to be controller
     * @return the amount of times a certain ResourceCard could be played in a player's board
     */
    public int getMatches(Board board){return 1;}

    /**
     * Returns ResourceCard's score
     * @return ResourceCard's score
     */
    public int getScore(){
        return super.getScore();
    }

}
