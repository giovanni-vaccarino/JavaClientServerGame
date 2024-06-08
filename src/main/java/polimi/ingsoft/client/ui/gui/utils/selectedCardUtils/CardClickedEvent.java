package polimi.ingsoft.client.ui.gui.utils.selectedCardUtils;

import javafx.event.Event;
import javafx.event.EventType;

public class CardClickedEvent extends Event {
    public static final EventType<CardClickedEvent> CARD_CLICKED = new EventType<>(Event.ANY, "CARD_CLICKED");

    public CardClickedEvent() {
        super(CARD_CLICKED);
    }
}
