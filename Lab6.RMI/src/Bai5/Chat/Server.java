package Bai5.Chat;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Server extends UnicastRemoteObject implements ChatService {

    public Server() throws RemoteException {
        super();
    }

    @Override
    public String sendMessage(String message) throws RemoteException {
        System.out.println("[Client gửi]: " + message);
        
        System.out.print("[Nhập phản hồi cho Client]: ");
        Scanner scanner = new Scanner(System.in);
        String reply = scanner.nextLine();
        
        return reply;
    }

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            
            Server server = new Server();
            registry.rebind("ChatService", server);
            
            System.out.println("Server Chat đang chạy và sẵn sàng kết nối...");
        } catch (Exception e) {
            System.err.println("Lỗi Server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}