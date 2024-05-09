package polimi.ingsoft.client.ui.gui;

public class ScoreUtils {
    public int GetXFromScore(int s){
        int x=41; //first post default

        switch(s){
            case 0:
                x=41;
                break;
            case 1:
                x=41;
                break;
        }
        return x;
    }

    public int GetYFromScore(int s){
        int y=331;//first post default

        switch(s){
            case 0:
                y=331;
                break;
            case 1:
                y=331;
                break;
        }
        return y;
    }
}
