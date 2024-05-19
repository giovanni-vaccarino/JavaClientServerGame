package polimi.ingsoft.client.ui.cli;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.enumerations.Resource;
import polimi.ingsoft.server.model.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JSONManagerTest {
    public static InitialCard initial;
    @BeforeAll
    public static void init() {
        CornerSpace upleft, upright, bottomleft, bottomright;
        CenterSpace center;
        List<Item> list = new ArrayList<>();
        List<Resource> list2 = new ArrayList<>();
        upleft = new CornerSpace(new ArrayList<>());
        upright = new CornerSpace(new ArrayList<>());
        list2.add(Resource.LEAF);
        list2.add(Resource.WOLF);
        list2.add(Resource.MUSHROOM);
        center = new CenterSpace(list2);
        Face front = new Face(upleft, upright, null, null, center);
        list = new ArrayList<>();
        list.add(Resource.MUSHROOM);
        upleft = new CornerSpace(list);
        list = new ArrayList<>();
        list.add(Resource.WOLF);
        upright = new CornerSpace(list);
        list = new ArrayList<>();
        list.add(Resource.LEAF);
        bottomleft = new CornerSpace(list);
        list = new ArrayList<>();
        list.add(Resource.BUTTERFLY);
        bottomright = new CornerSpace(list);
        Face back = new Face(upleft, upright, bottomleft, bottomright);
        initial = new InitialCard("lol", front, back, 0);
    }
    @Test
    void printJson() {
        JSONManager json=new JSONManager();
        json.put("bottomleft",initial.getBottomLeftCorner(false)!=null?initial.getBottomLeftCorner(false).getItems()!=null?initial.getBottomLeftCorner(false).getItems():"empty":"void" );
        json.put("bottomright",initial.getBottomRightCorner(false)!=null?initial.getBottomRightCorner(false).getItems()!=null?initial.getBottomRightCorner(false).getItems():"empty":"void" );
        json.printJson();
    }
}