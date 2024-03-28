package polimi.ingsoft.model;

public abstract class GameCard extends Card {
    private final Face front;
    private final Face back;

    public GameCard(Face front,Face back){
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
        return isFront ? front.getBottomRight(): back.getBottomLeft();
    }


}
