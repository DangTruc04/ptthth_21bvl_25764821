package Bai1.Hello;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class client {
	public static void main(String[] args) {
		try {
			Registry registry = LocateRegistry.getRegistry("localhost",1099);
			Hello comp = (Hello) registry.lookup("HelloService");
			String response = comp.sayHello();
			System.out.println("Phản hồi từ Server: " + response);
		}catch(Exception e){
			System.err.println("Lỗi Client: " + e.getMessage());
            e.printStackTrace();
		}
	}
}
