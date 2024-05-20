package polimi.ingsoft.server.factories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import netscape.javascript.JSObject;
import polimi.ingsoft.server.enumerations.Object;
import polimi.ingsoft.server.enumerations.Resource;
import polimi.ingsoft.server.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class DeckFactory {
private static final String RESOURCE="resourceCards.txt";
    private static final String GOLD="goldCards.txt";
    private static final String QUEST="questCards.txt";
    ObjectMapper cardCreator,cornerCreator,faceCreator;
    public DeckFactory(){
        this.cardCreator=new ObjectMapper();
        this.cornerCreator=new ObjectMapper();
        this.faceCreator=new ObjectMapper();
    }
    public ArrayList<ResourceCard> createResourceDeck() throws JsonProcessingException {
        ArrayList<ResourceCard> deck=new ArrayList<>();

        //cardCreator.readValue(RESOURCE,ResourceCard.class);
        //ResourceCard a=cardCreator.readValue(RESOURCE,ResourceCard.class);


        CornerSpace upleft,upright,bottomleft,bottomright;
        CenterSpace center;
        List<Item> list = new ArrayList<>();
        List<Resource> list2 =new ArrayList<>();
        Face front,back;
        PrintStream out=new PrintStream(System.out);

        list=new ArrayList<>();
        list2=new ArrayList<>();
        upleft=new CornerSpace(list);
        upright=new CornerSpace(list);
        bottomleft=new CornerSpace(list);
        bottomright=new CornerSpace(list);
        list2.add(Resource.BUTTERFLY);
        center=new CenterSpace(list2);
        front=new Face(upleft,upright,bottomleft,bottomright,center);

        list=new ArrayList<>();
        list.add(Object.FEATHER);
        upright=new CornerSpace(list);
        list=new ArrayList<>();
        list.add(Resource.WOLF);
        bottomleft=new CornerSpace(list);
        list=new ArrayList<>();
        list.add(Resource.BUTTERFLY);
        bottomright=new CornerSpace(list);
        back=new Face(null,upright,bottomleft,bottomright);
        ResourceCard res1=new ResourceCard("lol",front,back,2);
        //System.out.print(a.getID());
        out.print(cardCreator.writeValueAsString(res1));
        return deck;
    }
    public ArrayList<GoldCard>createGoldDeck()throws JsonProcessingException {
        ArrayList<GoldCard> deck=new ArrayList<>();

        cardCreator.readValue(RESOURCE,GoldCard.class);

        return deck;
        }
    public ArrayList<QuestCard>createQuestDeck()throws JsonProcessingException {
        ArrayList<QuestCard> deck = new ArrayList<>();

        cardCreator.readValue(RESOURCE, GoldCard.class);

        return deck;
    }
}
