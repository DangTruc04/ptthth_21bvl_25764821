package Bai2.Tong2So;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server extends UnicastRemoteObject implements TinhTong {

	public Server() throws RemoteException{
		super();
	}
	@Override
	public int add(int a, int b) throws RemoteException {
		System.out.println("Đang xử lý yêu cầu tính tổng: " + a + " + " + b);
		return a+b;
	}

	public static void main(String[] args) {
		try {
			Registry registry = LocateRegistry.createRegistry(1099);
			Server server = new Server();
			registry.rebind("TinhTongService", server);
			System.out.println("Dang chay Server");
		}catch(Exception e) {
			System.err.println("Lỗi Server: " + e.getMessage());
            e.printStackTrace();
		}

	}

	
}
