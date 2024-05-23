package polimi.ingsoft.client.ui.gui;

import polimi.ingsoft.client.common.Client;
import polimi.ingsoft.client.ui.UI;
import polimi.ingsoft.client.ui.gui.page.HomeController;
import polimi.ingsoft.server.enumerations.ERROR_MESSAGES;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class GUI extends UI{

    private HomeController homeController;
    private Client client;
    private String nickname;
    private Integer matchId;
    private List<String> matchList;

    public GUI(Client client){
        this.client=client;
        GUIsingleton.getInstance().setGui(this);
    }

    @Override
    public void showWelcomeScreen() throws IOException {
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
        GUIsingleton.getInstance().getNicknamePageController().showError(errorMessage);
    }

    public void createMatch(int numPlayers){
        try {
            client.createMatch(nickname,numPlayers);
        } catch (IOException ignore) {
        }
    }

    public void getClientMatches(){
        try {
            client.getMatches(this.client);
        } catch (IOException ignore) {
        }
    }

    public List<String> getMatchList(){
        return matchList;
    }

    @Override
    public void showUpdateMatchJoin() {
        GUIsingleton.getInstance().getNewGamePageController().nextPage();
    }

    @Override
    public void updateMatchesList(List<Integer> matches) {
        matchList = matches.stream()
                .map(String::valueOf)
                .collect(Collectors.toList());
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
