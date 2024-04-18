package polimi.ingsoft.server.model;

import java.util.ArrayList;

public class QuestCard extends Card implements Drawable, ConditionalPointsCard {
    private SchemePattern pattern;
    private ArrayList<Link> links;


    public QuestCard(ArrayList<Link> links,int points){
        super(points);
        this.links=links;
        this.pattern=new SchemePattern(links);
    }

    public int getPoints(Board board){
        return super.getScore()*this.pattern.getMatch(board);
        }
}
