package polimi.ingsoft.server.factories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import netscape.javascript.JSObject;
import polimi.ingsoft.server.enumerations.Resource;
import polimi.ingsoft.server.model.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class DeckFactory {
private static final String RESOURCE="resourceCards.txt";
    private static final String GOLD="goldCards.txt";
    private static final String QUEST="questCards.txt";
    ObjectMapper json;
    public DeckFactory(){
        this.json=new ObjectMapper();
    }
    public ArrayList<ResourceCard> createResourceDeck() throws JsonProcessingException {
        ArrayList<ResourceCard> deck=new ArrayList<>();

        json.readValue(RESOURCE,ResourceCard.class);

        return deck;
    }
    public ArrayList<GoldCard>createGoldDeck()throws JsonProcessingException {
        ArrayList<GoldCard> deck=new ArrayList<>();

        json.readValue(RESOURCE,GoldCard.class);

        return deck;
        }
    public ArrayList<QuestCard>createQuestDeck()throws JsonProcessingException {
        ArrayList<QuestCard> deck = new ArrayList<>();

        json.readValue(RESOURCE, GoldCard.class);

        return deck;
    }
}
