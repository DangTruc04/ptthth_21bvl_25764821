package Bai5.Chat;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatService extends Remote {
    // Phương thức gửi tin nhắn từ client lên server và nhận về chuỗi phản hồi
    String sendMessage(String message) throws RemoteException;
}