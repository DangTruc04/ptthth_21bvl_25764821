package Bai8.MayBay;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FlightService extends Remote {
    boolean bookTicket(String flightNumber, int seats) throws RemoteException;
    int availableSeats(String flightNumber) throws RemoteException;
    boolean cancelBooking(String flightNumber, int seats) throws RemoteException;
}