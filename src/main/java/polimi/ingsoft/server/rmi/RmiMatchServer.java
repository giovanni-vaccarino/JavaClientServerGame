package polimi.ingsoft.server.rmi;

import polimi.ingsoft.server.common.MatchServer;
import polimi.ingsoft.server.common.Server;
import polimi.ingsoft.server.common.command.MatchServerCommand;
import polimi.ingsoft.server.controller.MatchController;

import java.io.IOException;
import java.io.PrintStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class RmiMatchServer extends MatchServer {

    private final BlockingQueue<MatchServerCommand> methodQueue = new LinkedBlockingQueue<>();

    public RmiMatchServer(MatchController controller, PrintStream logger, Server server) {
        super(logger, controller, server);

        new Thread(() -> {
            while (true) {
                try {
                    MatchServerCommand command = methodQueue.take();
                    command.execute(this);
                } catch (InterruptedException | IOException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }, "RmiMatchServerCommandReader-" + controller.getMatchId()).start();
    }

    @Override
    public void sendMessage(MatchServerCommand command) throws IOException {
        try {
            methodQueue.put(command);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
