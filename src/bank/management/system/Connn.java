package bank.management.system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Connn {
	Connection c;
	Statement statement;

	public Connn() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); // Register MySQL Driver
			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank_system", "username", "password");
			statement = c.createStatement(); // Create statement
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
}
