package polimi.ingsoft.server.model;
import polimi.ingsoft.server.enumerations.Resource;

import java.io.Serializable;
import java.util.List;

/**
 * Class that represents a card that has been played in a player's Board
 */
public class PlayedCard implements Serializable {
    private final GameCard card;
    private boolean upRight=true;
    private boolean upLeft=true;
    private boolean downRight=true;
    private boolean downLeft=true;
    final private boolean facingUp;
    final private int order;

    /**
     * Creates a PlayedCard object
     * @param card a PlayedCard's relative card
     * @param facingUp defines if a PlayedCard is to be shown face up or down (TRUE -> UP)
     * @param order defines a PlayedCard's number inside a Board
     */
    public PlayedCard(GameCard card,boolean facingUp,int order){
        this.card=card;
        this.facingUp=facingUp;
        if(card.getUpLeftCorner(facingUp)==null)this.setUpLeft();
        if(card.getUpRightCorner(facingUp)==null)this.setUpRight();
        if(card.getBottomLeftCorner(facingUp)==null)this.setDownLeft();
        if(card.getBottomRightCorner(facingUp)==null)this.setDownRight();
        this.order=order;
        }

    /**
     * Returns a PlayedCard's relative card's CenterSpace's first Item
     * @return a PlayedCard's relative card's CenterSpace's first Item
     */
    public Resource getColor(){
        return card.getFront().getCenter().getItems().getFirst();
    }
    /**
     * Returns a PlayedCard's relative card's face showing up
     * @return a PlayedCard's relative card's face showing up
     */
    public Face getFace(){
        return facingUp ? card.getFront() : card.getBack();
    }
    /**
     * Returns a PlayedCard's relative card's face score
     * @return a PlayedCard's relative card's face score
     */
    public int getScore(){
        return facingUp? 0:card.getScore();
    }

    /**
     * Sets the up-right corner of a PlayedCard as uncoverable
     */
    public void setUpRight(){
        this.upRight=false;
    }
    /**
     * Sets the up-left corner of a PlayedCard as uncoverable
     */
    public void setUpLeft() {
        this.upLeft = false;
    }
    /**
     * Sets the down-right corner of a PlayedCard as uncoverable
     */
    public void setDownRight() {
        this.downRight = false;
    }
    /**
     * Sets the down-left corner of a PlayedCard as uncoverable
     */
    public void setDownLeft() {
        this.downLeft = false;
    }

    /**
     * Returns a PlayedCard's numberd inside a board
     * @return a PlayedCard's numberd inside a board
     */
    public int getOrder() {
        return order;
    }

    /**
     * Returns a PlayedCard's relative card
     * @return a PlayedCard's relative card
     */
    public GameCard getCard() {
        return card;
    }

    /**
     * Returns the coverability of a card's up-right corner
     * @return the coverability of a card's up-right corner
     */
    public boolean isFreeUpRight() {
        return upRight;
    }
    /**
     * Returns the coverability of a card's up-left corner
     * @return the coverability of a card's up-left corner
     */
    public boolean isFreeUpLeft() {
        return upLeft;
    }
    /**
     * Returns the coverability of a card's down-right corner
     * @return the coverability of a card's down-right corner
     */
    public boolean isFreeDownRight() {
        return downRight;
    }
    /**
     * Returns the coverability of a card's down-left corner
     * @return the coverability of a card's down-left corner
     */
    public boolean isFreeDownLeft() {
        return downLeft;
    }

    /**
     * Returns TRUE if a card has been played face up
     * @return TRUE if a card has been played face up
     */
    public boolean isFacingUp() {
        return facingUp;
    }

    /**
     * Returns a card's facing up face's up-right corner
     * @return a card's facing up face's up-right corner
     */
    public CornerSpace getUpRightCorner(){
        return card.getUpRightCorner(facingUp);
    }
    /**
     * Returns a card's facing up face's up-left corner
     * @return a card's facing up face's up-left corner
     */
    public CornerSpace getUpLeftCorner(){
        return card.getUpLeftCorner(facingUp);
    }
    /**
     * Returns a card's facing up face's down-right corner
     * @return a card's facing up face's down-right corner
     */
    public CornerSpace getBottomRightCorner(){
        return card.getBottomRightCorner(facingUp);
    }
    /**
     * Returns a card's facing up face's down-left corner
     * @return a card's facing up face's down-left corner
     */
    public CornerSpace getBottomLeftCorner(){
        return card.getBottomLeftCorner(facingUp);
    }

    /**
     * Returns a card's center space
     * @return a card's center space
     */
    public CenterSpace getCenter(){
        return card.getFront().getCenter();
    }

}
