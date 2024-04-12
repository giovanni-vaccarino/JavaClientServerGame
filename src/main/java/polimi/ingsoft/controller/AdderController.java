package polimi.ingsoft.controller;

import polimi.ingsoft.model.AdderModel;

public class AdderController {
    final AdderModel model;

    public AdderController(){
        this.model = new AdderModel();
    }

    public void add(Integer number){
        synchronized (this.model){
            this.model.add(number);
        }
    }

    public boolean reset(){
        synchronized (this.model){
            if(this.model.getState() == 0){
                return false;
            }else{
                this.model.add(-this.model.getState());
                return true;
            }
        }
    }

    public Integer getState(){
        return model.getState();
    }
}
