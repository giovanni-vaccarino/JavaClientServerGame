package polimi.ingsoft.server.model;

import java.util.ArrayList;

public class QuestCard extends Card implements Drawable, ConditionalPointsCard {

    private ArrayList<Link> links;


    public QuestCard(ArrayList<Link> links,int points){
        super(points);
        this.links=links;
    }

    public ArrayList<Link> getLink(){
        return this.links;
        }
}
