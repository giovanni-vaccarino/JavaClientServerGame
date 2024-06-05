package polimi.ingsoft.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Represents the game's QuestCards
 */
public class QuestCard extends Card implements Drawable, ConditionalPointsCard, Serializable {

    private final Pattern pattern;

    /**
     * Returns a QuestCard's score
     * @return a QuestCard's score
     */
    public int getScore(){
        return super.getScore();
    }

    /**
     * Creates a QuestCard object
     * @param iD a QuestCard's unique ID
     * @param pattern a QuestCard's scoring pattern
     * @param points score granted from a QuestCard when its pattern is fulfilled
     */
    public QuestCard(@JsonProperty("id") String iD, @JsonProperty("pointPattern")Pattern pattern,@JsonProperty("score") int points){
        super(points,iD);
        this.pattern=pattern;
    }

    /**
     * Returns the amount of points granted from a QuestCards' fulfillment
     * @param board the Board that has to be controller
     * @return the amount of points granted from a QuestCards' fulfillment
     */
    public int getMatches(Board board) {
        return pattern.getMatch(board,new Coordinates(0,0))*super.getScore();
    }

    /**
     * Returns a QuestCard's scoring pattern
     * @return a QuestCard's scoring pattern
     */
    @Override
    public Pattern getPointPattern() {
        return pattern;
    }

    /**
     * Returns a QuestCard's play conditions (NULL as of now)
     * @return a QuestCard's play conditions (NULL as of now)
     */
    public ItemPattern getPlayPattern(){return null;}

}
