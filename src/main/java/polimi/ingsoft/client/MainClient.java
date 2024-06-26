package polimi.ingsoft.client;

import polimi.ingsoft.client.common.Client;
import polimi.ingsoft.client.rmi.RmiClient;
import polimi.ingsoft.client.ui.UIType;
import polimi.ingsoft.client.ui.cli.IPChoiceCLI;
import polimi.ingsoft.client.ui.cli.JarParams;
import polimi.ingsoft.client.ui.cli.Protocols;
import polimi.ingsoft.client.socket.SocketClient;
import polimi.ingsoft.server.exceptions.UnableToConnectException;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

/**
 * The MainClient class is the entry point for the client application.
 * It initializes the client, chooses the communication protocol, and sets the UI type.
 */
public class MainClient {
    private static final PrintStream printStream = System.out;
    private static final Scanner scanner = new Scanner(System.in);
    private static final int socketServerPort = 4444;
    private static final int rmiServerPort = 1234;
    private static final String rmiServerName = "MatchManagerServer";


    /**
     * The main method initializes the client application, chooses the communication protocol, and sets the UI type.
     *
     * @param args command line arguments to specify the UI type (CLI or GUI).
     */
    public static void main(String[] args) {
        JarParams params = handleParams(args);

        try {
            Client client = createClient(params.serverIp(), params.protocol(), params.uiType());
            client.run();
            client.getUi().showWelcomeScreen();

            while (true) { }
        } catch (UnableToConnectException e) {
            main(args);
        } catch (IOException e) {
            printStream.println("Error: " + e.getMessage());
        }
    }


    /**
     * Creates a client based on the chosen protocol and UI type.
     *
     * @param protocol the communication protocol (RMI or Socket).
     * @param uiType the type of user interface (CLI or GUI).
     * @return the created Client object.
     * @throws UnableToConnectException if an I/O error occurs during client creation.
     */
    private static Client createClient(String serverIp, Protocols protocol, UIType uiType) throws UnableToConnectException {
        return (protocol == Protocols.RMI) ? createRmiClient(serverIp, uiType) : createSocketClient(serverIp, uiType);
    }


    /**
     * Creates an RMI client with the specified UI type.
     *
     * @param uiType the type of user interface (CLI or GUI).
     * @return the created RmiClient object, or null if an error occurs.
     * @throws UnableToConnectException if an I/O error occurs during client creation.
     */
    private static Client createRmiClient(String serverIp, UIType uiType) throws UnableToConnectException {
        try {
            System.setProperty("java.rmi.server.hostname", InetAddress.getLocalHost().getHostAddress());
            return new RmiClient(serverIp, rmiServerName, rmiServerPort, uiType, printStream, scanner);
        } catch (RemoteException | NotBoundException exception) {
            throw new UnableToConnectException();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Creates a Socket client with the specified UI type.
     *
     * @param uiType the type of user interface (CLI or GUI).
     * @return the created SocketClient object.
     * @throws UnableToConnectException if an I/O error occurs during client creation.
     */
    private static Client createSocketClient(String serverIp, UIType uiType) throws UnableToConnectException {
        try {
            return new SocketClient(serverIp, socketServerPort, uiType, printStream, scanner);
        } catch (IOException e) {
            throw new UnableToConnectException();
        }
    }


    private static JarParams handleParams(String []args){
        UIType uiType = UIType.GUI;
        Protocols protocol = Protocols.RMI;
        String serverIp = "";

        for (int i = 0; i < args.length; i++) {
            String arg = args[i].toLowerCase();

            if (IPChoiceCLI.isValidIP(arg)) {
                serverIp = arg;
            }

            // Check for UI type argument
            else if (arg.equals("-cli")) {
                uiType = UIType.CLI;
            }

            // Check for protocol argument
            else if (arg.equals("-socket")) {
                protocol = Protocols.SOCKET;
            }
        }

        if (serverIp.isEmpty()) {
            IPChoiceCLI ipChoiceCLI = new IPChoiceCLI(scanner, printStream);
            serverIp = ipChoiceCLI.runChooseIpRoutine();
        }

        return new JarParams(serverIp, uiType, protocol);
    }
}
