package polimi.ingsoft.server.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Class that represents all GameCards spaces
 * @param <T> the type of Item a space contains
 */
public abstract class Space<T extends Item> implements Serializable {

    protected final List<T> items;

    /**
     * Creates a space containing the specified T type
     * @param items arraylist T items
     */
    protected Space(List<T> items) {
        this.items = items;
    }

    /**
     * Returns a space's items
     * @return a space's items
     */
    public List<T> getItems() {
        return items;
    }

    /**
     * Returns TRUE when a space contains no items
     * @return TRUE when a space contains no items
     */
    public boolean isEmpty(){
        return items.isEmpty();
    }

    /**
     * Returns TRUE when a space and a given object are equals
     * @param o the controlled object
     * @return TRUE when a space and a given object are equals
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Space<?> space = (Space<?>) o;
        return Objects.equals(items, space.items);
    }

    /**
     * Returns a Space's HashCode
     * @return a Space's HashCode
     */
    @Override
    public int hashCode() {
        return Objects.hash(items);
    }
}
