package bank.management.system;

import java.sql.*;

public class Connn {
	Connection c;
	Statement s;

	public Connn() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankmanagementsystem", "root", "310305");
			s = c.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		return c;
	}

	public Statement getStatement() {
		return s;
	}
}
