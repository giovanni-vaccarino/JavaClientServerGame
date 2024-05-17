package polimi.ingsoft.client.ui.cli;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import polimi.ingsoft.client.Client;
import polimi.ingsoft.server.enumerations.Object;
import polimi.ingsoft.server.enumerations.Resource;
import polimi.ingsoft.server.model.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientBoardTest {
    static ClientBoard board;
    static InitialCard a;
    static ResourceCard res1,res2,res3,res4,res5,res6,res7,res8;

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
        upleft=new CornerSpace(list);
        upright=new CornerSpace(list);
        bottomleft=new CornerSpace(list);
        bottomright=new CornerSpace(list);
        list2.add(Resource.LEAF);
        center=new CenterSpace(list2);
        front=new Face(upleft,upright,bottomleft,bottomright,center);

//        list=new ArrayList<>();
//        list.add(Object.SCROLL);
//        upleft=new CornerSpace(list);
        list=new ArrayList<>();
        list.add(Resource.LEAF);
        upright=new CornerSpace(list);
        list=new ArrayList<>();
//        list.add(Resource.WOLF);
        bottomleft=new CornerSpace(list);
//        list=new ArrayList<>();
//        list.add(Resource.MUSHROOM);
        bottomright=new CornerSpace(list);
        back=new Face(null,upright,bottomleft,bottomright);

        res4=new ResourceCard("lol",front,back,1);

        list=new ArrayList<>();
        list2=new ArrayList<>();
        upleft=new CornerSpace(list);
        upright=new CornerSpace(list);
        bottomleft=new CornerSpace(list);
        bottomright=new CornerSpace(list);
        list2.add(Resource.LEAF);
        center=new CenterSpace(list2);
        front=new Face(upleft,upright,bottomleft,bottomright,center);

//        list=new ArrayList<>();
//        list.add(Object.SCROLL);
//        upleft=new CornerSpace(list);
        list=new ArrayList<>();
        list.add(Resource.LEAF);
        upright=new CornerSpace(list);
        list=new ArrayList<>();
//        list.add(Resource.WOLF);
        bottomleft=new CornerSpace(list);
        list=new ArrayList<>();
        list.add(Resource.LEAF);
        bottomright=new CornerSpace(list);
        back=new Face(null,upright,bottomleft,bottomright);

        res5=new ResourceCard("lol",front,back,0);

        list=new ArrayList<>();
        list2=new ArrayList<>();
        upleft=new CornerSpace(list);
        upright=new CornerSpace(list);
        bottomleft=new CornerSpace(list);
        bottomright=new CornerSpace(list);
        list2.add(Resource.LEAF);
        center=new CenterSpace(list2);
        front=new Face(upleft,upright,bottomleft,bottomright,center);

//        list=new ArrayList<>();
//        list.add(Object.SCROLL);
//        upleft=new CornerSpace(list);
        list=new ArrayList<>();
        list.add(Resource.BUTTERFLY);
        upright=new CornerSpace(list);
        list=new ArrayList<>();
        list.add(Object.FEATHER);
        bottomleft=new CornerSpace(list);
        list=new ArrayList<>();
        list.add(Resource.LEAF);
        bottomright=new CornerSpace(list);
        back=new Face(null,upright,bottomleft,bottomright);

        res6=new ResourceCard("lol",front,back,0);

        list=new ArrayList<>();
        list2=new ArrayList<>();
        upleft=new CornerSpace(list);
        upright=new CornerSpace(list);
        bottomleft=new CornerSpace(list);
        bottomright=new CornerSpace(list);
        list2.add(Resource.WOLF);
        center=new CenterSpace(list2);
        front=new Face(upleft,upright,bottomleft,bottomright,center);

        list=new ArrayList<>();
        list.add(Resource.WOLF);
        upleft=new CornerSpace(list);
//        list=new ArrayList<>();
//        list.add(Resource.BUTTERFLY);
//        upright=new CornerSpace(list);
        list=new ArrayList<>();
        list.add(Resource.WOLF);
        bottomleft=new CornerSpace(list);
        list=new ArrayList<>();
//        list.add(Resource.LEAF);
        bottomright=new CornerSpace(list);
        back=new Face(upleft,null,bottomleft,bottomright);

        res7=new ResourceCard("lol",front,back,0);

        list=new ArrayList<>();
        list2=new ArrayList<>();
        upleft=new CornerSpace(list);
        upright=new CornerSpace(list);
        bottomleft=new CornerSpace(list);
        bottomright=new CornerSpace(list);
        list2.add(Resource.MUSHROOM);
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

        res8=new ResourceCard("lol",front,back,0);
        //list2.add(Resource.LEAF);
        list2.add(Resource.MUSHROOM);
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

        a=new InitialCard("lol",front,back,0);
        board=new ClientBoard(new PlayedCard(a,true,0));
    }

    @Test
    void printBoard() {
        board.put(new Coordinates(1,1),new PlayedCard(res1,true,2));
        board.put(new Coordinates(-1,1),new PlayedCard(res2,false,1));
        board.printBoard();
    }
}