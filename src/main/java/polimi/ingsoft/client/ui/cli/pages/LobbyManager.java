package polimi.ingsoft.client.ui.cli.pages;

import polimi.ingsoft.client.ui.cli.CLI;
import polimi.ingsoft.client.ui.cli.MESSAGES;
import polimi.ingsoft.server.enumerations.ERROR_MESSAGES;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LobbyManager implements CLIPhaseManager {
    private final PrintStream out;
    private transient final CLI cli;

    public LobbyManager(PrintStream out, CLI cli) {
        this.out = out;
        this.cli = cli;
    }

    public void start() {
        // Waiting players update will be incoming shortly after
    }

    public void showWaitingPlayers(List<String> nicknames) {
        out.println(MESSAGES.PLAYERS_IN_LOBBY.getValue());
        for (var nickname : nicknames)
            out.println(nickname);
    }

    public void endLobby() {
        cli.returnLobbyManager();
    }

    @Override
    public void parseError(ERROR_MESSAGES error) {
        // TODO Maybe remove this one
        out.println("UNEXPECTED ERROR: " + error.getValue());
    }
}
