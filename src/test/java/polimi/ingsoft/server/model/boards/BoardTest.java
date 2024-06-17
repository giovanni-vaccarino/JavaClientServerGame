package polimi.ingsoft.server.model.boards;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.model.items.Object;
import polimi.ingsoft.server.model.player.PlayerColor;
import polimi.ingsoft.server.model.items.Resource;
import polimi.ingsoft.server.model.boards.Board;
import polimi.ingsoft.server.model.boards.Coordinates;
import polimi.ingsoft.server.model.cards.InitialCard;
import polimi.ingsoft.server.model.cards.ResourceCard;
import polimi.ingsoft.server.model.cards.cardstructure.CenterSpace;
import polimi.ingsoft.server.model.cards.cardstructure.CornerSpace;
import polimi.ingsoft.server.model.cards.cardstructure.Face;
import polimi.ingsoft.server.model.items.Item;

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
        a=new Board(initial,true,true, PlayerColor.RED);
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
        this.add();
        assertTrue(a.check(new Coordinates(3, 3)));
        assertFalse(a.check(new Coordinates(4, 4)));
    }

    @Test
    void getCard() {
        this.add();
        assertEquals(res7,a.getCard(new Coordinates(3,1)).getCard());
        assertNull(a.getCard(new Coordinates(24, 1)));
    }

    @Test
    void getCards() {
        this.add();
        assertEquals(res1,a.getCards().get(new Coordinates(-1,1)).getCard());
        assertEquals(res2,a.getCards().get(new Coordinates(1,1)).getCard());
        assertEquals(res3,a.getCards().get(new Coordinates(0,2)).getCard());
        assertEquals(res4,a.getCards().get(new Coordinates(-2,0)).getCard());
        assertEquals(res5,a.getCards().get(new Coordinates(2,2)).getCard());
        assertEquals(res6,a.getCards().get(new Coordinates(2,0)).getCard());
        assertEquals(res8,a.getCards().get(new Coordinates(-3,1)).getCard());
        assertEquals(res7,a.getCards().get(new Coordinates(3,1)).getCard());
    }

    @Test
    void getResources() {
        this.add();
        assertEquals(2,a.getResources().get(Resource.BUTTERFLY));
        assertEquals(4,a.getResources().get(Resource.LEAF));
        assertEquals(3,a.getResources().get(Resource.WOLF));
        assertEquals(2,a.getResources().get(Resource.MUSHROOM));
        assertEquals(0,a.getResources().get(Object.POTION));
        assertEquals(0,a.getResources().get(Object.SCROLL));
        assertEquals(1,a.getResources().get(Object.FEATHER));
    }

    @Test
    void updatePoints() {
        this.add();
        a.updatePoints(5);
        assertEquals(5,a.getScore());
        a.updatePoints(5);
        assertEquals(10,a.getScore());
        a.updatePoints(200);
        assertEquals(29,a.getScore());
        ;
    }

    @Test
    void getScore() {
        this.add();
        a.updatePoints(5);
        a.updatePoints(5);
        assertEquals(10,a.getScore());


    }

    @Test
    void getColor() {
        this.add();
        assertEquals(PlayerColor.RED,a.getColor());
    }

    @Test
    void getAvailablePlaces() {
        this.add();
        System.out.print(a.getAvailablePlaces());
    }

    @Test
    void isNotBlocked() {
        this.add();
        assertTrue(a.isNotBlocked());
    }

    @Test
    void getNumCards() {
        this.add();
        assertEquals(a.getCards().size(),a.getNumCards());
    }

    @Test
    void getWolfs() {
        this.add();
        assertEquals(3,a.getWolfs());
    }

    @Test
    void getLeaves() {
        this.add();
        assertEquals(4,a.getLeaves());
    }

    @Test
    void getMushrooms() {
        this.add();
        assertEquals(2,a.getMushrooms());
    }

    @Test
    void getButterflies() {
        this.add();
        assertEquals(2,a.getButterflies());
    }

    @Test
    void getFeathers() {
        this.add();
        assertEquals(1,a.getFeathers());
    }

    @Test
    void getScrolls() {
        this.add();
        assertEquals(0,a.getScrolls());
    }

    @Test
    void getPotions() {
        this.add();
        assertEquals(0,a.getPotions());
    }

    @Test
    void getFirstPlayer() {
        this.add();
        assertTrue(a.getFirstPlayer());
    }

    @Test
    void getPrintingCoordinates() {
        this.add();
        assertEquals(new Coordinates(0,0),a.getPrintingCoordinates());
    }

    @Test
    void updatePrintingCoordinates() {
        this.add();
        a.updatePrintingCoordinates(1,1);
        assertEquals(new Coordinates(1,1),a.getPrintingCoordinates());
    }
}