package polimi.ingsoft.client;

import polimi.ingsoft.client.cli.CLI;
import polimi.ingsoft.client.cli.ProtocolChoiceCLI;
import polimi.ingsoft.client.cli.Protocols;
import polimi.ingsoft.client.socket.SocketClient;
import polimi.ingsoft.server.Player;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class MainClient {
    private static final PrintStream printStream = System.out;
    private static final Scanner scanner = new Scanner(System.in);
    private static final String socketServerHostName = "127.0.0.1";
    private static final int socketServerPort = 4444;

    public static void main(String[] args) throws Exception {
        ProtocolChoiceCLI protocolChoiceCLI = new ProtocolChoiceCLI(scanner, printStream);
        Protocols protocol = protocolChoiceCLI.runChooseProtocolRoutine();

        try (
            Client client = createClient(protocol)
        ) {
            VirtualServer virtualServer = new VirtualServer();
            CLI cli = new CLI(scanner, printStream, virtualServer);

            Player player = cli.runJoinMatchRoutine();
        } catch (IOException ignored) { }
    }
    
    private static Client createClient(Protocols protocol) throws IOException {
        if (protocol == Protocols.RMI)
            return createRmiClient();
        else
            return createSocketClient();
    }

    private static Client createRmiClient() {
        return null;
    }

    private static Client createSocketClient() throws IOException {
        return new SocketClient(socketServerHostName, socketServerPort);
    }
}
