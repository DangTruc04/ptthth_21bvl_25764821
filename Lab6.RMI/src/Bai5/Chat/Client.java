package Bai5.Chat;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            
            ChatService chatService = (ChatService) registry.lookup("ChatService");
            
            Scanner scanner = new Scanner(System.in);
            System.out.println("=== ĐÃ KẾT NỐI VỚI SERVER CHAT ===");
            System.out.println("Nhập 'thoát' hoặc 'exit' để dừng chat.");
            
            while (true) {
                System.out.print("\n[Bạn]: ");
                String message = scanner.nextLine();
                
                if (message.equalsIgnoreCase("thoát") || message.equalsIgnoreCase("exit")) {
                    System.out.println("Đã ngắt kết nối chat.");
                    break;
                }
                
                String response = chatService.sendMessage(message);
                System.out.println("[Server phản hồi]: " + response);
            }
            
            scanner.close();
        } catch (Exception e) {
            System.err.println("Lỗi Client: " + e.getMessage());
            e.printStackTrace();
        }
    }
}