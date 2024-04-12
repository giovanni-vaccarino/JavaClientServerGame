package polimi.ingsoft.rmi;


import java.rmi.Remote;
import java.rmi.RemoteException;

//Interfaccia che utliazziamo dal server per interagire coi client
public interface VirtualView extends Remote{
    void showUpdate(Integer number) throws RemoteException;

    void reportError(String details) throws RemoteException;

}
