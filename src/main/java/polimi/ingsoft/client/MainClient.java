package polimi.ingsoft.client;

import polimi.ingsoft.client.common.Client;
import polimi.ingsoft.client.rmi.RmiClient;
import polimi.ingsoft.client.ui.UIType;
import polimi.ingsoft.client.ui.cli.ProtocolChoiceCLI;
import polimi.ingsoft.client.ui.cli.Protocols;
import polimi.ingsoft.client.socket.SocketClient;

import java.io.IOException;
import java.io.PrintStream;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class MainClient {
    private static final PrintStream printStream = System.out;
    private static final Scanner scanner = new Scanner(System.in);
    private static final String socketServerHostName = "192.168.129.134";
    private static final int socketServerPort = 4444;
    private static final String rmiServerHostName = "192.168.129.134";
    private static final int rmiServerPort = 1234;
    private static final String rmiServerName = "MatchManagerServer";

    public static void main(String[] args) {
        ProtocolChoiceCLI protocolChoiceCLI = new ProtocolChoiceCLI(scanner, printStream);
        Protocols protocol = protocolChoiceCLI.runChooseProtocolRoutine();

        try {
            Client client = createClient(protocol);
            client.run();
            client.getUi().showWelcomeScreen();

            while (true) { }
        } catch (IOException e) {
            printStream.println("Error: " + e.getMessage());
        }//TODO nullpointer exception se scegli RMI da una rete in cui non c'è nessun server
    }
    
    private static Client createClient(Protocols protocol) throws IOException {
        if (protocol == Protocols.RMI)
            return createRmiClient();
        else
            return createSocketClient();
    }

    private static Client createRmiClient() {
        try {
            return new RmiClient(rmiServerHostName, rmiServerName, rmiServerPort, UIType.GUI, printStream, scanner);
        } catch (RemoteException | NotBoundException exception) {
            System.out.println(exception);
            return null;
        }
    }

    private static Client createSocketClient() throws IOException {
        return new SocketClient(socketServerHostName, socketServerPort, UIType.GUI, printStream, scanner);
    }
}
