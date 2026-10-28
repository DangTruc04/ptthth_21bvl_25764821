package Bai7.DienTich;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            
            GeometryService geometryService = (GeometryService) registry.lookup("GeometryService");
            
            Scanner scanner = new Scanner(System.in);
            int choice;
            
            do {
                System.out.println("\n--- TÍNH DIỆN TÍCH HÌNH HỌC (RMI) ---");
                System.out.println("1. Tính diện tích Hình Chữ Nhật");
                System.out.println("2. Tính diện tích Hình Tròn");
                System.out.println("3. Tính diện tích Hình Tam Giác");
                System.out.println("0. Thoát");
                System.out.print("Chọn hình muốn tính: ");
                choice = Integer.parseInt(scanner.nextLine());
                
                switch (choice) {
                    case 1:
                        System.out.print("Nhập chiều rộng: ");
                        double width = Double.parseDouble(scanner.nextLine());
                        System.out.print("Nhập chiều dài (chiều cao): ");
                        double heightRect = Double.parseDouble(scanner.nextLine());
                        double rectArea = geometryService.rectangleArea(width, heightRect);
                        System.out.println("-> Diện tích hình chữ nhật là: " + rectArea);
                        break;
                        
                    case 2:
                        System.out.print("Nhập bán kính hình tròn: ");
                        double radius = Double.parseDouble(scanner.nextLine());
                        double circleArea = geometryService.circleArea(radius);
                        System.out.println("-> Diện tích hình tròn là: " + circleArea);
                        break;
                        
                    case 3:
                        System.out.print("Nhập độ dài cạnh đáy: ");
                        double base = Double.parseDouble(scanner.nextLine());
                        System.out.print("Nhập chiều cao tam giác: ");
                        double heightTri = Double.parseDouble(scanner.nextLine());
                        double triangleArea = geometryService.triangleArea(base, heightTri);
                        System.out.println("-> Diện tích hình tam giác là: " + triangleArea);
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
