package polimi.ingsoft.server.factories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import netscape.javascript.JSObject;
import polimi.ingsoft.server.enumerations.Object;
import polimi.ingsoft.server.enumerations.Resource;
import polimi.ingsoft.server.model.*;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DeckFactory {
private static final String RESOURCE="resourceCards.txt";
    private static final String GOLD="goldCards.txt";
    private static final String QUEST="questCards.txt";
    private static final String INITIAL="initialCards.txt";
    ObjectMapper cardCreator,cornerCreator,faceCreator;
    public DeckFactory(){
        this.cardCreator=new ObjectMapper();
        this.cornerCreator=new ObjectMapper();
        this.faceCreator=new ObjectMapper();
    }

    public Deck<ResourceCard> createResourceDeck() throws JsonProcessingException {
        ArrayList<ResourceCard> deck=new ArrayList<>();

        cardCreator.readValue(RESOURCE,ResourceCard.class);

        return new Deck<ResourceCard>(deck);
    }
    public Deck<GoldCard>createGoldDeck()throws JsonProcessingException {
        ArrayList<GoldCard> deck=new ArrayList<>();

        cardCreator.readValue(GOLD,GoldCard.class);

        return new Deck<GoldCard>(deck);
        }
    public Deck<QuestCard>createQuestDeck()throws JsonProcessingException {
        ArrayList<QuestCard> deck = new ArrayList<>();

        cardCreator.readValue(QUEST, GoldCard.class);

        return new Deck<QuestCard>(deck);
    }
    public Deck<InitialCard>createInitialDeck() throws JsonProcessingException{
        ArrayList<InitialCard> deck = new ArrayList<>();

        cardCreator.readValue(INITIAL, GoldCard.class);

        return new Deck<InitialCard>(deck);
    }
}
