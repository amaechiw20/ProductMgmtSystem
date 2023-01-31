package pkg.cogent.jdbc;
/**
 *  @author William Amaechi
 * 	@date   Jan 30, 2023
 * 
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class JDBCUtilsProductDB {
	private static String jdbcUrl = "jdbc:mysql://localhost:3306/jdbc_demos";
	private static String jdbcUserName = "root";
	private static String jdbcPassword = "mysqlroot";

	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(jdbcUrl,jdbcUserName, jdbcPassword);
		}catch(SQLException sqle) {
			System.out.println("Failed to add Record to Product DB");
			sqle.printStackTrace();
		}
		
		return conn;
	}

}
