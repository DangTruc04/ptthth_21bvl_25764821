package Bai9.DauGia;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface AuctionService extends Remote {
    void addProduct(String productName, double startingPrice) throws RemoteException;
    Map<String, Double> getProducts() throws RemoteException;
    boolean placeBid(String productName, double bidAmount, String bidderName) throws RemoteException;
}
