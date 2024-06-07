package polimi.ingsoft.client.ui.gui.utils.selectedCardUtils;
import javafx.event.EventHandler;

import java.util.ArrayList;
import java.util.List;

public class CardClickedEventDispatcher {
    private static final List<EventHandler<CardClickedEvent>> listeners = new ArrayList<>();

    public static void registerListener(EventHandler<CardClickedEvent> listener) {
        listeners.add(listener);
    }

    public static void unregisterListener(EventHandler<CardClickedEvent> listener) {
        listeners.remove(listener);
    }

    public static void dispatchEvent(CardClickedEvent event) {
        for (EventHandler<CardClickedEvent> listener : listeners) {
            listener.handle(event);
        }
    }
}
