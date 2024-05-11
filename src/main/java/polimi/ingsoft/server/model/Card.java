package polimi.ingsoft.server.model;

/**
 * Oggetto carta, con 2 facce orientate
 */
public abstract class Card implements Drawable {
    private final int score;
    private final String iD;
    protected Card(int score,String iD){
        this.score=score;
        this.iD=iD;
    }
    protected int getScore() {
        return score;
    }
    public String getID(){
        return this.iD;
    }
}
