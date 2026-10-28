package app;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class App {
	public static void main(String[] args) {
		 Connection conn = null;
		 Statement stmt = null; 
		 ResultSet rs = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:DB/nhanvien.db");
			conn.setAutoCommit(false);
			
			stmt = conn.createStatement();
			
			String sql_create_table = "CREATE TABLE IF NOT EXISTS NhanVien (id INTEGER PRIMARY KEY, ten TEXT NOT NULL, chuc_vu TEXT)";
			stmt.executeUpdate(sql_create_table);
			
			String sql_themNV1 = "INSERT INTO nhanvien(id,ten,chuc_vu) VALUES (1,'nguyen van a','IT')";
			stmt.executeUpdate(sql_themNV1);
			String sql_themNV2 = "INSERT INTO nhanvien(id,ten,chuc_vu) VALUES (2,'huynh van b','Ketoan')";
			stmt.executeUpdate(sql_themNV2);
			String sql_themNV3 = "INSERT INTO nhanvien(id,ten,chuc_vu) VALUES (3,'banh thi c','HR')";
			stmt.executeUpdate(sql_themNV3);
			
			conn.commit();
			
			String sql_select = "SELECT * FROM nhanvien";
			rs = stmt.executeQuery(sql_select);
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String ten = rs.getString("ten");
				String chuc_vu = rs.getString("chuc_vu");
				System.out.println("id: "+id+",ten: "+ten+",chuc vu: "+chuc_vu);
			}
			
		}catch(SQLException e) {
			System.out.println("Loi SQL xay ra: ");
			e.printStackTrace();
			if (conn != null) {
				try {
					conn.rollback();
					System.out.println("Da rollback thanh cong!");
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		} catch (ClassNotFoundException e) {
			System.out.println("Khong tim thay driver JDBC:"); 
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (stmt != null) stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (conn != null) {
					conn.setAutoCommit(true);
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
