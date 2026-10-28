package Bai7.DienTich;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server extends UnicastRemoteObject implements GeometryService {

    public Server() throws RemoteException {
        super();
    }

    @Override
    public double rectangleArea(double width, double height) throws RemoteException {
        System.out.println("[SERVER] Tính diện tích Hình Chữ Nhật: rộng = " + width + ", dài = " + height);
        return width * height;
    }

    @Override
    public double circleArea(double radius) throws RemoteException {
        System.out.println("[SERVER] Tính diện tích Hình Tròn: bán kính = " + radius);
        return Math.PI * radius * radius;
    }

    @Override
    public double triangleArea(double base, double height) throws RemoteException {
        System.out.println("[SERVER] Tính diện tích Hình Tam Giác: đáy = " + base + ", chiều cao = " + height);
        return 0.5 * base * height;
    }

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            
            Server server = new Server();
            registry.rebind("GeometryService", server);
            
            System.out.println("Server Tính Diện Tích Hình Học đang chạy...");
        } catch (Exception e) {
            System.err.println("Lỗi Server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
