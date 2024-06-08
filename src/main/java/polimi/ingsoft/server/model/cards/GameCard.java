package polimi.ingsoft.server.model.cards;

import polimi.ingsoft.server.model.cards.cardstructure.CornerSpace;
import polimi.ingsoft.server.model.cards.cardstructure.Face;

import java.util.Objects;

/**
 * Represents a Card that can be played during a match
 */
public abstract class GameCard extends Card {
    private final Face front;
    private final Face back;

    /**
     * Creates a GameCard
     * @param iD a GameCard's unique identifier
     * @param front a GameCard's front face
     * @param back a GameCard's back face
     * @param score a GameCard's score
     */
    public GameCard(String iD,Face front,Face back,int score){
        super(score,iD);
        this.front=front;
        this.back=back;
    }

    /**
     * Returns a GameCard's front face
     * @return a GameCard's front face
     */
    public Face getFront(){
        return this.front;
    }
    /**
     * Returns a GameCard's back face
     * @return a GameCard's back face
     */
    public Face getBack(){
        return this.back;
    }

    /**
     * Returns a GameCard's front or back up-left CornerSpace
     * @param isFront boolean that defines wether the function should return the GameCard's front or back up-left CornerSpace
     * @return front's up-left corner when @isFront is true, back's up-left corner when @isFront is false
     */
    public CornerSpace getUpLeftCorner(boolean isFront){
        return isFront ? front.getUpLeft(): back.getUpLeft();
    }
    /**
     * Returns a GameCard's front or back up-right CornerSpace
     * @param isFront boolean that defines wether the function should return the GameCard's front or back up-right CornerSpace
     * @return front's up-right corner when @isFront is true, back's up-right corner when @isFront is false
     */
    public CornerSpace getUpRightCorner(boolean isFront){
        return isFront ? front.getUpRight(): back.getUpRight();
    }
    /**
     * Returns a GameCard's front or back bottom-left CornerSpace
     * @param isFront boolean that defines wether the function should return the GameCard's front or back bottom-left CornerSpace
     * @return front's bottom-left corner when @isFront is true, back's bottom-left corner when @isFront is false
     */
    public CornerSpace getBottomLeftCorner(boolean isFront){
        return isFront ? front.getBottomLeft(): back.getBottomLeft();
    }
    /**
     * Returns a GameCard's front or back bottom-right CornerSpace
     * @param isFront boolean that defines wether the function should return the GameCard's front or back bottom-right CornerSpace
     * @return front's bottom-right corner when @isFront is true, back's bottom-right corner when @isFront is false
     */
    public CornerSpace getBottomRightCorner(boolean isFront){
        return isFront ? front.getBottomRight(): back.getBottomRight();
    }

    /**
     * Returns GameCard's face's score
     * @param isFront defines wether the function should return the GameCard's front face's score or the GameCard's back face's score
     * @return GameCard's front face's score whend @isFront is true, GameCard's back face's score when @isFront is false
     */
    public int getScore(boolean isFront){
        return isFront ? 0 : super.getScore();
    }

    /**
     * Returns true if the controlling GameCard and a controlled object are equal
     * @param o the controlled object
     * @return true if the controlling GameCard and a controlled object are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameCard gameCard = (GameCard) o;
        return Objects.equals(front, gameCard.front) && Objects.equals(back, gameCard.back);
    }

    /**
     * Returns GameCard's hashCode
     * @return GameCard's hashCode
     */
    @Override
    public int hashCode() {
        return Objects.hash(front, back);
    }
}
