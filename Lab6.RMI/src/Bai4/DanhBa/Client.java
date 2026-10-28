package Bai4.DanhBa;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            DanhBa danhBa = (DanhBa) registry.lookup("DanhBaService");
            
            Scanner scanner = new Scanner(System.in);
            int choice;
            
            do {
                System.out.println("\n--- QUẢN LÝ DANH BẠ RMI ---");
                System.out.println("1. Thêm / Sửa liên hệ");
                System.out.println("2. Tìm kiếm liên hệ");
                System.out.println("3. Xóa liên hệ");
                System.out.println("0. Thoát");
                System.out.print("Chọn chức năng: ");
                choice = Integer.parseInt(scanner.nextLine());
                
                switch (choice) {
                    case 1:
                        System.out.print("Nhập tên: ");
                        String nameAdd = scanner.nextLine();
                        System.out.print("Nhập số điện thoại: ");
                        String phoneAdd = scanner.nextLine();
                        danhBa.addContact(nameAdd, phoneAdd);
                        System.out.println("-> Thêm/Cập nhật thành công!");
                        break;
                        
                    case 2:
                        System.out.print("Nhập tên cần tìm: ");
                        String nameFind = scanner.nextLine();
                        String phoneResult = danhBa.findContact(nameFind);
                        if (phoneResult != null) {
                            System.out.println("-> Kết quả: Tên: " + nameFind + " | SĐT: " + phoneResult);
                        } else {
                            System.out.println("-> Không tìm thấy liên hệ này!");
                        }
                        break;
                        
                    case 3:
                        System.out.print("Nhập tên cần xóa: ");
                        String nameDel = scanner.nextLine();
                        boolean isDeleted = danhBa.deleteContact(nameDel);
                        if (isDeleted) {
                            System.out.println("-> Xóa liên hệ thành công!");
                        } else {
                            System.out.println("-> Không tìm thấy tên để xóa!");
                        }
                        break;
                        
                    case 0:
                        System.out.println("Thoát chương trình.");
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