package polimi.ingsoft.client.ui.cli;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.enumerations.Object;
import polimi.ingsoft.server.enumerations.Resource;
import polimi.ingsoft.server.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class ClientHandTest {
    private static ResourceCard a,b,d;
            private static GoldCard c;
    private static QuestCard quest,quest2;
    private static PlayerHand hand;
    @BeforeAll
    public static void init(){
        CornerSpace upleft,upright,bottomleft,bottomright;
        CenterSpace center;
        List<Item> list = new ArrayList<>();
        List<Resource> list2 =new ArrayList<>();
        Face front,back;
        upleft=new CornerSpace(list);
        upright=new CornerSpace(list);
        bottomleft=new CornerSpace(list);
        bottomright=new CornerSpace(list);
        //list2.add(Resource.LEAF);
        //list2.add(Resource.MUSHROOM);
        list2.add(Resource.BUTTERFLY);
        center=new CenterSpace(list2);
        front=new Face(upleft,upright,bottomleft,bottomright,center);

        list=new ArrayList<>();
        list.add(Object.SCROLL);
        upleft=new CornerSpace(list);
        list=new ArrayList<>();
        list.add(Resource.LEAF);
        upright=new CornerSpace(list);
        list=new ArrayList<>();
        list.add(Resource.WOLF);
        bottomleft=new CornerSpace(list);
        list=new ArrayList<>();
        list.add(Resource.LEAF);
        bottomright=new CornerSpace(list);
        back=new Face(upleft,null,bottomleft,bottomright);

        a=new ResourceCard("lol",front,back,2);


        list = new ArrayList<>();
        list2 =new ArrayList<>();
        upleft=new CornerSpace(list);
        upright=new CornerSpace(list);
        bottomleft=new CornerSpace(list);
        bottomright=new CornerSpace(list);
        list2.add(Resource.LEAF);
        center=new CenterSpace(list2);
        front=new Face(upleft,upright,bottomleft,bottomright,center);

        list=new ArrayList<>();
        list.add(Object.SCROLL);
        upleft=new CornerSpace(list);
        list=new ArrayList<>();
        list.add(Resource.LEAF);
        upright=new CornerSpace(list);
        list=new ArrayList<>();
        list.add(Resource.WOLF);
        bottomleft=new CornerSpace(list);
        list=new ArrayList<>();
        list.add(Resource.LEAF);
        bottomright=new CornerSpace(list);
        back=new Face(upleft,null,bottomleft,bottomright);

        b=new ResourceCard("lol",front,back,0);

        list = new ArrayList<>();
        list2 =new ArrayList<>();
        upleft=new CornerSpace(list);
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
        list.add(Resource.LEAF);
        upright=new CornerSpace(list);
        list=new ArrayList<>();
        list.add(Resource.WOLF);
        bottomleft=new CornerSpace(list);
        list=new ArrayList<>();
        list.add(Resource.LEAF);
        bottomright=new CornerSpace(list);
        back=new Face(upleft,null,bottomleft,bottomright);
        HashMap<Item,Integer>itemcost=new HashMap<>(),cost=new HashMap<>();
        cost.put(Object.FEATHER,1);
        itemcost.put(Resource.MUSHROOM,0);
        itemcost.put(Resource.BUTTERFLY,1);
        itemcost.put(Resource.WOLF,2);
        itemcost.put(Resource.LEAF,0);
        c=new GoldCard("lol",front,back,new ItemPattern(itemcost),null,/*new CornerPattern(),new ItemPattern(cost),*/2);


        list = new ArrayList<>();
        list2 =new ArrayList<>();
        upleft=new CornerSpace(list);
        upright=new CornerSpace(list);
        bottomleft=new CornerSpace(list);
        bottomright=new CornerSpace(list);
        list2.add(Resource.WOLF);
        center=new CenterSpace(list2);
        front=new Face(upleft,upright,bottomleft,bottomright,center);

        list=new ArrayList<>();
        list.add(Object.SCROLL);
        upleft=new CornerSpace(list);
        list=new ArrayList<>();
        list.add(Resource.LEAF);
        upright=new CornerSpace(list);
        list=new ArrayList<>();
        list.add(Resource.WOLF);
        bottomleft=new CornerSpace(list);
        list=new ArrayList<>();
        list.add(Resource.LEAF);
        bottomright=new CornerSpace(list);
        back=new Face(upleft,null,bottomleft,bottomright);

        d=new ResourceCard("lol",front,back,0);

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

    }
    @Test
    void print() {
        hand=new PlayerHand(a,a,c);
        System.out.print("\n\n\n");
        ClientHand.print(hand,null);
        ClientHand.print(hand,quest2);
        ClientHand.print(hand,quest);
        hand.flip(0);
        hand.flip(1);
        hand.flip(2);
        ClientHand.print(hand,null);
        System.out.print("\n\n\n");
        hand.flip(1);
        hand.flip(2);
        ClientHand.print(hand,null);

    }
}