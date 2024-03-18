package src.main.java.polimi.ingsoft;

import java.util.List;

public class CenterSpace extends Space<Resource> {
    private ItemPosition  position;
    private boolean isCovered;

    @Override
    public List<Resource> getItems() {
        return items;
    }

    public ItemPosition getPosition(){
        return position;
    }

    public boolean getAvailability(){
        return !isCovered;
    }

    public boolean isEmpty(){
        return items.isEmpty();
    }

}
