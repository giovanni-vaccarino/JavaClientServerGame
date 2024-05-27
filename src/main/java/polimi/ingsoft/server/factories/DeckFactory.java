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
        //SimpleModule simpleModule = new SimpleModule();
        //simpleModule.addKeyDeserializer(Map.class, new MapDeserializer());
        this.cardCreator=new ObjectMapper();
        //cardCreator.registerModule(simpleModule);
        //cardCreator.configure(StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION.mappedFeature(), true); //da cancellare

    }


    public Deck<ResourceCard> createResourceDeck() throws JsonProcessingException, FileNotFoundException {
        ArrayList<ResourceCard> cards;
        CollectionType listType = cardCreator.getTypeFactory().constructCollectionType(ArrayList.class, ResourceCard.class);
        file=new File(RESOURCE);
        fileReader=new Scanner(file);
        cards=cardCreator.readValue(fileReader.nextLine(),listType);
//        for(int i=0;i<40;i++) {
//            ResourceCard card=new Deck<>(cards).draw();
//            System.out.println(cardCreator.writeValueAsString(card));
//        }
        return new Deck<>(cards);
    }
    public Deck<GoldCard>createGoldDeck() throws JsonProcessingException, FileNotFoundException {
        ArrayList<GoldCard> cards;
        CollectionType listType = cardCreator.getTypeFactory().constructCollectionType(ArrayList.class, GoldCard.class);
        file=new File(GOLD);
        fileReader=new Scanner(file);
        cards=cardCreator.readValue(fileReader.nextLine(),listType);
//        HashMap<Item,Integer> cost=new HashMap<>();//
//        cost.put(Resource.MUSHROOM,1);//
//        ItemPattern pattern=new ItemPattern(cost);//

        //System.out.println(cardCreator.writeValueAsString(new GoldCard(null,null,null,pattern,pattern,1)));//
//        for(int i=0;i<40;i++) {
//            System.out.println(cardCreator.writeValueAsString(new Deck<>(cards).draw()));//
//        }
        return new Deck<>(cards);
        }
    public Deck<QuestCard>createQuestDeck() throws JsonProcessingException, FileNotFoundException {
        ArrayList<QuestCard> cards;
        String s;
        int rows=0,count=0,score,x,y;
        Resource resource;
        ArrayList<Link> links=new ArrayList<>();
        CollectionType listType = cardCreator.getTypeFactory().constructCollectionType(ArrayList.class, QuestCard.class);
        file=new File(QUEST);
        fileReader=new Scanner(file);
        cards=cardCreator.readValue(fileReader.nextLine(), listType);
        do{
            links=new ArrayList<>();
            s=fileReader.nextLine();
            score=Integer.parseInt(s);
            count=0;
            do {
                s = fileReader.nextLine();
                resource = switch (s) {
                    case "MUSHROOM" -> Resource.MUSHROOM;
                    case "WOLF" -> Resource.WOLF;
                    case "BUTTERFLY" -> Resource.BUTTERFLY;
                    case "LEAF" -> Resource.LEAF;
                    default -> null;
                };
                s = fileReader.nextLine();
                x = Integer.parseInt(s);
                s = fileReader.nextLine();
                y = Integer.parseInt(s);
                links.add(new Link(resource, new Coordinates(x, y)));
                count++;
            }while(count<3);
            s = fileReader.nextLine();
            cards.add(new QuestCard(s,new SchemePattern(links),score));
            rows++;
        }while(rows<8);
        for(int i=0;i<16;i++) {
            System.out.println(cardCreator.writeValueAsString(new Deck<>(cards).draw()));//
        }
        return new Deck<>(cards);
    }

    public Deck<InitialCard>createInitialDeck() throws JsonProcessingException, FileNotFoundException {
        ArrayList<InitialCard> cards;
        CollectionType listType = cardCreator.getTypeFactory().constructCollectionType(ArrayList.class, InitialCard.class);
        file=new File(INITIAL);
        fileReader=new Scanner(file);
        cards=cardCreator.readValue(fileReader.nextLine(), listType);
//        for(int i=0;i<6;i++) {
//            System.out.println(cardCreator.writeValueAsString(new Deck<>(cards).draw()));//
//        }
        return new Deck<InitialCard>(cards);
    }
}
