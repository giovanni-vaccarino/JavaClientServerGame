package polimi.ingsoft.server.factories;

import polimi.ingsoft.server.model.player.PlayerColor;
import polimi.ingsoft.server.model.boards.Board;
import polimi.ingsoft.server.model.cards.InitialCard;

public class BoardFactory {
    public static Board createBoard(InitialCard initialCard, boolean isInitialFaceUp, PlayerColor color, Boolean isFirstPlayer) {
        return new Board(initialCard, isInitialFaceUp, isFirstPlayer, color);
    }
}
