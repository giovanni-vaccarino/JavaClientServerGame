package polimi.ingsoft.server.model;

import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.model.boards.Link;
import polimi.ingsoft.server.model.cards.QuestCard;
import polimi.ingsoft.server.model.items.Item;
import polimi.ingsoft.server.model.patterns.ItemPattern;
import polimi.ingsoft.server.model.patterns.SchemePattern;

import java.util.ArrayList;
import java.util.HashMap;

class QuestCardTest {
    @Test
 public void test(){
     HashMap<Item,Integer>cost=new HashMap<>();
        ArrayList<Link> links=new ArrayList<>();
     QuestCard card=new QuestCard("lol",new ItemPattern(cost),0);
     card=new QuestCard("lol",new SchemePattern(links),0);

 }
}