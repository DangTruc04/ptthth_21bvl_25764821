package Bai3.SoNguyenTo;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		try {
			Registry registry = LocateRegistry.getRegistry("localhost", 1099);
			KiemTra kiemtra=(KiemTra) registry.lookup("KiemTraService");
			
			System.out.print("Nhap so can kiem tra: ");
			int n = scanner.nextInt();
			
			boolean kt = kiemtra.isPrime(n);
			if(kt==true) {
				System.out.print("So "+ n+ " la so nguyen to");
			}else if(kt==false) {
				System.out.print("So "+n+" khong phai la so nguyen to");
			}
		}catch(Exception e) {
			System.err.println("Lỗi Client: " + e.getMessage());
            e.printStackTrace();
		}finally {
            scanner.close();
        }

	}

}
