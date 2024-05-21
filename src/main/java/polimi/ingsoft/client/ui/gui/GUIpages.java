package polimi.ingsoft.client.ui.gui;

import polimi.ingsoft.client.ui.gui.page.ConnectionPageController;
import polimi.ingsoft.client.ui.gui.page.HomeController;
import polimi.ingsoft.client.ui.gui.page.NicknamePageController;

public class GUIpages {
    private static GUIpages instance;

    private ConnectionPageController connectionPageController;
    private NicknamePageController nicknamePageController;

    private GUIpages() {
        // Private constructor to prevent instantiation
    }

    public static GUIpages getInstance() {
        if (instance == null) {
            instance = new GUIpages();
        }
        return instance;
    }

    public ConnectionPageController getConnectionPageController() {
        return connectionPageController;
    }

    public void setConnectionPageController(ConnectionPageController connectionPageController) {
        this.connectionPageController = connectionPageController;
    }

    public NicknamePageController getNicknamePageController() {
        return nicknamePageController;
    }

    public void setNicknamePageController(NicknamePageController nicknamePageController) {
        this.nicknamePageController = nicknamePageController;
    }
}
