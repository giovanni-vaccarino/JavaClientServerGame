package polimi.ingsoft.server.common;

import polimi.ingsoft.server.common.command.Command;
import polimi.ingsoft.server.common.command.MatchServerCommand;
import polimi.ingsoft.server.common.command.ServerCommand;
import polimi.ingsoft.client.common.VirtualView;
import polimi.ingsoft.server.enumerations.PlayerColor;
import polimi.ingsoft.server.enumerations.TYPE_HAND_CARD;
import polimi.ingsoft.server.model.Coordinates;
import polimi.ingsoft.server.model.MixedCard;
import polimi.ingsoft.server.model.PlaceInPublicBoard;
import polimi.ingsoft.server.model.QuestCard;

import java.io.IOException;

public class Dispatcher {

    private final VirtualServer server;
    private VirtualMatchServer matchServer;

    public Dispatcher(VirtualServer server) {
        this.server = server;
    }

    public void dispatchCommand(Command<?> command) throws IOException {
        if (command instanceof ServerCommand serverCommand) {
            serverCommand.execute(server);
        } else if (command instanceof MatchServerCommand matchServerCommand) {
            matchServerCommand.execute(matchServer);
        }
    }

    public void setMatchServer(VirtualMatchServer matchServer) {
        this.matchServer = matchServer;
    }
}
