package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SanPhamApp {
	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Scanner sc = new Scanner(System.in);
		
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:DB/nhanvien.db");
			
			conn.setAutoCommit(false);
			
			stmt = conn.createStatement();
			
			String sqlTaoBang = "CREATE TABLE IF NOT EXISTS SanPham ("	
					+ "maSP INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ "tenSP TEXT NOT NULL, "
					+ "gia REAL NOT NULL, "
					+ "soluong INTEGER NOT NULL)";
			stmt.executeUpdate(sqlTaoBang);
			
			int luaChon;
			do {
				System.out.println("\n========== QUAN LY SAN PHAM ==========");
				System.out.println("1. Them it nhat 3 san pham");
				System.out.println("2. Hien thi tat ca san pham");
				System.out.println("3. Tim kiem san pham theo tenSP");
				System.out.println("4. Cap nhat gia hoac so luong theo maSP");
				System.out.println("5. Xoa san pham theo maSP");
				System.out.println("0. Thoat");
				System.out.print("Nhap lua chon: ");
				luaChon = Integer.parseInt(sc.nextLine());
				
				switch (luaChon) {
				case 1: {
					System.out.print("Nhap so luong san pham muon them (>=1): ");
					int n = Integer.parseInt(sc.nextLine());
					
					String sqlThem = "INSERT INTO SanPham(tenSP, gia, soluong) VALUES(?, ?, ?)";
					PreparedStatement psThem = conn.prepareStatement(sqlThem);
					
					for (int i = 1; i <= n; i++) {
						System.out.println("\n--- San pham thu " + i + " ---");
						System.out.print("Nhap ten san pham: ");
						String tenSP = sc.nextLine();
						
						System.out.print("Nhap gia san pham: ");
						double gia = Double.parseDouble(sc.nextLine());
						
						System.out.print("Nhap so luong: ");
						int soluong = Integer.parseInt(sc.nextLine());
						
						psThem.setString(1, tenSP);
						psThem.setDouble(2, gia);
						psThem.setInt(3, soluong);
						psThem.executeUpdate();
					}
					
					conn.commit();
					System.out.println("\nDa them " + n + " san pham thanh cong!");
					psThem.close();
					break;
				}
				
				case 2: {
					String sqlHienThi = "SELECT * FROM SanPham";
					PreparedStatement psHienThi = conn.prepareStatement(sqlHienThi);
					rs = psHienThi.executeQuery();
					
					System.out.println("\n========== DANH SACH SAN PHAM ==========");
					System.out.printf("%-5s | %-20s | %-15s | %-10s\n", "maSP", "tenSP", "gia", "soluong");
					System.out.println("------------------------------------------------------------");
					
					boolean coDuLieu = false;
					while (rs.next()) {
						coDuLieu = true;
						int maSP = rs.getInt("maSP");
						String tenSP = rs.getString("tenSP");
						double gia = rs.getDouble("gia");
						int soluong = rs.getInt("soluong");
						
						System.out.printf("%-5d | %-20s | %-15.0f | %-10d\n", maSP, tenSP, gia, soluong);
					}
					
					if (!coDuLieu) {
						System.out.println("Chua co san pham nao trong danh muc!");
					}
					break;
				}
				
				case 3: {
					System.out.print("Nhap ten san pham can tim: ");
					String tuKhoa = sc.nextLine();
					
					String sqlTim = "SELECT * FROM SanPham WHERE tenSP LIKE ?";
					PreparedStatement psTim = conn.prepareStatement(sqlTim);
					psTim.setString(1, "%" + tuKhoa + "%");
					rs = psTim.executeQuery();
					
					System.out.println("\n========== KET QUA TIM KIEM ==========");
					System.out.printf("%-5s | %-20s | %-15s | %-10s\n", "maSP", "tenSP", "gia", "soluong");
					System.out.println("------------------------------------------------------------");
					
					boolean timThay = false;
					while (rs.next()) {
						timThay = true;
						int maSP = rs.getInt("maSP");
						String tenSP = rs.getString("tenSP");
						double gia = rs.getDouble("gia");
						int soluong = rs.getInt("soluong");
						
						System.out.printf("%-5d | %-20s | %-15.0f | %-10d\n", maSP, tenSP, gia, soluong);
					}
					
					if (!timThay) {
						System.out.println("Khong tim thay san pham nao phu hop!");
					}
					
					psTim.close();
					break;
				}
				
				case 4: {
					System.out.print("Nhap maSP can cap nhat: ");
					int maSPCapNhat = Integer.parseInt(sc.nextLine());
					System.out.print("Nhap gia moi: ");
					double giaMoi = Double.parseDouble(sc.nextLine());
					System.out.print("Nhap so luong moi: ");
					int slMoi = Integer.parseInt(sc.nextLine());
					PreparedStatement psCapNhat = null;
					try {
						conn.setAutoCommit(false); 
						String sqlCapNhat = "UPDATE SanPham SET gia = ?, soluong = ? WHERE maSP = ?";
						psCapNhat = conn.prepareStatement(sqlCapNhat);
						psCapNhat.setDouble(1, giaMoi);
						psCapNhat.setInt(2, slMoi);
						psCapNhat.setInt(3, maSPCapNhat);
						
						int soDong = psCapNhat.executeUpdate();
						conn.commit();
						
						if (soDong > 0) {
							System.out.println("\nCap nhat san pham thanh cong!");
						} else {
							System.out.println("\nCap nhat that bai! Khong ton tai maSP nay.");
						}
					}catch (SQLException e) {
						System.out.println("\n[Loi SQL] Khi cap nhat san pham!");
						e.printStackTrace();
						if (conn != null) {
							try {
								conn.rollback();
								System.out.println("Da rollback thanh cong!");
							} catch (SQLException ex) {
								ex.printStackTrace();
							}
						}
					} finally {
						try {
							if (psCapNhat != null) psCapNhat.close();
							conn.setAutoCommit(true);
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					break;
				}
				
				case 5: {
					System.out.print("Nhap maSP can xoa: ");
					int maSPXoa = Integer.parseInt(sc.nextLine());
					PreparedStatement psXoa = null;
					try {
						conn.setAutoCommit(false);
						String sqlXoa = "DELETE FROM SanPham WHERE maSP = ?";
						psXoa = conn.prepareStatement(sqlXoa);
						psXoa.setInt(1, maSPXoa);
						
						int soDongXoa = psXoa.executeUpdate();
						conn.commit();
						
						if (soDongXoa > 0) {
							System.out.println("\nXoa san pham thanh cong!");
						} else {
							System.out.println("\nXoa that bai! Khong ton tai maSP nay.");
						}
					}catch(SQLException e){
						System.out.println("\n[Loi SQL] Khi xoa san pham!");
						e.printStackTrace();
						if (conn != null) {
							try {
								conn.rollback();
								System.out.println("Da rollback thanh cong!");
							} catch (SQLException ex) {
								ex.printStackTrace();
							}
						}
					} finally {
						try {
							if (psXoa != null) psXoa.close();
							conn.setAutoCommit(true);
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					break;
				}
				
				case 0: {
					System.out.println("\nDang thoat chuong trinh...");
					break;
				}
				
				default: {
					System.out.println("\nLua chon khong hop le! Vui long chon lai.");
					break;
				}
				}
				
			} while (luaChon != 0);
			
		} catch (SQLException e) {
			System.out.println("\n[LOI SQL] Xay ra loi trong qua trinh thuc thi!");
			e.printStackTrace();
			if (conn != null) {
				try {
					conn.rollback();
					System.out.println("Da thuc hien ROLLBACK de huy bo cac thay doi!");
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		} catch (ClassNotFoundException e) {
			System.out.println("\n[LOI] Khong tim thay Driver JDBC!");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("\n[LOI] Du lieu nhap khong hop le!");
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if (conn != null) {
					conn.setAutoCommit(true);
					conn.close();
					System.out.println("Da dong ket noi CSDL.");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			if (sc != null) {
				sc.close();
			}
		}
	}
}
