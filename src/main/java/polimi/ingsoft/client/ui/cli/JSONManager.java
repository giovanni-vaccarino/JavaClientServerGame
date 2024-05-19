package polimi.ingsoft.client.ui.cli;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

public class JSONManager {
    JSONObject json;
    public JSONManager(){
        this.json=new JSONObject();
        json.put("value",new ArrayList<Integer>());
    }
    public void printJson(){

        System.out.print(json);
    }
    public void put(String string,Object object){
        json.put(string,object);
    }
}
