package polimi.ingsoft.server.model;

import java.util.ArrayList;

public class QuestCard extends Card implements Drawable, ConditionalPointsCard {

    private final Pattern pattern;

    public QuestCard(String iD,Pattern pattern,int points){
        super(points,iD);
        this.pattern=pattern;
    }
    public int getMatches(Board board) {
        return pattern.getMatch(board,new Coordinates(0,0))*super.getScore();
    }
    @Override
    public Pattern getPlayPattern() {
        return pattern;
    }
    public Pattern getPointPattern(){return null;}
}
