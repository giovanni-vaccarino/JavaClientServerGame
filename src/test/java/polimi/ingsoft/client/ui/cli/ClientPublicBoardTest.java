package polimi.ingsoft.client.ui.cli;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.model.items.Object;
import polimi.ingsoft.server.model.items.Resource;
import polimi.ingsoft.server.model.boards.Coordinates;
import polimi.ingsoft.server.model.boards.Link;
import polimi.ingsoft.server.model.cards.GoldCard;
import polimi.ingsoft.server.model.cards.QuestCard;
import polimi.ingsoft.server.model.cards.ResourceCard;
import polimi.ingsoft.server.model.cards.cardstructure.CenterSpace;
import polimi.ingsoft.server.model.cards.cardstructure.CornerSpace;
import polimi.ingsoft.server.model.cards.cardstructure.Face;
import polimi.ingsoft.server.model.decks.Deck;
import polimi.ingsoft.server.model.items.Item;
import polimi.ingsoft.server.model.patterns.CornerPattern;
import polimi.ingsoft.server.model.patterns.ItemPattern;
import polimi.ingsoft.server.model.patterns.SchemePattern;
import polimi.ingsoft.server.model.publicboard.PublicBoard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class ClientPublicBoardTest {
    public static ResourceCard res1,res2,res3;
    public static PublicBoard publicBoard;
    public static QuestCard quest,quest2;
    public static GoldCard gold;
    public static ArrayList<ResourceCard> resources;
    public static   ArrayList<GoldCard> goldCards;
    public static   ArrayList<QuestCard> quests;

    @BeforeAll
    public static void init(){
        CornerSpace upleft,upright,bottomleft,bottomright;
        CenterSpace center;
        List<Item> list = new ArrayList<>();
        List<Resource> list2 =new ArrayList<>();
        Face front,back;

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
        res1=new ResourceCard("lol",front,back,2);

        list=new ArrayList<>();
        list2=new ArrayList<>();
        list.add(Resource.LEAF);
        upleft=new CornerSpace(list);
        list=new ArrayList<>();
        list.add(Object.SCROLL);
        upright=new CornerSpace(list);
        bottomleft=new CornerSpace(list);
        bottomright=new CornerSpace(list);
        list2.add(Resource.MUSHROOM);
        center=new CenterSpace(list2);
        front=new Face(upleft,upright,bottomleft,bottomright,center);

        list=new ArrayList<>();
        list.add(Object.SCROLL);
        upleft=new CornerSpace(list);
        list=new ArrayList<>();
        list.add(Resource.BUTTERFLY);
        upright=new CornerSpace(list);
//        list=new ArrayList<>();
//        list.add(Resource.WOLF);
//        bottomleft=new CornerSpace(list);
        list=new ArrayList<>();
        list.add(Resource.MUSHROOM);
        bottomright=new CornerSpace(list);
        back=new Face(upleft,upright,null,bottomright);
        res2=new ResourceCard("lol",front,back,2);



        list=new ArrayList<>();
        list2=new ArrayList<>();
        upleft=new CornerSpace(list);
        upright=new CornerSpace(list);
        bottomleft=new CornerSpace(list);
        bottomright=new CornerSpace(list);
        list2.add(Resource.BUTTERFLY);
        center=new CenterSpace(list2);
        front=new Face(upleft,upright,bottomleft,bottomright,center);

//        list=new ArrayList<>();
//        list.add(Object.SCROLL);
//        upleft=new CornerSpace(list);
        list=new ArrayList<>();
        list.add(Resource.BUTTERFLY);
        upright=new CornerSpace(list);
        list=new ArrayList<>();
//        list.add(Resource.WOLF);
        bottomleft=new CornerSpace(list);
//        list=new ArrayList<>();
//        list.add(Resource.MUSHROOM);
        bottomright=new CornerSpace(list);
        back=new Face(null,upright,bottomleft,bottomright);
        res3=new ResourceCard("lol",front,back,1);


        list=new ArrayList<>();
        list2=new ArrayList<>();
        list.add(Resource.BUTTERFLY);
        upleft=new CornerSpace(list);
        list=new ArrayList<>();
        upright=new CornerSpace(list);
        bottomleft=new CornerSpace(list);
        list.add(Resource.LEAF);
        bottomright=new CornerSpace(list);
        list2.add(Resource.WOLF);
        center=new CenterSpace(list2);
        front=new Face(upleft,upright,bottomleft,bottomright,center);

        list=new ArrayList<>();
        list.add(Object.POTION);
        upleft=new CornerSpace(list);
        list=new ArrayList<>();
        list.add(Resource.MUSHROOM);
        upright=new CornerSpace(list);
//        list=new ArrayList<>();
//        list.add(Resource.WOLF);
//        bottomleft=new CornerSpace(list);
        list=new ArrayList<>();
        list.add(Resource.WOLF);
        bottomright=new CornerSpace(list);
        back=new Face(upleft,upright,null,bottomright);

        HashMap<Resource,Integer> cost=new HashMap<>();
        cost.put(Resource.LEAF,2);
        cost.put(Resource.WOLF,0);
        cost.put(Resource.MUSHROOM,0);
        cost.put(Resource.BUTTERFLY,0);
        gold=new GoldCard("lol",front,back,new ItemPattern(new HashMap<>(cost)),new CornerPattern(),5);

        ArrayList<Link> links=new ArrayList<>();
        links.addFirst(new Link(Resource.WOLF,new Coordinates(0,0)));
        links.add(new Link(Resource.WOLF,new Coordinates(1,1)));
        links.add(new Link(Resource.WOLF,new Coordinates(2,2)));
        quest=new QuestCard("id",new SchemePattern(links),3);

        HashMap<Item,Integer>costquest=new HashMap<>();
        costquest.put(Resource.LEAF,3);
        costquest.put(Resource.WOLF,0);
        costquest.put(Resource.MUSHROOM,0);
        costquest.put(Resource.BUTTERFLY,0);
        costquest.put(Object.SCROLL,0);
        costquest.put(Object.POTION,0);
        costquest.put(Object.FEATHER,0);
        quest2=new QuestCard("id",new ItemPattern(costquest),1);

        resources=new ArrayList<>();
        resources.add(res1);
        resources.add(res2);
//        resources.add(res3);
        goldCards=new ArrayList<>();
        goldCards.add(gold);
        goldCards.add(gold);
//        goldCards.add(gold);
//        goldCards.add(gold);
        quests=new ArrayList<>();
        quests.add(quest);
        quests.add(quest2);



    }

    @Test
    void printClientPublicBoard() {
        publicBoard=new PublicBoard(new Deck<>(resources),new Deck<>(goldCards),new Deck<>(quests),null);
        ClientPublicBoard.printPublicBoard(publicBoard.getPublicBoardResource(),publicBoard.getPublicBoardGold(),publicBoard.getPublicBoardQuest());
        this.init();
        resources.add(res3);
        goldCards.add(gold);
        goldCards.add(gold);
        publicBoard=new PublicBoard(new Deck<>(resources),new Deck<>(goldCards),new Deck<>(quests),null);
        ClientPublicBoard.printPublicBoard(publicBoard.getPublicBoardResource(),publicBoard.getPublicBoardGold(),publicBoard.getPublicBoardQuest());
    }
}