package Bai2.Tong2So;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		try {
			Registry registry = LocateRegistry.getRegistry("localhost",1099);
			TinhTong tinh = (TinhTong) registry.lookup("TinhTongService");
			
			System.out.print("Nhap so a: ");
			int a=scanner.nextInt();	
			System.out.print("Nhap so b: ");
			int b=scanner.nextInt();
			
			int tong = tinh.add(a, b);
			System.out.print("Tong cua phep tinh "+a+" + "+b+" = "+tong);
		}catch(Exception e) {
			System.err.println("Lỗi Client: " + e.getMessage());
            e.printStackTrace();
		}finally {
            scanner.close();
            }
	}

}
