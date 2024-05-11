package polimi.ingsoft.server.model;

import java.util.HashMap;

public class GoldCard extends MixedCard implements ConditionalPointsCard {
    ItemPattern playPattern;
    Pattern pointPattern;
    public GoldCard(String iD,Face front, Face back, ItemPattern playPattern,Pattern pointPattern,int score) {
        super(iD,front, back,score);
        this.playPattern=playPattern;
        this.pointPattern=pointPattern;
    }

    @Override
    public ItemPattern getPattern() {
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
}
