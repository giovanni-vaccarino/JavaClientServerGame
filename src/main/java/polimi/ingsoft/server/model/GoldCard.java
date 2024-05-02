package polimi.ingsoft.server.model;

import java.util.HashMap;

public class GoldCard extends MixedCard implements ConditionalPointsCard {
    ItemPattern pattern;
    public GoldCard(Face front, Face back, ItemPattern pattern,int score) {
        super(front, back,score);
        this.pattern=pattern;
    }

    @Override
    public ItemPattern getPattern() {
        return pattern;
    }

    @Override
    public int getMatches(Board board) {
        return pattern.getMatch(board)*super.getScore();
    }
}
