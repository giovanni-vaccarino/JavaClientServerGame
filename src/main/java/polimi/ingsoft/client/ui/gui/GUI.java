package polimi.ingsoft.client.ui.gui;

import polimi.ingsoft.client.common.Client;
import polimi.ingsoft.client.ui.UI;
import polimi.ingsoft.client.ui.cli.MESSAGES;
import polimi.ingsoft.client.ui.gui.page.HomeController;
import polimi.ingsoft.server.enumerations.CLIENT_STATE;
import polimi.ingsoft.server.enumerations.ERROR_MESSAGES;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class GUI extends UI{

    private HomeController homeController;
    private Client client;
    private String nickname;
    private Integer matchId;
    private List<Integer> matchList;
    private CLIENT_STATE clientState;

    public GUI(Client client){
        this.client=client;
        GUIsingleton.getInstance().setGui(this);
    }

    @Override
    public void showWelcomeScreen() throws IOException {
        getClientMatches();
        homeController = new HomeController();
        HomeController.main(new String[]{});
    }

    public void setNickname(String nickname){
        try {
            this.nickname=nickname;
            client.setNickname(nickname);
        } catch (IOException ignored) {
        }
    }

    @Override
    public void updateNickname() {
        GUIsingleton.getInstance().getNicknamePageController().nextPage();
    }

    @Override
    public void reportError(ERROR_MESSAGES errorMessage) {
        switch (errorMessage){
            case NICKNAME_NOT_AVAILABLE -> {
                GUIsingleton.getInstance().getNicknamePageController().showError(errorMessage);
            }
            case MATCH_IS_ALREADY_FULL -> {
                GUIsingleton.getInstance().getJoinGamePageController().showError(errorMessage);
            }
        }
    }

    public void createMatch(int numPlayers){
        try {
            clientState = CLIENT_STATE.NEWGAME;
            client.createMatch(nickname,numPlayers);
        } catch (IOException ignore) {
        }
    }

    public void joinMatch(Integer matchId) {
        try {
            clientState = CLIENT_STATE.JOINGAME;
            client.joinMatch(nickname, matchId);
        } catch (IOException ignore) {
        }
    }

    public void getClientMatches(){
        try {
            client.getMatches(this.client);
        } catch (IOException ignore) {
        }
    }

    public List<Integer> getMatchList(){
        return matchList;
    }

    @Override
    public void showUpdateMatchJoin() {
        switch (clientState){
            case NEWGAME:
                GUIsingleton.getInstance().getNewGamePageController().nextPage();
                break;
            case JOINGAME:
                GUIsingleton.getInstance().getJoinGamePageController().nextPage();
                break;
        }
    }

    @Override
    public void updateMatchesList(List<Integer> matches) {
        matchList = matches;
    }

    @Override
    public void updatePlayersInLobby(List<String> nicknames) {

    }

    @Override
    public void showMatchCreate(Integer matchId) {
        this.matchId = matchId;
        try {
            client.joinMatch(nickname,matchId);
        } catch (IOException ignore) {
        }
    }



}
