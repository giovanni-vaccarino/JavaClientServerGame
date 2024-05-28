package polimi.ingsoft.client.ui.cli.pages;

import polimi.ingsoft.client.ui.cli.CLI;
import polimi.ingsoft.client.ui.cli.MESSAGES;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LobbyManager {
    private final PrintStream out;

    private List<String> nicknames = new ArrayList<>();

    public LobbyManager(PrintStream out) {
        this.out = out;
    }

    public void showWaitingPlayers() {
        out.println(MESSAGES.PLAYERS_IN_LOBBY.getValue());
        for (var nickname : nicknames)
            out.println(nickname);
    }

    public void setNicknames(List<String> nicknames) {
        this.nicknames = nicknames;
    }
}
