package polimi.ingsoft.client.rmi;

import polimi.ingsoft.server.model.Message;
import polimi.ingsoft.server.rmi.VirtualMatchServer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

//Rmi client extends UnicastRemoteObject in modo che possa essere passato al server
public class RmiClient extends UnicastRemoteObject implements VirtualView {
    final VirtualMatchServer server;

    public RmiClient(VirtualMatchServer server) throws RemoteException{
        this.server = server;
    }

    private void run() throws RemoteException{
        this.server.connect(this);
        this.runCli();
    }

    private void runCli() throws RemoteException{
        Scanner scan = new Scanner(System.in);
        while (true){
            System.out.print("> ");
            int command = scan.nextInt();

            if (command == 0){
                server.addMessage("ciao");
            }else{
                server.addMessage("ciao" + command);
            }
        }
    }

    //Anche qui gestire le eccezioni con un senso, non throwandole
    public static void main(String[] args) throws RemoteException, NotBoundException {
        final String serverName = "Server";
        Registry registry = LocateRegistry.getRegistry(args[0], 1234);

        VirtualMatchServer server = (VirtualMatchServer) registry.lookup(serverName);

        new RmiClient(server).run();
    }

    @Override
    public void showUpdateChat(Message message) throws RemoteException {
        //Attenzione alle data races
        //Aggiungo l'ultimo messaggio alla lista
    }

    @Override
    public void showUpdatePublicBoard() throws RemoteException {

    }

    @Override
    public void showUpdateBoard() throws RemoteException {

    }

    @Override
    public void reportError(String details) throws RemoteException {
        System.err.print("\n[ERROR]" + details + "\n> ");
    }
}
