package polimi.ingsoft.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.enumerations.Object;
import polimi.ingsoft.server.enumerations.Resource;
import polimi.ingsoft.server.model.*;
import polimi.ingsoft.server.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
Board a;
static InitialCard initial;
static ResourceCard res1,res2,res3,res4,res5,res6,res7,res8;
    @BeforeAll
    public static void init() {
        CornerSpace upleft,upright,bottomleft,bottomright;
        CenterSpace center;
        List<Item> list = new ArrayList<>();
        List<Resource> list2 =new ArrayList<>();
        upleft=new CornerSpace(new ArrayList<>());
        upright=new CornerSpace(new ArrayList<>());
        list2.add(Resource.LEAF);
        list2.add(Resource.WOLF);
        list2.add(Resource.MUSHROOM);
        center=new CenterSpace(list2);
        Face front=new Face(upleft,upright,null,null,center);
        list.add(Resource.MUSHROOM);
        upleft=new CornerSpace(list);
        list.add(Resource.WOLF);
        upright=new CornerSpace(list);
        list.add(Resource.LEAF);
        bottomleft=new CornerSpace(list);
        list.add(Resource.BUTTERFLY);
        bottomright=new CornerSpace(list);
        Face back=new Face(upleft,upright,bottomleft,bottomright);
        initial= new InitialCard("lol",front,back,0);

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
        res1=new ResourceCard("lol",front,back,0);

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

        res2=new ResourceCard("lol",front,back,0);



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
    }
    @Test
    void add() {
        a=new Board(initial,true);
        System.out.println("RES1"+a.add(new Coordinates(-1,1),res1,false));
        System.out.println("RES2"+a.add(new Coordinates(-1,-1),res2,false));
        System.out.println("RES2"+a.add(new Coordinates(1,1),res2,false));
        System.out.println("RES3"+a.add(new Coordinates(0,2),res3,true));
        System.out.println("RES4"+a.add(new Coordinates(-1,-1),res4,false));
        System.out.println("RES4"+a.add(new Coordinates(1,-1),res4,true));
        System.out.println("RES4"+a.add(new Coordinates(-2,0),res4,true));
        System.out.println("RES5"+a.add(new Coordinates(-1,-1),res5,false));
        System.out.println("RES5"+a.add(new Coordinates(2,2),res5,false));

        System.out.println("RES6"+a.add(new Coordinates(1,3),res6,false));

        System.out.println("RES6"+a.add(new Coordinates(2,0),res6,false));

        System.out.println("RES7"+a.add(new Coordinates(-2,2),res7,false));
        System.out.println("RES8"+a.add(new Coordinates(-3,1),res8,true));
        System.out.println("RES7"+a.add(new Coordinates(-2,2),res7,false));

        System.out.println("RES7"+a.add(new Coordinates(3,1),res7,false));

        System.out.println("RES7"+a.add(new Coordinates(3,1),res7,true));

        System.out.println("BUTT: "+a.getButterflies());
        System.out.println("LEAVES: "+a.getLeaves());
        System.out.println("WOLFS: "+a.getWolfs());
        System.out.println("MUSH: "+a.getMushrooms());
        System.out.println("POT: "+a.getPotions());
        System.out.println("SCR: "+a.getScrolls());
        System.out.println("FEATHERS: "+a.getFeathers());
    }

    @Test
    void check() {

    }
}