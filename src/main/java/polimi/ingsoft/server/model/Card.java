package polimi.ingsoft.server.model;

/**
 * Oggetto carta, con 2 facce orientate
 */
public abstract class Card implements Drawable {
    private final int score;

    protected Card(int score){
        this.score=score;
    }
    protected int getScore() {
        return score;
    }
}
