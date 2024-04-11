package polimi.ingsoft.model;

public class AdderModel {
    Integer state = 0;

    public void add(Integer number){
        this.state += number;
    }

    public Integer getState(){
        return this.state;
    }
}
