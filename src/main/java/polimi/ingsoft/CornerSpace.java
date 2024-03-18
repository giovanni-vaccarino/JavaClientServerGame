package src.main.java.polimi.ingsoft;

import java.util.List;

public class CornerSpace extends Space<Item> {
    private ItemPosition  position;
    private boolean isCovered = false;

    protected CornerSpace(List<Item> items) {
        super(items);
    }

    public ItemPosition getPosition(){
        return position;
    }

    public void cover() {
        isCovered = true;
    }

    public boolean getAvailability(){
        return !isCovered;
    }
}
