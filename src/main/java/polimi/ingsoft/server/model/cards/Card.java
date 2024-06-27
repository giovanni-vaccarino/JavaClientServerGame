package polimi.ingsoft.server.model.cards;

import java.io.Serializable;

public abstract class Card implements Drawable, Serializable, Cloneable {
    private final int score;
    private final String iD;

    /**
     * Card creator
     * @param score  amount of points the card should grant
     * @param iD card's unique identifier
     */
    protected Card(int score,String iD){
        this.score=score;
        this.iD=iD;
    }

    /**
     * returns the card's score
     * @return the card's score
     */
    public int getScore() {
        return score;
    }

    /**
     * returns the card's id
     * @return the card's id
     */
    public String getID(){
        return this.iD;
    }


    @Override
    public Card clone() {
        try {
            Card clone = (Card) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
