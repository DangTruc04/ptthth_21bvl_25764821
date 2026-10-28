package Bai1.Hello;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class server extends UnicastRemoteObject implements Hello {
	
	public server() throws RemoteException{
		super();
	}
	@Override
	public String sayHello() throws RemoteException {
		return "Hello, World";
	}
	public static void main(String[] args) {
		try {
			Registry registry = LocateRegistry.createRegistry(1099);
			server server = new server();
			registry.rebind("HelloService", server);
			System.out.print("RMI dang chay");
		}catch (Exception e) {
            System.err.println("Lỗi Server: " + e.getMessage());
            e.printStackTrace();
        }
	}
	
}
