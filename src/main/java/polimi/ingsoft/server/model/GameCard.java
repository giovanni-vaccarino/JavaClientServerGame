package polimi.ingsoft.server.model;

import java.util.Objects;

public abstract class GameCard extends Card {
    private final Face front;
    private final Face back;

    public GameCard(Face front,Face back,int score){
        super(score);
        this.front=front;
        this.back=back;
    }

    public Face getFront(){
        return this.front;
    }

    public Face getBack(){
        return this.back;
    }

    public CornerSpace getUpLeftCorner(boolean isFront){
        return isFront ? front.getUpLeft(): back.getUpLeft();
    }

    public CornerSpace getUpRightCorner(boolean isFront){
        return isFront ? front.getUpRight(): back.getUpRight();
    }

    public CornerSpace getBottomLeftCorner(boolean isFront){
        return isFront ? front.getBottomLeft(): back.getBottomLeft();
    }

    public CornerSpace getBottomRightCorner(boolean isFront){
        return isFront ? front.getBottomRight(): back.getBottomRight();
    }

    public int getScore(boolean isFront){
        return isFront?0:super.getScore();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameCard gameCard = (GameCard) o;
        return Objects.equals(front, gameCard.front) && Objects.equals(back, gameCard.back);
    }

    @Override
    public int hashCode() {
        return Objects.hash(front, back);
    }
}
