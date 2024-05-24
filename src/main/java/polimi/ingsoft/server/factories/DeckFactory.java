package polimi.ingsoft.server.factories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.StreamReadFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.CollectionType;
import polimi.ingsoft.server.model.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class DeckFactory {
private static final String RESOURCE="resourceCards.txt";
    private static final String GOLD="goldCards.txt";
    private static final String QUEST="questCards.txt";
    private static final String INITIAL="initialCards.txt";
    ObjectMapper cardCreator;
    File file;
    Scanner fileReader;
    public DeckFactory(){
        this.cardCreator=new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addKeyDeserializer(Map.class, new HashMapDeserializer());
        cardCreator.registerModule(simpleModule);
        cardCreator.configure(StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION.mappedFeature(), true);

    }


    public Deck<ResourceCard> createResourceDeck() throws JsonProcessingException, FileNotFoundException {
        ArrayList<ResourceCard> cards;
        CollectionType listType = cardCreator.getTypeFactory().constructCollectionType(ArrayList.class, ResourceCard.class);
        file=new File(RESOURCE);
        fileReader=new Scanner(file);
        cards=cardCreator.readValue(fileReader.nextLine(),listType);
        return new Deck<>(cards);
    }
    public Deck<GoldCard>createGoldDeck() throws JsonProcessingException, FileNotFoundException {
        ArrayList<GoldCard> cards;
        CollectionType listType = cardCreator.getTypeFactory().constructCollectionType(ArrayList.class, GoldCard.class);
        file=new File(GOLD);
        fileReader=new Scanner(file);
        cards=cardCreator.readValue(fileReader.nextLine(),listType);
        return new Deck<>(cards);
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
