package polimi.ingsoft.client.common;

import polimi.ingsoft.server.common.VirtualMatchServer;
import polimi.ingsoft.server.common.command.MatchServerCommand;
import polimi.ingsoft.server.model.player.PlayerColor;
import polimi.ingsoft.server.enumerations.TYPE_HAND_CARD;
import polimi.ingsoft.server.model.boards.Coordinates;
import polimi.ingsoft.server.model.cards.MixedCard;
import polimi.ingsoft.server.model.publicboard.PlaceInPublicBoard;
import polimi.ingsoft.server.model.cards.QuestCard;

import java.io.IOException;

public abstract class MatchServerProxy implements VirtualMatchServer {
    @Override
    public abstract void sendMessage(MatchServerCommand command) throws IOException;

    @Override
    public void setColor(String nickname, PlayerColor color) throws IOException {
        sendMessage((MatchServerCommand) server -> server.setColor(nickname, color));
    }

    @Override
    public void setIsInitialCardFacingUp(String nickname, Boolean isInitialCardFacingUp) throws IOException {
        sendMessage((MatchServerCommand) server -> server.setIsInitialCardFacingUp(nickname, isInitialCardFacingUp));
    }

    @Override
    public void setQuestCard(String nickname, QuestCard questCard) throws IOException {
        sendMessage((MatchServerCommand) server -> server.setQuestCard(nickname, questCard));
    }

    @Override
    public void sendBroadcastMessage(String player, String _message) throws IOException {
        sendMessage((MatchServerCommand) server -> server.sendBroadcastMessage(player, _message));
    }

    @Override
    public void sendPrivateMessage(String player, String recipient, String _message) throws IOException {
        sendMessage((MatchServerCommand) server -> server.sendPrivateMessage(player, recipient, _message));
    }

    @Override
    public void drawCard(String player, TYPE_HAND_CARD deckType, PlaceInPublicBoard.Slots slot) throws IOException {
        sendMessage((MatchServerCommand) server -> server.drawCard(player, deckType, slot));
    }

    @Override
    public void placeCard(String player, MixedCard card, Coordinates coordinates, boolean isFacingUp) throws IOException {
        sendMessage((MatchServerCommand) server -> server.placeCard(player, card, coordinates, isFacingUp));
    }

    @Override
    public void ping(String nickname) throws IOException {
        sendMessage((MatchServerCommand) server -> server.ping(nickname));
    }
}
