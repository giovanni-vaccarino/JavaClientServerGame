package polimi.ingsoft;

public abstract class GameCard extends Card {
    private Face front;
    private Face back;

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

    


}
