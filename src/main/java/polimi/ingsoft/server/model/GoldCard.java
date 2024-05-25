package polimi.ingsoft.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

public class GoldCard extends MixedCard {
    ItemPattern playPattern;
    Pattern pointPattern;
    public GoldCard(@JsonProperty("id") String iD, @JsonProperty("front")Face front, @JsonProperty("back") Face back,@JsonProperty("playPattern") ItemPattern playPattern,@JsonProperty("pointPattern") Pattern pointPattern,@JsonProperty("score") int score) {
        super(iD,front, back,score);
        this.playPattern=playPattern;
        this.pointPattern=pointPattern;
    }

    @Override
    public ItemPattern getPlayPattern() {
        return playPattern;
    }

    public Pattern getPointPattern() {
        return pointPattern;
    }

    @Override
    public int getMatches(Board board) {
        return playPattern.getMatch(board,new Coordinates(0,0));
    }

    @Override
    public int getPlayability(Board board) {
        return this.getMatches(board);
    }

    public int getPoints(Board board, Coordinates coordinates){
        return pointPattern==null? 1:pointPattern.getMatch(board,coordinates);
    }
    public int getScore(){
        return super.getScore();
    }

}
