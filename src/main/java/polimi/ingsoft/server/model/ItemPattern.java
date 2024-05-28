package polimi.ingsoft.server.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import polimi.ingsoft.server.enumerations.*;
import polimi.ingsoft.server.enumerations.Object;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

public class ItemPattern implements Pattern, Serializable {

    //@JsonSubTypes.Type(value=ItemPattern.class, name="")

    public static class MapDeserializer extends JsonDeserializer<HashMap> {
//        @Override
//        public HashMap<Item, Integer> deserializeKey(String s, DeserializationContext deserializationContext) throws IOException {
//            System.out.print(s);
//            return new HashMap<Item, Integer>();
//        }

        @Override
        public HashMap<Item,Integer> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
//            ObjectMapper mapper = new ObjectMapper();
//            System.out.println("\n\nlol"+mapper.readValue(jsonParser, HashMap.class));
            JsonNode node = jsonParser.getCodec().readTree(jsonParser);
            HashMap<Item,Integer> cost=new HashMap<>();
            Iterator<HashMap.Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                HashMap.Entry<String, JsonNode> entry = fields.next();
                String key = entry.getKey();
                JsonNode value = entry.getValue();
                Item item=null;
                if (key.startsWith("MUSHROOM")) {
                    item=Resource.MUSHROOM;
                } else if (key.startsWith("BUTTERFLY")) {
                    item=Resource.BUTTERFLY;
                } else if (key.startsWith("WOLF")) {
                    item=Resource.WOLF;
                }else if (key.startsWith("LEAF")) {
                    item=Resource.LEAF;
                }else if (key.startsWith("FEATHER")) {
                    item= Object.FEATHER;
                }else if (key.startsWith("POTION")) {
                    item=Object.POTION;
                }else if (key.startsWith("SCROLL")) {
                    item=Object.SCROLL;
                }
                cost.put(item, value.asInt());
            }
            return cost;//mapper.readValue(jsonParser, HashMap.class);
        }
    }
    @JsonDeserialize(using = MapDeserializer.class)
    private final HashMap<Item,Integer> cost;

    public ItemPattern(@JsonProperty("cost") HashMap<Item, Integer> cost) {
        this.cost = cost;
    }

    @Override
    public int getMatch(Board board,Coordinates coordinates) {
        int count=0;
        if(board.getFeathers()>=cost.get(Object.FEATHER) &&
            board.getButterflies()>=cost.get(Resource.BUTTERFLY) &&
             board.getLeaves()>=cost.get(Resource.LEAF) &&
              board.getMushrooms()>=cost.get(Resource.MUSHROOM) &&
               board.getPotions()>=cost.get(Object.POTION) &&
                board.getScrolls()>=cost.get(Object.SCROLL) &&
                 board.getWolfs()>=cost.get(Resource.WOLF)){
            if (cost.get(Object.FEATHER) != 0) count=getValue(board,cost,Object.FEATHER);
                else if (cost.get(Resource.BUTTERFLY) != 0 && board.getButterflies() < count) count=getValue(board,cost,Resource.BUTTERFLY);
                else if (cost.get(Resource.LEAF) != 0) count=getValue(board,cost,Resource.LEAF);
                else if (cost.get(Resource.MUSHROOM) != 0) count=getValue(board,cost,Resource.MUSHROOM);
                else if (cost.get(Object.POTION) != 0 ) count=getValue(board,cost,Object.POTION);
                else if (cost.get(Object.SCROLL) != 0 ) count=getValue(board,cost,Object.SCROLL);
                else if (cost.get(Resource.WOLF) != 0) count=getValue(board,cost,Resource.WOLF);
            if(cost.get(Object.FEATHER)!=0&&getValue(board,cost,Object.FEATHER)<count)count=getValue(board,cost,Object.FEATHER);
            if(cost.get(Resource.BUTTERFLY)!=0&&getValue(board,cost,Resource.BUTTERFLY)<count)count=getValue(board,cost,Resource.BUTTERFLY);
            if(cost.get(Resource.LEAF)!=0&&getValue(board,cost,Resource.LEAF)<count)count=getValue(board,cost,Resource.LEAF);
            if(cost.get(Resource.MUSHROOM)!=0&&getValue(board,cost,Resource.MUSHROOM)<count)count=getValue(board,cost,Resource.MUSHROOM);
            if(cost.get(Object.POTION)!=0&&getValue(board,cost,Object.POTION)<count)count=getValue(board,cost,Object.POTION);
            if(cost.get(Object.SCROLL)!=0&&getValue(board,cost,Object.SCROLL)<count)count=getValue(board,cost,Object.SCROLL);
            if(cost.get(Resource.WOLF)!=0&&getValue(board,cost,Resource.WOLF)<count)count=getValue(board,cost,Resource.WOLF);
            return count;
        }
        else return 0;
    }
    private int getValue(Board board,HashMap<Item,Integer> cost,Item item){
        return switch (item) {
            case Object.FEATHER -> Math.floorDiv(board.getFeathers(), cost.get(Object.FEATHER));
            case Resource.BUTTERFLY -> Math.floorDiv(board.getButterflies(), cost.get(Resource.BUTTERFLY));
            case Resource.LEAF -> Math.floorDiv(board.getLeaves(), cost.get(Resource.LEAF));
            case Resource.MUSHROOM -> Math.floorDiv(board.getMushrooms(), cost.get(Resource.MUSHROOM));
            case Object.POTION -> Math.floorDiv(board.getPotions(), cost.get(Object.POTION));
            case Object.SCROLL -> Math.floorDiv(board.getScrolls(), cost.get(Object.SCROLL));
            case Resource.WOLF -> Math.floorDiv(board.getWolfs(), cost.get(Resource.WOLF));
            default -> 0;
        };
    }
    public HashMap<Item,Integer> getCost(){
        return this.cost;
    }

}
