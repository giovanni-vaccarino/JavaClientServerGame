package polimi.ingsoft.server.factories;

import polimi.ingsoft.server.controller.ChatController;
import polimi.ingsoft.server.model.Board;
import polimi.ingsoft.server.model.InitialCard;

public class BoardFactory {
    public static Board createBoard(InitialCard initialCard, boolean isInitialFaceUp) {
        return new Board(initialCard, isInitialFaceUp);
    }
}
