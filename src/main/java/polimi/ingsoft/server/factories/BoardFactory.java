package polimi.ingsoft.server.factories;

import polimi.ingsoft.server.controller.ChatController;
import polimi.ingsoft.server.enumerations.PlayerColor;
import polimi.ingsoft.server.model.Board;
import polimi.ingsoft.server.model.InitialCard;
import polimi.ingsoft.server.model.PlaceInPublicBoard;

public class BoardFactory {
    public static Board createBoard(InitialCard initialCard, boolean isInitialFaceUp, PlayerColor color) {
        return new Board(initialCard, isInitialFaceUp, color);
    }
}
