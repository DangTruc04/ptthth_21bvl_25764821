package Bai3.SoNguyenTo;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface KiemTra extends Remote {
	boolean isPrime(int n) throws RemoteException;
}
