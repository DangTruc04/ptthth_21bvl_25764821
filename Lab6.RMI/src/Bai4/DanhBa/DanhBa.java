package Bai4.DanhBa;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DanhBa extends Remote {
	void addContact(String name, String phone) throws RemoteException;
    String findContact(String name) throws RemoteException;
    boolean deleteContact(String name) throws RemoteException;
}
