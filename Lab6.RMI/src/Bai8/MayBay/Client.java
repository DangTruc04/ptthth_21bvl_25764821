package Bai8.MayBay;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            
            FlightService flightService = (FlightService) registry.lookup("FlightService");
            
            Scanner scanner = new Scanner(System.in);
            int choice;
            
            do {
                System.out.println("\n--- HỆ THỐNG ĐẶT VÉ MÁY BAY (RMI) ---");
                System.out.println("1. Kiểm tra số ghế trống");
                System.out.println("2. Đặt vé máy bay");
                System.out.println("3. Hủy vé đã đặt");
                System.out.println("0. Thoát");
                System.out.print("Chọn chức năng: ");
                choice = Integer.parseInt(scanner.nextLine());
                
                switch (choice) {
                    case 1:
                        System.out.print("Nhập số hiệu chuyến bay (vd: VN123, VJ456): ");
                        String flightCheck = scanner.nextLine();
                        int seats = flightService.availableSeats(flightCheck);
                        if (seats != -1) {
                            System.out.println("-> Chuyến bay " + flightCheck + " còn lại: " + seats + " ghế trống.");
                        } else {
                            System.out.println("-> Không tìm thấy mã chuyến bay này!");
                        }
                        break;
                        
                    case 2:
                        System.out.print("Nhập số hiệu chuyến bay muốn đặt: ");
                        String flightBook = scanner.nextLine();
                        System.out.print("Nhập số lượng vé muốn đặt: ");
                        int bookSeats = Integer.parseInt(scanner.nextLine());
                        boolean booked = flightService.bookTicket(flightBook, bookSeats);
                        if (booked) {
                            System.out.println("-> Đặt vé thành công!");
                        } else {
                            System.out.println("-> Đặt vé thất bại! (Kiểm tra lại số ghế hoặc mã chuyến bay)");
                        }
                        break;
                        
                    case 3:
                        System.out.print("Nhập số hiệu chuyến bay muốn hủy vé: ");
                        String flightCancel = scanner.nextLine();
                        System.out.print("Nhập số lượng vé muốn hủy: ");
                        int cancelSeats = Integer.parseInt(scanner.nextLine());
                        boolean canceled = flightService.cancelBooking(flightCancel, cancelSeats);
                        if (canceled) {
                            System.out.println("-> Hủy vé thành công!");
                        } else {
                            System.out.println("-> Hủy vé thất bại!");
                        }
                        break;
                        
                    case 0:
                        System.out.println("Thoát chương trình đặt vé.");
                        break;
                        
                    default:
                        System.out.println("Lựa chọn không hợp lệ!");
                }
            } while (choice != 0);
            
            scanner.close();
        } catch (Exception e) {
            System.err.println("Lỗi Client: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
