package Bai8.MayBay;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class Server extends UnicastRemoteObject implements FlightService {
    private HashMap<String, Integer> flights;

    public Server() throws RemoteException {
        super();
        flights = new HashMap<>();

        flights.put("VN123", 50);
        flights.put("VJ456", 30);
        flights.put("QH789", 20);
    }

    @Override
    public synchronized int availableSeats(String flightNumber) throws RemoteException {
        System.out.println("[SERVER] Kiểm tra số ghế trống cho chuyến bay: " + flightNumber);
        if (flights.containsKey(flightNumber)) {
            return flights.get(flightNumber);
        }
        return -1; 
    }

    @Override
    public synchronized boolean bookTicket(String flightNumber, int seats) throws RemoteException {
        System.out.println("[SERVER] Yêu cầu đặt " + seats + " vé cho chuyến bay: " + flightNumber);
        if (flights.containsKey(flightNumber)) {
            int currentSeats = flights.get(flightNumber);
            if (currentSeats >= seats && seats > 0) {
                flights.put(flightNumber, currentSeats - seats);
                System.out.println("[SERVER] Đặt vé thành công! Ghế trống còn lại của " + flightNumber + ": " + flights.get(flightNumber));
                return true;
            }
        }
        System.out.println("[SERVER] Đặt vé thất bại (Không đủ ghế trống hoặc sai thông tin chuyến bay).");
        return false;
    }

    @Override
    public synchronized boolean cancelBooking(String flightNumber, int seats) throws RemoteException {
        System.out.println("[SERVER] Yêu cầu hủy " + seats + " vé cho chuyến bay: " + flightNumber);
        if (flights.containsKey(flightNumber) && seats > 0) {
            int currentSeats = flights.get(flightNumber);
            flights.put(flightNumber, currentSeats + seats);
            System.out.println("[SERVER] Hủy vé thành công! Ghế trống hiện tại của " + flightNumber + ": " + flights.get(flightNumber));
            return true;
        }
        System.out.println("[SERVER] Hủy vé thất bại.");
        return false;
    }

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            
            Server server = new Server();
            registry.rebind("FlightService", server);
            
            System.out.println("Server Đặt Vé Máy Bay đang chạy...");
            System.out.println("Các chuyến bay có sẵn: VN123 (50 ghế), VJ456 (30 ghế), QH789 (20 ghế)");
        } catch (Exception e) {
            System.err.println("Lỗi Server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
