package polimi.ingsoft.client.ui.cli.pages;

import polimi.ingsoft.server.enumerations.ERROR_MESSAGES;

public interface CLIPhaseManager {
    void parseError(ERROR_MESSAGES error);
    void start();
}
