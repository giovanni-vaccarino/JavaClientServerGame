package polimi.ingsoft;

public class PlayedCard {
    private GameCard card;
    private boolean upRight=true;
    private boolean upLeft=true;
    private boolean downRight=true;
    private boolean downLeft=true;
    final private boolean facingUp;

    public PlayedCard(GameCard card,boolean facingUp){
        this.card=card;
        this.facingUp=facingUp;
    }
    public Face getFace(){
        return facingUp ? card.getFront() : card.getBack();
    }
    public void setUpRight(){
        this.upRight=false;
    }
    public void setUpLeft() {
        this.upLeft = false;
    }
    public void setDownRight() {
        this.downRight = false;
    }
    public void setDownLeft() {
        this.downLeft = false;
    }

    public GameCard getCard() {
        return card;
    }

    public boolean isFreeUpRight() {
        return upRight;
    }

    public boolean isFreeUpLeft() {
        return upLeft;
    }

    public boolean isFreeDownRight() {
        return downRight;
    }

    public boolean isFreeDownLeft() {
        return downLeft;
    }

    public boolean isFacingUp() {
        return facingUp;
    }
}
