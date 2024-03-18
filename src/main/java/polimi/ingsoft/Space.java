package src.main.java.polimi.ingsoft;

import java.util.List;

public abstract class Space<T extends Item> {

    List<Item> items;
    public abstract List<T> getItems();
}
