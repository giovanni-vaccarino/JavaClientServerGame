package polimi.ingsoft.server.model;

import java.util.ArrayList;

public class QuestCard extends Card implements Drawable, ConditionalPointsCard {

    private final Pattern pattern;

    public QuestCard(Pattern pattern,int points){
        super(points);
        this.pattern=pattern;
    }
}
