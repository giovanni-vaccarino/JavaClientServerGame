package polimi.ingsoft.client.ui.cli.pages;

import polimi.ingsoft.server.enumerations.ERROR_MESSAGES;

import java.io.Serializable;

public interface CLIPhaseManager extends Serializable {
    void parseError(ERROR_MESSAGES error);
    void start();
}
