package polimi.ingsoft.model;

import java.util.List;

public abstract class Space<T extends Item> {

    protected final List<T> items;

    protected Space(List<T> items) {
        this.items = items;
    }

    public List<T> getItems() {
        return items;
    }

    public boolean isEmpty(){
        return items.isEmpty();
    }
}
