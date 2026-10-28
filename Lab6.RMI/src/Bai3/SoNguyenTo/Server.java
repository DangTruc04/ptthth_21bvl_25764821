package Bai3.SoNguyenTo;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server extends UnicastRemoteObject implements KiemTra {
	
	public Server() throws RemoteException{
		super();
	}

	@Override
	public boolean isPrime(int n) throws RemoteException {
		System.out.println("Nhan kiem tra so nguyen to: "+n);
		if(n<=1) {
			return false;
		}
		for(int i=2;i<=Math.sqrt(n);i++) {
			if (n%i==0) {
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		try {
			Registry registry = LocateRegistry.createRegistry(1099);
			Server server = new Server();
			registry.rebind("KiemTraService", server);
			System.out.println("Kiem Tra Server dang chay");
		}catch(Exception e) {
			System.err.println("Lỗi Server: " + e.getMessage());
            e.printStackTrace();
		}

	}


	

}
