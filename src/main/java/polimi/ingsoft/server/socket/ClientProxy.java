package polimi.ingsoft.server.socket;

import polimi.ingsoft.server.controller.PlayerInitialSetting;
import polimi.ingsoft.server.enumerations.ERROR_MESSAGES;
import polimi.ingsoft.client.common.VirtualView;
import polimi.ingsoft.server.common.VirtualMatchServer;
import polimi.ingsoft.server.controller.GameState;
import polimi.ingsoft.server.enumerations.TYPE_HAND_CARD;
import polimi.ingsoft.server.model.*;
import polimi.ingsoft.server.rmi.RmiMethodCall;
import polimi.ingsoft.server.common.protocol.MessageCodes;
import polimi.ingsoft.server.common.protocol.NetworkMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ClientProxy implements VirtualView {
    private final ObjectOutputStream out;

    public ClientProxy(ObjectOutputStream out) {
        this.out = out;
    }

    @Override
    public void handleRmiClientMessages(RmiMethodCall rmiMethodCall) {

    }

    @Override
    public void showNicknameUpdate() throws IOException {
        NetworkMessage message = new NetworkMessage(MessageCodes.SET_NICKNAME_UPDATE, null);
        out.writeObject(message);
        out.flush();
    }

    @Override
    public void showUpdateLobbyPlayers(List<String> players) throws IOException {
        NetworkMessage message = new NetworkMessage(MessageCodes.LOBBY_PLAYERS_UPDATE, (Serializable) players);
        out.writeObject(message);
        out.flush();
    }

    @Override
    public void showUpdateMatchesList(List<Integer> matches) throws IOException {
        NetworkMessage message = new NetworkMessage(MessageCodes.MATCHES_LIST_UPDATE, (Serializable) matches);
        out.writeObject(message);
        out.flush();
    }

    @Override
    public void showUpdateMatchJoin() throws IOException {
        NetworkMessage message = new NetworkMessage(MessageCodes.MATCH_JOIN_UPDATE, null);
        out.writeObject(message);
        out.flush();
    }

    @Override
    public void showUpdateMatchCreate(Integer matchId) throws IOException {
        NetworkMessage message = new NetworkMessage(MessageCodes.MATCH_CREATE_UPDATE, matchId);
        out.writeObject(message);
        out.flush();
    }

    @Override
    public void showUpdateBroadcastChat(Message _message) throws IOException {
        NetworkMessage message = new NetworkMessage(
                MessageCodes.MATCH_BROADCAST_MESSAGE_UPDATE,
                new NetworkMessage.BroadcastMessageUpdatePayload(_message)
        );
        out.writeObject(message);
        out.flush();
    }

    @Override
    public void showUpdatePrivateChat(String recipient, Message _message) throws IOException {
        NetworkMessage message = new NetworkMessage(
                MessageCodes.MATCH_PRIVATE_MESSAGE_UPDATE,
                new NetworkMessage.PrivateMessageUpdatePayload(recipient, _message)
        );
        out.writeObject(message);
        out.flush();
    }

    @Override
    public void showUpdateInitialSettings(PlayerInitialSetting playerInitialSetting) throws IOException {
        PlayerInitialSetting playerInitialSettingCopy = playerInitialSetting.clone();
        NetworkMessage message = new NetworkMessage(
                MessageCodes.SET_INITIAL_SETTINGS_UPDATE,
                new NetworkMessage.InitialSettings(playerInitialSettingCopy)
        );
        out.writeObject(message);
        out.flush();
    }

    @Override
    public void showUpdateGameState(GameState gameState) throws IOException {
        GameState gameStateCopy = gameState.clone();
        NetworkMessage message = new NetworkMessage(
            MessageCodes.MATCH_GAME_STATE_UPDATE,
            new NetworkMessage.GameStatePayload(gameStateCopy)
        );
        out.writeObject(message);
        out.flush();
    }

    @Override
    public void showUpdateGameStart(PlaceInPublicBoard<ResourceCard> resource, PlaceInPublicBoard<GoldCard> gold, PlaceInPublicBoard<QuestCard> quest, Map<String, Board> boards) throws IOException {
        NetworkMessage message = new NetworkMessage(
                MessageCodes.GAME_START_UPDATE,
                new NetworkMessage.GameStartUpdate(resource, gold, quest, boards)
        );
        out.writeObject(message);
        out.flush();
    }

    @Override
    public void showUpdatePlayerHand(PlayerHand playerHand) throws IOException {
        NetworkMessage message = new NetworkMessage(
                MessageCodes.MATCH_PLAYER_HAND_UPDATE,
                playerHand
        );
        out.writeObject(message);
        out.flush();
    }

    @Override
    public void showUpdatePublicBoard(TYPE_HAND_CARD deckType, PlaceInPublicBoard<?> placeInPublicBoard) throws IOException{

    }

    @Override
    public void showUpdateBoard(String nickname, Coordinates coordinates, PlayedCard playedCard) throws IOException {
        NetworkMessage message = new NetworkMessage(
                MessageCodes.MATCH_BOARD_UPDATE,
                new NetworkMessage.BoardUpdatePayload(nickname, coordinates, playedCard)
        );
        out.writeObject(message);
        out.flush();
    }

    @Override
    public void reportError(ERROR_MESSAGES errorMessage) throws IOException {
        NetworkMessage message = new NetworkMessage(
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
