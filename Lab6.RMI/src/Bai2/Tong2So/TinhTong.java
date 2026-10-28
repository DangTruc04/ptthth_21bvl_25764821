package Bai2.Tong2So;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TinhTong extends Remote {
	int add(int a,int b) throws RemoteException;
}
