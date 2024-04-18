package polimi.ingsoft.server.model;

import java.util.ArrayList;

public class QuestCard implements Drawable, ConditionalPointsCard {
    private SchemePattern pattern;
    private ArrayList<Link> links;
    private int points;

    public QuestCard(ArrayList<Link> links,int points){
        this.links=links;
        this.points=points;
        this.pattern=new SchemePattern(links);
    }

    public int getPoints(Board board){
        return this.points*this.pattern.getMatch(board);
        }
}
