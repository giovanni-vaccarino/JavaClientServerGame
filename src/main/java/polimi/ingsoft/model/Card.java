package polimi.ingsoft.model;

/**
 * Oggetto carta, con 2 facce orientate
 */
public abstract class Card implements Drawable {
    private int score;

    public int getScore() {
        return score;
    }
}
