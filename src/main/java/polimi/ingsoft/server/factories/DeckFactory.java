package polimi.ingsoft.server.factories;

import netscape.javascript.JSObject;
import org.json.JSONObject;
import polimi.ingsoft.server.enumerations.Resource;
import polimi.ingsoft.server.model.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class DeckFactory<T extends GameCard> {
private static final String JSON="cards.txt";
    public ArrayList<T> createDeck() throws FileNotFoundException {

        String id;
        int score;
        Face front,back;
        Pattern pointPattern;
        ItemPattern playPattern;
        CornerSpace upleft,upright,bottomleft,bottomright;
        CenterSpace center;
        ArrayList<T> deck=new ArrayList<>();
        FileInputStream input = new FileInputStream(JSON);

        return deck;
    }
}
