package polimi.ingsoft.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;


//Interfaccia che utliazziamo dal client per interagire col server
public interface VirtualServer extends Remote{
    // Metodo per segnalare al server chi siamo, per contattarci
    // gli passo lo stub del client
    void connect(VirtualView client) throws RemoteException;

    void add(Integer number) throws RemoteException;

    void reset() throws RemoteException;
}
