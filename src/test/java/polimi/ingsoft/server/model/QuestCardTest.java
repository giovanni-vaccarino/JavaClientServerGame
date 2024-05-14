package polimi.ingsoft.server.model;

import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.enumerations.Resource;

import java.util.ArrayList;
import java.util.HashMap;

class QuestCardTest {
    @Test
 public void test(){
     HashMap< Item,Integer>cost=new HashMap<>();
        ArrayList<Link> links=new ArrayList<>();
     QuestCard card=new QuestCard("lol",new ItemPattern(cost),0);
     card=new QuestCard("lol",new SchemePattern(links),0);

 }
}