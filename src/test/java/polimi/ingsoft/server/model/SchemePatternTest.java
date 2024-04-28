package polimi.ingsoft.server.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.enumerations.Resource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SchemePatternTest {
    static Board board;
    static ArrayList<Link> link1,link2,link3,link4,link5,link6,link7,link8;
    SchemePattern pattern;
    @BeforeAll
    public static void init(){
        ArrayList<Item> corners=new ArrayList<Item>();
        ArrayList<Resource> center=new ArrayList<Resource>();
        ResourceCard resource;
        center.add(Resource.LEAF);
        center.add(Resource.MUSHROOM);
        Face front=new Face(new CornerSpace(corners),new CornerSpace(corners),new CornerSpace(corners),new CornerSpace(corners),new CenterSpace(center)),
            back=new Face(new CornerSpace(corners),new CornerSpace(corners),new CornerSpace(corners),new CornerSpace(corners));
        InitialCard initial=new InitialCard(front,back,0);
        board=new Board(initial,true);

        center = new ArrayList<Resource>();
        center.add(Resource.MUSHROOM);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard(front, back, 0);
        board.add(new Coordinates(1, -1), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.MUSHROOM);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard(front, back, 0);
        board.add(new Coordinates(-1, 1), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.BUTTERFLY);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard(front, back, 0);
        board.add(new Coordinates(-1, -1), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.BUTTERFLY);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard(front, back, 0);
        board.add(new Coordinates(1, 1), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.BUTTERFLY);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard(front, back, 0);
        board.add(new Coordinates(0, 2), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.LEAF);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard(front, back, 0);
        board.add(new Coordinates(0, -2), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.LEAF);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard(front, back, 0);
        board.add(new Coordinates(2, 2), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.WOLF);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard(front, back, 0);
        board.add(new Coordinates(-2, -2), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.WOLF);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard(front, back, 0);
        board.add(new Coordinates(-2, 0), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.WOLF);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard(front, back, 0);
        board.add(new Coordinates(-3, -1), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.WOLF);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard(front, back, 0);
        board.add(new Coordinates(-4, -2), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.BUTTERFLY);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard(front, back, 0);
        board.add(new Coordinates(-1, -3), resource, true);
        center = new ArrayList<Resource>();
        center.add(Resource.LEAF);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard(front, back, 0);
        board.add(new Coordinates(1, -3), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.LEAF);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard(front, back, 0);
        board.add(new Coordinates(2, -4), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.LEAF);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard(front, back, 0);
        board.add(new Coordinates(3, -5), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.BUTTERFLY);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard(front, back, 0);
        board.add(new Coordinates(1, 3), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.BUTTERFLY);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard(front, back, 0);
        board.add(new Coordinates(-1, 3), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.LEAF);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard(front, back, 0);
        board.add(new Coordinates(2, 4), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.MUSHROOM);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard(front, back, 0);
        board.add(new Coordinates(-2, 4), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.MUSHROOM);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard(front, back, 0);
        board.add(new Coordinates(-3, 3), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.MUSHROOM);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard(front, back, 0);
        board.add(new Coordinates(-4, 2), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.MUSHROOM);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard(front, back, 0);
        board.add(new Coordinates(-1, 5), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.MUSHROOM);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard(front, back, 0);
        board.add(new Coordinates(0, 6), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.MUSHROOM);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard(front, back, 0);
        board.add(new Coordinates(1, 7), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.WOLF);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard(front, back, 0);
        board.add(new Coordinates(0, 4), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.MUSHROOM);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard(front, back, 0);
        board.add(new Coordinates(1, 5), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.WOLF);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard(front, back, 0);
        board.add(new Coordinates(2, 6), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.WOLF);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard(front, back, 0);
        board.add(new Coordinates(3, 7), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.WOLF);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard(front, back, 0);
        board.add(new Coordinates(4, 8), resource, true);

        link1=new ArrayList<Link>();
        link2=new ArrayList<Link>();
        link3=new ArrayList<Link>();
        link4=new ArrayList<Link>();
        link5=new ArrayList<Link>();
        link6=new ArrayList<Link>();
        link7=new ArrayList<Link>();
        link8=new ArrayList<Link>();

        link1.add(new Link(Resource.LEAF,new Coordinates(0,0)));
        link1.add(new Link(Resource.LEAF,new Coordinates(1,-1)));
        link1.add(new Link(Resource.LEAF,new Coordinates(2,-2)));

        link2.add(new Link(Resource.BUTTERFLY,new Coordinates(0,0)));
        link2.add(new Link(Resource.BUTTERFLY,new Coordinates(1,-1)));
        link2.add(new Link(Resource.BUTTERFLY,new Coordinates(2,-2)));

        link3.add(new Link(Resource.LEAF,new Coordinates(0,0)));
        link3.add(new Link(Resource.LEAF,new Coordinates(0,-2)));
        link3.add(new Link(Resource.BUTTERFLY,new Coordinates(-1,-3)));

        link4.add(new Link(Resource.MUSHROOM,new Coordinates(0,0)));
        link4.add(new Link(Resource.MUSHROOM,new Coordinates(0,-2)));
        link4.add(new Link(Resource.LEAF,new Coordinates(1,-3)));

        link5.add(new Link(Resource.BUTTERFLY,new Coordinates(0,0)));
        link5.add(new Link(Resource.BUTTERFLY,new Coordinates(0,2)));
        link5.add(new Link(Resource.WOLF,new Coordinates(-1,3)));

        link6.add(new Link(Resource.MUSHROOM,new Coordinates(0,0)));
        link6.add(new Link(Resource.MUSHROOM,new Coordinates(-1,-1)));
        link6.add(new Link(Resource.MUSHROOM,new Coordinates(-2,-2)));

        link7.add(new Link(Resource.WOLF,new Coordinates(0,0)));
        link7.add(new Link(Resource.WOLF,new Coordinates(-1,-1)));
        link7.add(new Link(Resource.WOLF,new Coordinates(-2,-2)));

        link8.add(new Link(Resource.WOLF,new Coordinates(0,0)));
        link8.add(new Link(Resource.WOLF,new Coordinates(0,2)));
        link8.add(new Link(Resource.MUSHROOM,new Coordinates(1,3)));

    }
    @Test
    void getMatch() {

        pattern=new SchemePattern();
        assertEquals(1,pattern.getMatch(board,link1));
        /**///System.out.println("RES1:"+pattern.getMatch(board));

        //pattern=new SchemePattern();
        assertEquals(1,pattern.getMatch(board,link2));
        /**///System.out.println("RES2:"+pattern.getMatch(board));

        //pattern=new SchemePattern();
        assertEquals(1,pattern.getMatch(board,link3));
        /**///System.out.println("RES3:"+pattern.getMatch(board));

        //pattern=new SchemePattern();
        assertEquals(1,pattern.getMatch(board,link4));
        /**///System.out.println("RES4:"+pattern.getMatch(board));

        //pattern=new SchemePattern();
        assertEquals(2,pattern.getMatch(board,link5));
        /**///System.out.println("RES5:"+pattern.getMatch(board));

        //pattern=new SchemePattern();
        assertEquals(2,pattern.getMatch(board,link6));
        /**///System.out.println("RES6:"+pattern.getMatch(board));

        //pattern=new SchemePattern();
        assertEquals(2,pattern.getMatch(board,link7));
        /**///System.out.println("RES7:"+pattern.getMatch(board));

        //pattern=new SchemePattern();
        assertEquals(1,pattern.getMatch(board,link8));
        /**///System.out.println("RES8:"+pattern.getMatch(board));
    }

//    void getMatch1() {
//        pattern=new SchemePattern(link1);
//        assertEquals(1,pattern.getMatch(board));
//    }
//    void getMatch2() {
//        pattern=new SchemePattern(link2);
//        assertEquals(1,pattern.getMatch(board));
//    }
//    void getMatch3() {
//        pattern=new SchemePattern(link3);
//        assertEquals(1,pattern.getMatch(board));
//    }
//    void getMatch4() {
//        pattern=new SchemePattern(link4);
//        assertEquals(1,pattern.getMatch(board));
//    }
//    void getMatch5() {
//        pattern=new SchemePattern(link5);
//        assertEquals(2,pattern.getMatch(board));
//    }
//    void getMatch6() {
//        pattern=new SchemePattern(link6);
//        assertEquals(2,pattern.getMatch(board));
//    }
//    void getMatch7() {
//        pattern=new SchemePattern(link7);
//        assertEquals(2,pattern.getMatch(board));
//    }
//    void getMatch8() {
//        pattern=new SchemePattern(link8);
//        assertEquals(1,pattern.getMatch(board));
//    }
}