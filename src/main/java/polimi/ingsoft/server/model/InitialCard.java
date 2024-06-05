package polimi.ingsoft.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents game's initial cards
 */
public class InitialCard extends GameCard implements Drawable {

    /**
     * Creates an InitialCard object
     * @param iD the InitialCard's unique ID
     * @param front the InitialCard's front face
     * @param back the InitialCard's back face
     * @param score the InitialCard's score
     */
    public InitialCard(@JsonProperty("id") String iD, @JsonProperty("front")Face front,@JsonProperty("back") Face back,@JsonProperty("score") int score) {
        super(iD,front, back,score);
    }

    /**
     * Returns InitialCard's score
     * @return InitialCard's score
     */
    public int getScore(){return 0;}

}
