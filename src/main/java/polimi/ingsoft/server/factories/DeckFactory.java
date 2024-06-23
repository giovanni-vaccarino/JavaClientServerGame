package polimi.ingsoft.server.factories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import polimi.ingsoft.server.model.items.Resource;
import polimi.ingsoft.server.model.boards.Coordinates;
import polimi.ingsoft.server.model.boards.Link;
import polimi.ingsoft.server.model.cards.GoldCard;
import polimi.ingsoft.server.model.cards.InitialCard;
import polimi.ingsoft.server.model.cards.QuestCard;
import polimi.ingsoft.server.model.cards.ResourceCard;
import polimi.ingsoft.server.model.decks.Deck;
import polimi.ingsoft.server.model.patterns.SchemePattern;

import java.util.Scanner;

/**
 * Class that creates all game's cards and their respective deck
 */
public class DeckFactory {
    private static final String PATHCARDSJSON = "/polimi/ingsoft/demo/graphics/cards/";
    private static final String RESOURCE = PATHCARDSJSON + "resourceCards.json";
    private static final String GOLD = PATHCARDSJSON + "goldCards.json";
    private static final String QUEST = PATHCARDSJSON + "questCards.json";
    private static final String INITIAL = PATHCARDSJSON + "initialCards.json";
    static ObjectMapper cardCreator;
    static Scanner fileReader;

    /**
     * Creates a ResourceCard deck
     * @return a shuffled ResourceCard deck
     * @throws JsonProcessingException when Json problem occurs
     * @throws FileNotFoundException when file name is wrong
     */
    public static Deck<ResourceCard> createResourceDeck() throws JsonProcessingException, FileNotFoundException {
        cardCreator=new ObjectMapper();
        ArrayList<ResourceCard> cards;
        CollectionType listType = cardCreator.getTypeFactory().constructCollectionType(ArrayList.class, ResourceCard.class);
        InputStream in= DeckFactory.class.getResourceAsStream(RESOURCE);
        fileReader=new Scanner(in);
        cards=cardCreator.readValue(fileReader.nextLine(),listType);
        return new Deck<>(cards);
    }
    /**
     * Creates a GoldCard deck
     * @return a shuffled GoldCard deck
     * @throws JsonProcessingException when Json problem occurs
     * @throws FileNotFoundException when file name is wrong
     */
    public static Deck<GoldCard>createGoldDeck() throws JsonProcessingException, FileNotFoundException {
        cardCreator=new ObjectMapper();
        ArrayList<GoldCard> cards;
        CollectionType listType = cardCreator.getTypeFactory().constructCollectionType(ArrayList.class, GoldCard.class);
        InputStream in= DeckFactory.class.getResourceAsStream(GOLD);
        fileReader=new Scanner(in);
        cards=cardCreator.readValue(fileReader.nextLine(),listType);
        return new Deck<>(cards);
        }

    /**
     * Creates a QuestCard deck
     * @return a shuffled QuestCard deck
     * @throws JsonProcessingException when Json problem occurs
     * @throws FileNotFoundException when file name is wrong
     */
    public static Deck<QuestCard>createQuestDeck() throws JsonProcessingException, FileNotFoundException {
        cardCreator=new ObjectMapper();
        ArrayList<QuestCard> cards;
        String s;
        int rows=0,count,score,x,y;
        Resource resource;
        ArrayList<Link> links;
        CollectionType listType = cardCreator.getTypeFactory().constructCollectionType(ArrayList.class, QuestCard.class);
        InputStream in= DeckFactory.class.getResourceAsStream(QUEST);
        fileReader=new Scanner(in);
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

    /**
     * Creates a InitialCard deck
     * @return a shuffled InitialCard deck
     * @throws JsonProcessingException when Json problem occurs
     * @throws FileNotFoundException when file name is wrong
     */
    public static Deck<InitialCard>createInitialDeck() throws JsonProcessingException, FileNotFoundException {
        cardCreator=new ObjectMapper();
        ArrayList<InitialCard> cards;
        CollectionType listType = cardCreator.getTypeFactory().constructCollectionType(ArrayList.class, InitialCard.class);
        InputStream in= DeckFactory.class.getResourceAsStream(INITIAL);
        fileReader=new Scanner(in);
        cards=cardCreator.readValue(fileReader.nextLine(), listType);
        return new Deck<>(cards);
    }
}
