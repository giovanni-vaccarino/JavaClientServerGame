package polimi.ingsoft.client.ui;

import polimi.ingsoft.server.controller.MatchController;

import java.io.IOException;
import java.util.List;

public abstract class UI {
    public abstract void showWelcomeScreen() throws IOException;
    public abstract void updateNickname(boolean result) throws IOException;
    public abstract void updateMatchesList(List<Integer> matches) throws IOException;
    public abstract void showMatchCreate(MatchController match);
}
