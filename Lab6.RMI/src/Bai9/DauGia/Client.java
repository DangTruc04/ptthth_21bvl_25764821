package Bai9.DauGia;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Map;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            
            AuctionService auctionService = (AuctionService) registry.lookup("AuctionService");
            
            Scanner scanner = new Scanner(System.in);
            System.out.print("Nhập tên định danh của bạn (Tên người dùng/Bidder): ");
            String username = scanner.nextLine();
            
            int choice;
            do {
                System.out.println("\n--- HỆ THỐNG ĐẤU GIÁ TRỰC TUYẾN ---");
                System.out.println("1. Xem danh sách sản phẩm đấu giá");
                System.out.println("2. Đặt giá thầu (Bid) cho sản phẩm");
                System.out.println("3. Thêm sản phẩm mới (Dành cho Seller)");
                System.out.println("0. Thoát");
                System.out.print("Chọn chức năng: ");
                choice = Integer.parseInt(scanner.nextLine());
                
                switch (choice) {
                    case 1:
                        Map<String, Double> productList = auctionService.getProducts();
                        System.out.println("\n--- DANH SÁCH SẢN PHẨM ĐANG ĐẤU GIÁ ---");
                        if (productList.isEmpty()) {
                            System.out.println("Hiện chưa có sản phẩm nào.");
                        } else {
                            for (Map.Entry<String, Double> entry : productList.entrySet()) {
                                System.out.println("- Tên: " + entry.getKey() + " | Giá hiện tại: " + entry.getValue() + " VNĐ");
                            }
                        }
                        break;
                        
                    case 2:
                        System.out.print("Nhập tên sản phẩm muốn đấu giá: ");
                        String prodName = scanner.nextLine();
                        System.out.print("Nhập mức giá thầu muốn đặt (VNĐ): ");
                        double bidPrice = Double.parseDouble(scanner.nextLine());
                        
                        boolean success = auctionService.placeBid(prodName, bidPrice, username);
                        if (success) {
                            System.out.println("-> Đặt giá thầu thành công! Bạn đang dẫn đầu.");
                        } else {
                            System.out.println("-> Đặt giá thầu thất bại! (Giá đưa ra phải cao hơn giá hiện tại hoặc sai tên sản phẩm)");
                        }
                        break;
                        
                    case 3:
                        System.out.print("Nhập tên sản phẩm mới: ");
                        String newProd = scanner.nextLine();
                        System.out.print("Nhập giá khởi điểm (VNĐ): ");
                        double startPrice = Double.parseDouble(scanner.nextLine());
                        
                        auctionService.addProduct(newProd, startPrice);
                        System.out.println("-> Đã thêm sản phẩm vào hệ thống đấu giá thành công!");
                        break;
                        
                    case 0:
                        System.out.println("Thoát hệ thống đấu giá.");
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
