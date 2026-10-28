package Bai6.NganHang;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server extends UnicastRemoteObject implements BankService {
    private double balance;

    public Server() throws RemoteException {
        super();
        this.balance = 1000000.0;
    }

    @Override
    public synchronized double getBalance() throws RemoteException {
        System.out.println("[SERVER] Có yêu cầu kiểm tra số dư.");
        return this.balance;
    }

    @Override
    public synchronized void deposit(double amount) throws RemoteException {
        if (amount > 0) {
            this.balance += amount;
            System.out.println("[SERVER] Gửi tiền thành công: +" + amount + " | Số dư mới: " + this.balance);
        } else {
            System.out.println("[SERVER] Số tiền gửi không hợp lệ!");
        }
    }

    @Override
    public synchronized boolean withdraw(double amount) throws RemoteException {
        System.out.println("[SERVER] Yêu cầu rút số tiền: " + amount);
        if (amount > 0 && amount <= this.balance) {
            this.balance -= amount;
            System.out.println("[SERVER] Rút tiền thành công! Số dư còn lại: " + this.balance);
            return true;
        }
        System.out.println("[SERVER] Rút tiền thất bại (Số dư không đủ hoặc số tiền không hợp lệ).");
        return false;
    }

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            
            Server server = new Server();
            registry.rebind("BankService", server);
            
            System.out.println("Server Ngân Hàng đang chạy... (Số dư khởi tạo: 1,000,000 VND)");
        } catch (Exception e) {
            System.err.println("Lỗi Server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
