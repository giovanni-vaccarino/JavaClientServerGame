package polimi.ingsoft.server.factories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    static ObjectMapper cardCreator;
    static File file;
    static Scanner fileReader;
    public DeckFactory(){
        this.cardCreator=new ObjectMapper();

    }


    public static Deck<ResourceCard> createResourceDeck() throws JsonProcessingException, FileNotFoundException {
        cardCreator=new ObjectMapper();
        ArrayList<ResourceCard> cards;
        CollectionType listType = cardCreator.getTypeFactory().constructCollectionType(ArrayList.class, ResourceCard.class);
        file=new File(RESOURCE);
        fileReader=new Scanner(file);
        cards=cardCreator.readValue(fileReader.nextLine(),listType);
        return new Deck<>(cards);
    }
    public static Deck<GoldCard>createGoldDeck() throws JsonProcessingException, FileNotFoundException {
        cardCreator=new ObjectMapper();
        ArrayList<GoldCard> cards;
        CollectionType listType = cardCreator.getTypeFactory().constructCollectionType(ArrayList.class, GoldCard.class);
        file=new File(GOLD);
        fileReader=new Scanner(file);
        cards=cardCreator.readValue(fileReader.nextLine(),listType);
        return new Deck<>(cards);
        }
    public static Deck<QuestCard>createQuestDeck() throws JsonProcessingException, FileNotFoundException {
        cardCreator=new ObjectMapper();
        ArrayList<QuestCard> cards;
        String s;
        int rows=0,count,score,x,y;
        Resource resource;
        ArrayList<Link> links;
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
        return new Deck<>(cards);
    }

    public static Deck<InitialCard>createInitialDeck() throws JsonProcessingException, FileNotFoundException {
        ArrayList<InitialCard> cards;
        CollectionType listType = cardCreator.getTypeFactory().constructCollectionType(ArrayList.class, InitialCard.class);
        file=new File(INITIAL);
        fileReader=new Scanner(file);
        cards=cardCreator.readValue(fileReader.nextLine(), listType);
        return new Deck<InitialCard>(cards);
    }
}
