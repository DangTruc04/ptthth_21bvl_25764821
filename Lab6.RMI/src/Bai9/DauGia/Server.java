package Bai9.DauGia;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class Server extends UnicastRemoteObject implements AuctionService {
    private Map<String, Double> products;
    private Map<String, String> highestBidders;

    public Server() throws RemoteException {
        super();
        products = new HashMap<>();
        highestBidders = new HashMap<>();
        
        products.put("Laptop Gaming", 15000000.0);
        highestBidders.put("Laptop Gaming", "Chưa có");
        
        products.put("Dong Ho Smartwatch", 2000000.0);
        highestBidders.put("Dong Ho Smartwatch", "Chưa có");
    }

    @Override
    public synchronized void addProduct(String productName, double startingPrice) throws RemoteException {
        products.put(productName, startingPrice);
        highestBidders.put(productName, "Chưa có");
        System.out.println("[SERVER] Đã thêm sản phẩm mới: " + productName + " | Giá khởi điểm: " + startingPrice + " VNĐ");
    }

    @Override
    public synchronized Map<String, Double> getProducts() throws RemoteException {
        System.out.println("[SERVER] Có client yêu cầu xem danh sách sản phẩm đấu giá.");
        return new HashMap<>(products);
    }

    @Override
    public synchronized boolean placeBid(String productName, double bidAmount, String bidderName) throws RemoteException {
        System.out.println("[SERVER] " + bidderName + " muốn đặt giá " + bidAmount + " VNĐ cho sản phẩm: " + productName);
        
        if (products.containsKey(productName)) {
            double currentPrice = products.get(productName);
            if (bidAmount > currentPrice) {
                products.put(productName, bidAmount);
                highestBidders.put(productName, bidderName);
                System.out.println("-> THÀNH CÔNG! Sản phẩm [" + productName + "] có giá cao mới: " + bidAmount + " VNĐ bởi " + bidderName);
                return true;
            } else {
                System.out.println("-> THẤT BẠI! Giá thầu thấp hơn hoặc bằng giá hiện tại (" + currentPrice + " VNĐ).");
            }
        } else {
            System.out.println("-> THẤT BẠI! Không tìm thấy sản phẩm này.");
        }
        return false;
    }

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            
            Server server = new Server();
            registry.rebind("AuctionService", server);
            
            System.out.println("Server Đấu Giá Trực Tuyến đang chạy...");
        } catch (Exception e) {
            System.err.println("Lỗi Server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
