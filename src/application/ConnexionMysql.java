package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionMysql {
	public Connection cn = null;
	
	public static Connection connexionDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/locationlogement", "root", "");
			return cn;
		} 
		catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}