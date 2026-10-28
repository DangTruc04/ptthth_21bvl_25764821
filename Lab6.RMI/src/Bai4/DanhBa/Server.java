package Bai4.DanhBa;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class Server extends UnicastRemoteObject implements DanhBa {
    private HashMap<String, String> contacts;

    public Server() throws RemoteException {
        super();
        contacts = new HashMap<>();
    }

    @Override
    public synchronized void addContact(String name, String phone) throws RemoteException {
        contacts.put(name, phone);
        System.out.println("[SERVER] Đã thêm/cập nhật liên hệ: " + name + " - " + phone);
    }

    @Override
    public synchronized String findContact(String name) throws RemoteException {
        System.out.println("[SERVER] Yêu cầu tìm kiếm liên hệ: " + name);
        if (contacts.containsKey(name)) {
            return contacts.get(name);
        }
        return null;
    }

    @Override
    public synchronized boolean deleteContact(String name) throws RemoteException {
        System.out.println("[SERVER] Yêu cầu xóa liên hệ: " + name);
        if (contacts.containsKey(name)) {
            contacts.remove(name);
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            Server server = new Server();
            registry.rebind("DanhBaService", server);
            System.out.println("Server quản lý danh bạ đang chạy...");
        } catch (Exception e) {
            System.err.println("Lỗi Server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}