package polimi.ingsoft.client.ui.gui;

import javafx.css.Match;
import javafx.stage.Stage;
import polimi.ingsoft.client.common.Client;
import polimi.ingsoft.client.ui.UI;
import polimi.ingsoft.client.ui.gui.page.ConnectionPageController;
import polimi.ingsoft.client.ui.gui.page.HomeController;
import polimi.ingsoft.client.ui.gui.page.NicknamePageController;
import polimi.ingsoft.server.controller.MatchController;
import polimi.ingsoft.server.enumerations.Connection;
import polimi.ingsoft.server.socket.ConnectionHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GUI extends UI{

    private HomeController homeController;
    private Client client;

    public GUI(Client client){
        this.client=client;
    }

    @Override
    public void showWelcomeScreen() throws IOException {
        homeController = new HomeController(this);
        homeController.main(new String[]{});
    }

    public void setNickname(String nickname){
        try {
            client.setNickname(nickname);
        } catch (IOException ignored) {
        }
    }

    @Override
    public void updateNickname() {
        GUIpages.getInstance().getNicknamePageController().nextPage();
    }

    @Override
    public void updateMatchesList(List<Integer> matches) {

    }

    @Override
    public void showUpdateMatchJoin() {

    }

    @Override
    public void updatePlayersInLobby(List<String> nicknames) {

    }

    @Override
    public void showMatchCreate(Integer matchId) {

    }

}
