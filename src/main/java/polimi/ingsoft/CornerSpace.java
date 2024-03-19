package polimi.ingsoft;

import java.util.List;

public class CornerSpace extends Space<Item> {
    private ItemPosition  position;

    protected CornerSpace(List<Item> items) {
        super(items);
    }

    public ItemPosition getPosition(){
        return position;
    }
}
