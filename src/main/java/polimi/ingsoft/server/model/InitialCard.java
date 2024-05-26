package polimi.ingsoft.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InitialCard extends GameCard implements Drawable {
    public InitialCard(@JsonProperty("id") String iD, @JsonProperty("front")Face front,@JsonProperty("back") Face back,@JsonProperty("score") int score) {
        super(iD,front, back,score);
    }
    public int getScore(){return 0;}

}
