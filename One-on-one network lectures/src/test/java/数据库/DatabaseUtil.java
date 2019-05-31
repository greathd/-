package 数据库;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseUtil {

	private static final String 配置文件路径 = "数据库/dbconfig.properties";
	private static Properties 配置管理器 = new Properties();

	static {
		try {
			InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(配置文件路径);
			配置管理器.load(in);
			Class.forName(配置管理器.getProperty("driverClassName"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(DatabaseUtil.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static Connection getConnection() {
		try {
			return DriverManager.getConnection(配置管理器.getProperty("url"), 配置管理器.getProperty("username"),
					配置管理器.getProperty("password"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void 查找() {
		try (Connection con = DatabaseUtil.getConnection()) {
			String sql = "SELECT * FROM begin_class";
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " "
						+ rs.getString(4) + " " + rs.getString(5) + " " + rs.getString(6) + " " + rs.getString(7) + " "
						+ rs.getString(8) + " " + rs.getString(9) + " " + rs.getString(10));
			}
			rs.close();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public static void 更新() {
		try (Connection con = DatabaseUtil.getConnection()) {
			String sql = "INSERT INTO 学生(Id,新字段) VALUES (?,?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "2");
			pstmt.setString(2, "huidi");
			pstmt.executeUpdate();
			pstmt.close();
			con.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		更新();
	}
}
