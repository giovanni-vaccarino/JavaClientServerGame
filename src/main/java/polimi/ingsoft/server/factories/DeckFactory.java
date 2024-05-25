package polimi.ingsoft.server.factories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.StreamReadFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.MapDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.CollectionType;
import polimi.ingsoft.server.model.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import polimi.ingsoft.server.enumerations.Resource;

import java.util.Map;
import java.util.Scanner;

public class DeckFactory {
private static final String RESOURCE="resourceCards.json";
    private static final String GOLD="goldCards.json";
    private static final String QUEST="questCards.json";
    private static final String INITIAL="initialCards.json";
    ObjectMapper cardCreator;
    File file;
    Scanner fileReader;
    public DeckFactory(){
        SimpleModule simpleModule = new SimpleModule();
        //simpleModule.addKeyDeserializer(Map.class, new MapDeserializer());
        this.cardCreator=new ObjectMapper();
        cardCreator.registerModule(simpleModule);
        cardCreator.configure(StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION.mappedFeature(), true); //da cancellare

    }


    public Deck<ResourceCard> createResourceDeck() throws JsonProcessingException, FileNotFoundException {
        ArrayList<ResourceCard> cards;
        CollectionType listType = cardCreator.getTypeFactory().constructCollectionType(ArrayList.class, ResourceCard.class);
        file=new File(RESOURCE);
        fileReader=new Scanner(file);
        cards=cardCreator.readValue(fileReader.nextLine(),listType);
        System.out.print(cardCreator.writeValueAsString(new Deck<>(cards).draw()));
        return new Deck<>(cards);
    }
    public Deck<GoldCard>createGoldDeck() throws JsonProcessingException, FileNotFoundException {
        ArrayList<GoldCard> cards;
        CollectionType listType = cardCreator.getTypeFactory().constructCollectionType(ArrayList.class, GoldCard.class);
        file=new File(GOLD);
        fileReader=new Scanner(file);
//        HashMap<Item,Integer> cost=new HashMap<>();//
//        cost.put(Resource.MUSHROOM,1);//
//        ItemPattern pattern=new ItemPattern(cost);//

        //System.out.println(cardCreator.writeValueAsString(new GoldCard(null,null,null,pattern,pattern,1)));//
        cards=cardCreator.readValue(fileReader.nextLine(),listType);
        System.out.println(cardCreator.writeValueAsString(new Deck<>(cards).draw()));
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
