package polimi.ingsoft.client.ui.cli;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.model.items.Object;
import polimi.ingsoft.server.model.player.PlayerColor;
import polimi.ingsoft.server.model.items.Resource;
import polimi.ingsoft.server.model.boards.Board;
import polimi.ingsoft.server.model.boards.Coordinates;
import polimi.ingsoft.server.model.cards.GoldCard;
import polimi.ingsoft.server.model.cards.InitialCard;
import polimi.ingsoft.server.model.cards.ResourceCard;
import polimi.ingsoft.server.model.cards.cardstructure.CenterSpace;
import polimi.ingsoft.server.model.cards.cardstructure.CornerSpace;
import polimi.ingsoft.server.model.cards.cardstructure.Face;
import polimi.ingsoft.server.model.items.Item;
import polimi.ingsoft.server.model.patterns.CornerPattern;
import polimi.ingsoft.server.model.patterns.ItemPattern;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class ClientBoardTest {
    static Board board;
    static InitialCard a;
    static GoldCard gold;
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
        //list2.add(Resource.MUSHROOM);
        //list2=new ArrayList<>();
        //list2.add(Resource.BUTTERFLY);
        center=new CenterSpace(list2);
        front=new Face(upleft,null/*upright*/,bottomleft,bottomright,center);

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
        board=new Board(a,true,true, PlayerColor.RED);

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
    }

    @Test
    void printBoard() {
        //board.add(new Coordinates(1,1),gold,true);
        board.add(new Coordinates(-1,1),gold,false);
        board.add(new Coordinates(-1,-1),res1,true);
        board.add(new Coordinates(1,-1),res2,false);
        board.add(new Coordinates(-2,-2),res3,true);
        ClientBoard.printBoard(board);
        ClientBoard.printBoard(board,BoardArgument.DOWNLEFT);
    }
}

