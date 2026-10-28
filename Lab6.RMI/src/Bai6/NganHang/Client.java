package Bai6.NganHang;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            
            BankService bankService = (BankService) registry.lookup("BankService");
            
            Scanner scanner = new Scanner(System.in);
            int choice;
            
            do {
                System.out.println("\n--- HỆ THỐNG NGÂN HÀNG TỪ XA (RMI) ---");
                System.out.println("1. Kiểm tra số dư");
                System.out.println("2. Gửi tiền vào tài khoản");
                System.out.println("3. Rút tiền từ tài khoản");
                System.out.println("0. Thoát");
                System.out.print("Chọn chức năng: ");
                choice = Integer.parseInt(scanner.nextLine());
                
                switch (choice) {
                    case 1:
                        double currentBalance = bankService.getBalance();
                        System.out.println("-> Số dư hiện tại của bạn là: " + currentBalance + " VND");
                        break;
                        
                    case 2:
                        System.out.print("Nhập số tiền cần gửi: ");
                        double depositAmount = Double.parseDouble(scanner.nextLine());
                        bankService.deposit(depositAmount);
                        System.out.println("-> Gửi tiền thành công!");
                        break;
                        
                    case 3:
                        System.out.print("Nhập số tiền cần rút: ");
                        double withdrawAmount = Double.parseDouble(scanner.nextLine());
                        boolean success = bankService.withdraw(withdrawAmount);
                        if (success) {
                            System.out.println("-> Rút tiền thành công!");
                        } else {
                            System.out.println("-> Rút tiền thất bại! (Kiểm tra lại số dư hoặc số tiền nhập)");
                        }
                        break;
                        
                    case 0:
                        System.out.println("Đã thoát ứng dụng ngân hàng.");
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
