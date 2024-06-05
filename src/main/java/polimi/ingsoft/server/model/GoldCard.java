package polimi.ingsoft.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

/**
 * Represents the game Gold Cards
 */
public class GoldCard extends MixedCard {
    ItemPattern playPattern;
    Pattern pointPattern;

    /**
     * Creates a GoldCard with specified values
     * @param iD the GoldCard's unique identifier
     * @param front the GoldCard's front face
     * @param back the GoldCard's back face
     * @param playPattern the GoldCard's play condition
     * @param pointPattern the GoldCard's scoring pattern
     * @param score the GoldCard's score when its scoring condition are fulfilled
     */
    public GoldCard(@JsonProperty("id") String iD, @JsonProperty("front")Face front, @JsonProperty("back") Face back,@JsonProperty("playPattern") ItemPattern playPattern,@JsonProperty("pointPattern") Pattern pointPattern,@JsonProperty("score") int score) {
        super(iD,front, back,score);
        this.playPattern=playPattern;
        this.pointPattern=pointPattern;
    }

    /**
     * Returns the pattern representing the conditions for a GoldCard's placement
     * @return the pattern representing the conditions for a GoldCard's placement
     */
    @Override
    public ItemPattern getPlayPattern() {
        return playPattern;
    }

    /**
     * Returns the GoldCard's scoring pattern
     * @return the GoldCard's scoring pattern
     */
    public Pattern getPointPattern() {
        return pointPattern;
    }

    /**
     * Returns the amount of times a certain GoldCard could be played in a player's board
     * @param board the Board that has to be controlled
     * @return the amount of times a certain GoldCard could be played in a player's board
     */
    @Override
    public int getMatches(Board board) {
        return playPattern.getMatch(board,new Coordinates(0,0));
    }

    /**
     * Returns the amount of times a certain GoldCard could be played in a player's board
     * @param board the Board that has to be controlled
     * @return the amount of times a certain GoldCard could be played in a player's board
     */
    @Override
    public int getPlayability(Board board) {
        //TODO see how to improve this
        return this.getMatches(board);
    }

    /**
     * Returns the amount of times a Board's played cards fulfill a GoldCard's scoring pattern
     * @param board the Board that has to be controlled
     * @param coordinates the coordinates that have to be checked
     * @return the amount of times a Board's played cards fulfill a GoldCard's scoring pattern
     */
    public int getPoints(Board board, Coordinates coordinates){
        return pointPattern==null? 1:pointPattern.getMatch(board,coordinates);
    }

    /**
     * Returns GoldCard's score
     * @return GoldCard's score
     */
    public int getScore(){
        return super.getScore();
    }

}
