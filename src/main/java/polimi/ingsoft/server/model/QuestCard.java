package polimi.ingsoft.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;

public class QuestCard extends Card implements Drawable, ConditionalPointsCard, Serializable {

    private final Pattern pattern;
    public int getScore(){
        return super.getScore();
    }
    public QuestCard(@JsonProperty("id") String iD, @JsonProperty("pointPattern")Pattern pattern,@JsonProperty("score") int points){
        super(points,iD);
        this.pattern=pattern;
    }
    public int getMatches(Board board) {
        return pattern.getMatch(board,new Coordinates(0,0))*super.getScore();
    }
    @Override
    public Pattern getPointPattern() {
        return pattern;
    }
    public ItemPattern getPlayPattern(){return null;}

}
