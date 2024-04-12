package polimi.ingsoft.rmi;

import polimi.ingsoft.controller.AdderController;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class RmiServer implements VirtualServer{
    final AdderController controller;
    final List<VirtualView> clients = new ArrayList<>();

    public RmiServer(AdderController controller) {
        this.controller = controller;
    }

    //Al posto di mettere throws eccezioni, gestisci bene in un try catch il tutto
    public static void main(String[] args) throws RemoteException{
        final String serverName = "AdderServer";

        VirtualServer server = new RmiServer(new AdderController());
        VirtualServer stub = (VirtualServer) UnicastRemoteObject.exportObject(server,0);
        Registry registry = LocateRegistry.createRegistry(1234);
        registry.rebind(serverName, stub);
        System.out.println("server bound.");
     }

    @Override
    public void connect(VirtualView client) throws RemoteException {
        synchronized (this.clients){
            this.clients.add(client);
        }
    }

    @Override
    public void add(Integer number) throws RemoteException {
        System.err.println("add request received");
        this.controller.add(number);
        Integer currentState = this.controller.getState();

        //Cambia in modo meno pesante sulla rete
        synchronized (this.clients){
            for(var c : this.clients){
               c.showUpdate(currentState);
            }
        }
    }

    @Override
    public void reset() throws RemoteException {
        System.err.println("reset request received");
        boolean result = this.controller.reset();

        synchronized (this.clients){
            if(result){
                Integer currentState = this.controller.getState();
                for(var c : this.clients){
                    c.showUpdate(currentState);
                }
            }else{
                for(var c : this.clients){
                    c.reportError("already reset");
                }
            }
        }
    }
}
