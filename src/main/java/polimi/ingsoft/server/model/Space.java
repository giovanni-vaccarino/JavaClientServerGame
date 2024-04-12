package polimi.ingsoft.server.model;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Space<?> space = (Space<?>) o;
        return Objects.equals(items, space.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items);
    }
}
