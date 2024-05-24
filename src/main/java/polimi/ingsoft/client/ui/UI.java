package polimi.ingsoft.client.ui;

import polimi.ingsoft.client.common.Client;
import polimi.ingsoft.server.controller.GameState;
import polimi.ingsoft.server.controller.MatchController;
import polimi.ingsoft.server.enumerations.ERROR_MESSAGES;
import polimi.ingsoft.server.enumerations.PlayerColor;

import java.io.IOException;
import java.util.List;

public abstract class UI {

    private Client client;

    private String nickname;
    public  UI (Client client){
        this.client = client;
    }

    public Client getClient(){
        return client;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public abstract void showWelcomeScreen() throws IOException;
    public abstract void updateNickname();
    public abstract void updateMatchesList(List<Integer> matches);
    public abstract void showUpdateMatchJoin();
    public abstract void updatePlayersInLobby(List<String> nicknames);
    public abstract void showMatchCreate(Integer matchId);
    public abstract void reportError(ERROR_MESSAGES errorMessage);
    public abstract void showUpdateGameState(GameState gameState);

    public void setColor(PlayerColor playerColor){
        try {
            this.client.setColor(nickname,playerColor);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
