package polimi.ingsoft.server.socket;

import polimi.ingsoft.server.enumerations.ERROR_MESSAGES;
import polimi.ingsoft.client.common.VirtualView;
import polimi.ingsoft.server.common.VirtualMatchServer;
import polimi.ingsoft.server.controller.GameState;
import polimi.ingsoft.server.controller.MatchController;
import polimi.ingsoft.server.enumerations.PlayerColor;
import polimi.ingsoft.server.model.*;
import polimi.ingsoft.server.socket.protocol.MessageCodes;
import polimi.ingsoft.server.socket.protocol.SocketMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

public class ClientProxy implements VirtualView {
    private final ObjectOutputStream out;

    public ClientProxy(ObjectOutputStream out) {
        this.out = out;
    }

    @Override
    public void showNicknameUpdate(boolean result) throws IOException {
        SocketMessage message = new SocketMessage(MessageCodes.SET_NICKNAME_UPDATE, result);
        out.writeObject(message);
        out.flush();
    }

    @Override
    public void showUpdateLobbyPlayers(List<String> players) throws IOException {
        SocketMessage message = new SocketMessage(MessageCodes.LOBBY_PLAYERS_UPDATE, (Serializable) players);
        out.writeObject(message);
        out.flush();
    }

    @Override
    public void showUpdateMatchesList(List<Integer> matches) throws IOException {
        SocketMessage message = new SocketMessage(MessageCodes.MATCHES_LIST_UPDATE, (Serializable) matches);
        out.writeObject(message);
        out.flush();
    }

    @Override
    public void showUpdateMatchJoin() throws IOException {
        SocketMessage message = new SocketMessage(MessageCodes.MATCH_JOIN_UPDATE, null);
        out.writeObject(message);
        out.flush();
    }

    @Override
    public void showUpdateMatchCreate(MatchController match) throws IOException {
        SocketMessage message = new SocketMessage(MessageCodes.MATCH_CREATE_UPDATE, match);
        out.writeObject(message);
        out.flush();
    }

    @Override
    public void showUpdateChat(Message message) throws IOException {

    }

    @Override
    public void showUpdateInitialSettings(PlayerColor color, Boolean isFacingUp, QuestCard questCard) throws IOException {
        SocketMessage message = new SocketMessage(
                MessageCodes.SET_INITIAL_SETTINGS_UPDATE,
                new SocketMessage.InitialSettings(color, isFacingUp, questCard)
        );
        out.writeObject(message);
        out.flush();
    }

    @Override
    public void showUpdateGameState(GameState gameState) throws IOException {
        SocketMessage message = new SocketMessage(
            MessageCodes.MATCH_GAME_STATE_UPDATE,
            gameState
        );
        out.writeObject(message);
        out.flush();
    }

    @Override
    public void showUpdatePlayerHand(PlayerHand<MixedCard> playerHand) throws IOException {
        SocketMessage message = new SocketMessage(
                MessageCodes.MATCH_PLAYER_HAND_UPDATE,
                playerHand
        );
        out.writeObject(message);
        out.flush();
    }

    @Override
    public void showUpdatePublicBoard(PublicBoard publicBoard) throws IOException {
        SocketMessage message = new SocketMessage(
                MessageCodes.MATCH_PUBLIC_BOARD_UPDATE,
                publicBoard
        );
        out.writeObject(message);
        out.flush();
    }

    @Override
    public void showUpdateBoard(String nickname, Board board) throws IOException {
        SocketMessage message = new SocketMessage(
                MessageCodes.MATCH_BOARD_UPDATE,
                new SocketMessage.NicknameAndBoard(nickname, board)
        );
        out.writeObject(message);
        out.flush();
    }

    @Override
    public void reportError(ERROR_MESSAGES errorMessage) throws IOException {
        SocketMessage message = new SocketMessage(
                MessageCodes.ERROR,
                errorMessage
        );
        out.writeObject(message);
        out.flush();
    }

    @Override
    public void setMatchControllerServer(VirtualMatchServer matchServer) throws IOException {

    }
}
