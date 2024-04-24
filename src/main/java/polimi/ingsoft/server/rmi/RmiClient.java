package polimi.ingsoft.server.rmi;

import polimi.ingsoft.server.model.Message;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Scanner;

//Rmi client extends UnicastRemoteObject in modo che possa essere passato al server
public class RmiClient extends UnicastRemoteObject implements VirtualView {
    final VirtualServer server;

    public RmiClient(VirtualServer server) throws RemoteException{
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
        final String serverName = "AdderServer";
        Registry registry = LocateRegistry.getRegistry(args[0], 1234);

        VirtualServer server = (VirtualServer) registry.lookup(serverName);

        new RmiClient(server).run();
    }

    @Override
    public void showUpdateChat(List<Message> messages) throws RemoteException {
        //Attenzione alle data races

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
