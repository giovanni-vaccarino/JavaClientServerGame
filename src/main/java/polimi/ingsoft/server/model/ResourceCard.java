package polimi.ingsoft.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResourceCard extends MixedCard {

    public ResourceCard(@JsonProperty("id") String iD,@JsonProperty("front") Face front, @JsonProperty("back") Face back, @JsonProperty("score")int score) {
        super(iD,front, back,score);
    }

    @Override
    public int getPlayability(Board board) {
        return 1;
    }

    @Override
    public int getPoints(Board board,Coordinates coordinates) {
        return 1;
    }
    public ItemPattern getPlayPattern() {
        return null;
    }

    public Pattern getPointPattern() {
        return null;
    }
    public int getMatches(Board board){return 1;}
    public int getScore(){
        return super.getScore();
    }

}
