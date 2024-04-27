package polimi.ingsoft.server.model;
import polimi.ingsoft.server.enumerations.*;
import polimi.ingsoft.server.enumerations.Object;

import java.util.HashMap;

public class ItemPattern implements Pattern<HashMap<Item,Integer>>{

    @Override
    public int getMatch(Board board,HashMap<Item,Integer> cost) {
        int count=0;
        if(board.getFeathers()>=cost.get(Object.FEATHER) &&
            board.getButterflies()>=cost.get(Resource.BUTTERFLY) &&
             board.getLeaves()>=cost.get(Resource.LEAF) &&
              board.getMushrooms()>=cost.get(Resource.MUSHROOM) &&
               board.getPotions()>=cost.get(Object.POTION) &&
                board.getScrolls()>=cost.get(Object.SCROLL) &&
                 board.getWolfs()>=cost.get(Resource.WOLF)){
            if(count==0) {
                if (cost.get(Object.FEATHER) != 0) count=getValue(board,cost,Object.FEATHER);
                else if (cost.get(Resource.BUTTERFLY) != 0 && board.getButterflies() < count) count=getValue(board,cost,Resource.BUTTERFLY);
                else if (cost.get(Resource.LEAF) != 0) count=getValue(board,cost,Resource.LEAF);
                else if (cost.get(Resource.MUSHROOM) != 0) count=getValue(board,cost,Resource.MUSHROOM);
                else if (cost.get(Object.POTION) != 0 ) count=getValue(board,cost,Object.POTION);
                else if (cost.get(Object.SCROLL) != 0 ) count=getValue(board,cost,Object.SCROLL);
                else if (cost.get(Resource.WOLF) != 0) count=getValue(board,cost,Resource.WOLF);
            }
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
        switch(item){
            case Object.FEATHER:
                return Math.floorDiv(board.getFeathers(),cost.get(Object.FEATHER));
            case Resource.BUTTERFLY:
                return Math.floorDiv(board.getButterflies(),cost.get(Resource.BUTTERFLY));
            case Resource.LEAF:
                return Math.floorDiv(board.getLeaves(),cost.get(Resource.LEAF));
            case Resource.MUSHROOM:
                return Math.floorDiv(board.getMushrooms(),cost.get(Resource.MUSHROOM));
            case Object.POTION:
                return Math.floorDiv(board.getPotions(),cost.get(Object.POTION));
            case Object.SCROLL:
                return Math.floorDiv(board.getScrolls(),cost.get(Object.SCROLL));
            case Resource.WOLF:
                return Math.floorDiv(board.getWolfs(),cost.get(Resource.WOLF));
            default:
                return 0;
        }
    }
}
