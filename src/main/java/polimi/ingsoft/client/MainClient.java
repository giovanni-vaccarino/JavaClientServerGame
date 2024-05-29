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
    private static final String socketServerHostName = "127.0.0.1";
    private static final int socketServerPort = 4444;
    private static final String rmiServerHostName = "127.0.0.1";
    private static final int rmiServerPort = 1234;
    private static final String rmiServerName = "MatchManagerServer";

    public static void main(String[] args) {
        ProtocolChoiceCLI protocolChoiceCLI = new ProtocolChoiceCLI(scanner, printStream);
        Protocols protocol = protocolChoiceCLI.runChooseProtocolRoutine();

        //If no parameters GUI
        UIType uiType = UIType.GUI;

        if(args.length == 1){
            if(args[0].toLowerCase().equals("cli") || args[0].toLowerCase().equals("tui")){
                uiType = UIType.CLI;
            }else if(args[0].toLowerCase().equals("gui")){
                uiType = UIType.GUI;
            }
        }

        try {
            Client client = createClient(protocol, uiType);
            client.run();
            client.getUi().showWelcomeScreen();

            while (true) { }
        } catch (IOException e) {
            printStream.println("Error: " + e.getMessage());
        }//TODO nullpointer exception se scegli RMI da una rete in cui non c'è nessun server
    }
    
    private static Client createClient(Protocols protocol, UIType uiType) throws IOException {
        if (protocol == Protocols.RMI)
            return createRmiClient(uiType);
        else
            return createSocketClient(uiType);
    }

    private static Client createRmiClient(UIType uiType) {
        try {
            return new RmiClient(rmiServerHostName, rmiServerName, rmiServerPort, uiType, printStream, scanner);
        } catch (RemoteException | NotBoundException exception) {
            System.out.println(exception);
            return null;
        }
    }

    private static Client createSocketClient(UIType uiType) throws IOException {
        return new SocketClient(socketServerHostName, socketServerPort, uiType, printStream, scanner);
    }
}
