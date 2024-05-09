package polimi.ingsoft.server.factories;

import polimi.ingsoft.server.Player;
import polimi.ingsoft.server.controller.ChatController;
import polimi.ingsoft.server.controller.MatchController;
import polimi.ingsoft.server.controller.PlayerInitialSetting;
import polimi.ingsoft.server.enumerations.PlayerColors;
import polimi.ingsoft.server.model.*;

import java.io.PrintStream;

public class PlayerFactory {
    public static Player createPlayer(PlayerInitialSetting playerInitialSetting) {
        PlayerHand<MixedCard> playerHand = playerInitialSetting.getPlayerHand();
        InitialCard initialCard = playerInitialSetting.getInitialCard();
        String nickname = playerInitialSetting.getNickname();
        PlayerColors color = playerInitialSetting.getColor();
        boolean isInitialFaceUp = playerInitialSetting.getIsInitialFaceUp();
        QuestCard questCard = playerInitialSetting.getQuestCard();

        Board board = BoardFactory.createBoard(initialCard, isInitialFaceUp);

        return new Player(playerHand, nickname, color, board, questCard);
    }
}
