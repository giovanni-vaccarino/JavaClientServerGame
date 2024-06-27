package polimi.ingsoft.server.model.patterns;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.model.player.PlayerColor;
import polimi.ingsoft.server.model.items.Resource;
import polimi.ingsoft.server.model.boards.Board;
import polimi.ingsoft.server.model.boards.Coordinates;
import polimi.ingsoft.server.model.boards.Link;
import polimi.ingsoft.server.model.cards.InitialCard;
import polimi.ingsoft.server.model.cards.ResourceCard;
import polimi.ingsoft.server.model.cards.cardstructure.CenterSpace;
import polimi.ingsoft.server.model.cards.cardstructure.CornerSpace;
import polimi.ingsoft.server.model.cards.cardstructure.Face;
import polimi.ingsoft.server.model.items.Item;
import polimi.ingsoft.server.model.patterns.SchemePattern;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit tests for the SchemePattern class.
 */
class SchemePatternTest {
    static Board board;
    static ArrayList<Link> link1,link2,link3,link4,link5,link6,link7,link8;
    SchemePattern pattern;

    /**
     * Initializes the board and links for testing.
     */
    @BeforeAll
    public static void init(){
        ArrayList<Item> corners=new ArrayList<Item>();
        ArrayList<Resource> center=new ArrayList<Resource>();
        ResourceCard resource;
        center.add(Resource.LEAF);
        center.add(Resource.MUSHROOM);
        Face front=new Face(new CornerSpace(corners),new CornerSpace(corners),new CornerSpace(corners),new CornerSpace(corners),new CenterSpace(center)),
            back=new Face(new CornerSpace(corners),new CornerSpace(corners),new CornerSpace(corners),new CornerSpace(corners));
        InitialCard initial=new InitialCard("lol",front,back,0);
        board=new Board(initial,true,true, PlayerColor.RED);

        center = new ArrayList<Resource>();
        center.add(Resource.MUSHROOM);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard("lol",front, back, 0);
        board.add(new Coordinates(1, -1), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.MUSHROOM);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard("lol",front, back, 0);
        board.add(new Coordinates(-1, 1), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.BUTTERFLY);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard("lol",front, back, 0);
        board.add(new Coordinates(-1, -1), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.BUTTERFLY);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard("lol",front, back, 0);
        board.add(new Coordinates(1, 1), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.BUTTERFLY);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard("lol",front, back, 0);
        board.add(new Coordinates(0, 2), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.LEAF);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard("lol",front, back, 0);
        board.add(new Coordinates(0, -2), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.LEAF);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard("lol",front, back, 0);
        board.add(new Coordinates(2, 2), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.WOLF);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard("lol",front, back, 0);
        board.add(new Coordinates(-2, -2), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.WOLF);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard("lol",front, back, 0);
        board.add(new Coordinates(-2, 0), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.WOLF);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard("lol",front, back, 0);
        board.add(new Coordinates(-3, -1), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.WOLF);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard("lol",front, back, 0);
        board.add(new Coordinates(-4, -2), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.BUTTERFLY);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard("lol",front, back, 0);
        board.add(new Coordinates(-1, -3), resource, true);
        center = new ArrayList<Resource>();
        center.add(Resource.LEAF);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard("lol",front, back, 0);
        board.add(new Coordinates(1, -3), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.LEAF);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard("lol",front, back, 0);
        board.add(new Coordinates(2, -4), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.LEAF);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard("lol",front, back, 0);
        board.add(new Coordinates(3, -5), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.BUTTERFLY);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard("lol",front, back, 0);
        board.add(new Coordinates(1, 3), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.BUTTERFLY);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard("lol",front, back, 0);
        board.add(new Coordinates(-1, 3), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.LEAF);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard("lol",front, back, 0);
        board.add(new Coordinates(2, 4), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.MUSHROOM);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard("lol",front, back, 0);
        board.add(new Coordinates(-2, 4), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.MUSHROOM);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard("lol",front, back, 0);
        board.add(new Coordinates(-3, 3), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.MUSHROOM);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard("lol",front, back, 0);
        board.add(new Coordinates(-4, 2), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.MUSHROOM);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard("lol",front, back, 0);
        board.add(new Coordinates(-1, 5), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.MUSHROOM);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard("lol",front, back, 0);
        board.add(new Coordinates(0, 6), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.MUSHROOM);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard("lol",front, back, 0);
        board.add(new Coordinates(1, 7), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.WOLF);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard("lol",front, back, 0);
        board.add(new Coordinates(0, 4), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.MUSHROOM);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard("lol",front, back, 0);
        board.add(new Coordinates(1, 5), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.WOLF);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard("lol",front, back, 0);
        board.add(new Coordinates(2, 6), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.WOLF);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard("lol",front, back, 0);
        board.add(new Coordinates(3, 7), resource, true);

        center = new ArrayList<Resource>();
        center.add(Resource.WOLF);
        front = new Face(new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CornerSpace(corners), new CenterSpace(center));
        resource = new ResourceCard("lol",front, back, 0);
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

    /**
     * Tests the getMatch method of the SchemePattern class.
     * Verifies that the getMatch method returns the correct number of matched links on the board
     * based on different SchemePattern instances initialized with specific link configurations.
     * Each SchemePattern instance is tested with the assertEquals method against expected values.
     * The commented-out println statements are used for debugging purposes.
     */
    @Test
    void getMatch() {

        pattern=new SchemePattern(link1);
        assertEquals(1,pattern.getMatch(board,new Coordinates(0,0)));
        /**///System.out.println("RES1:"+pattern.getMatch(board));

        pattern=new SchemePattern(link2);
        assertEquals(1,pattern.getMatch(board,new Coordinates(0,0)));
        /**///System.out.println("RES2:"+pattern.getMatch(board));

        pattern=new SchemePattern(link3);
        assertEquals(1,pattern.getMatch(board,new Coordinates(0,0)));
        /**///System.out.println("RES3:"+pattern.getMatch(board));

        pattern=new SchemePattern(link4);
        assertEquals(1,pattern.getMatch(board,new Coordinates(0,0)));
        /**///System.out.println("RES4:"+pattern.getMatch(board));

        pattern=new SchemePattern(link5);
        assertEquals(2,pattern.getMatch(board,new Coordinates(0,0)));
        /**///System.out.println("RES5:"+pattern.getMatch(board));

        pattern=new SchemePattern(link6);
        assertEquals(2,pattern.getMatch(board,new Coordinates(0,0)));
        /**///System.out.println("RES6:"+pattern.getMatch(board));

        pattern=new SchemePattern(link7);
        assertEquals(2,pattern.getMatch(board,new Coordinates(0,0)));
        /**///System.out.println("RES7:"+pattern.getMatch(board));

        pattern=new SchemePattern(link8);
        assertEquals(1,pattern.getMatch(board,new Coordinates(0,0)));
        /**///System.out.println("RES8:"+pattern.getMatch(board));
    }

    /**
     * Tests the getOrder method of the SchemePattern class.
     * Verifies that the getOrder method correctly retrieves the order of links
     * associated with a SchemePattern instance initialized with a specific link configuration.
     * The getOrder method result is printed to the console using System.out.println.
     */
    @Test
    public void getOrder(){
        pattern=new SchemePattern(link1);
        System.out.println(pattern.getOrder());
    }
}