package storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import core.ReadFactory;
import storage.PatentNodeStorage;

public class PatentStorage {
	public ArrayList<PatentNodeStorage> Patents = new ArrayList<PatentNodeStorage>();
	public static int count = 0;

	public int addPatent(PatentNodeStorage node) {
		node.id = count++;
		Patents.add(node);
		return node.id;
	}

	public void print() {
		for (PatentNodeStorage node : Patents) {
			System.out.println(node.id + ":" + node.no + ",\t" + node.name
					+ ",\t" + node.category + ":\t" + node.abstruct + "-"
					+ node.isAutomaticClassified);
		}
	}

	@SuppressWarnings("unused")
	public void write2sql() throws ClassNotFoundException, SQLException {
		Connection conn = null;
		String sql;
		String url = "jdbc:mysql://localhost:3306/auto";
		String user = "root", password = "123456";
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(url, user, password);
		Statement stmt = conn.createStatement();
		for (PatentNodeStorage node : Patents) {
			sql = "insert auto.patent(id,no,name,abstruct,category,isAuto,isMatch) values("
					+ node.id
					+ ",'"
					+ node.no
					+ "','"
					+ node.name.replace("'", "''")
					+ "','"
					+ node.abstruct.replace("'", "''")
					+ "',"
					+ node.category
					+ ","
					+ node.isAutomaticClassified
					+ ","
					+ node.isMatched
					+ ")";
			try {
				int result = stmt.executeUpdate(sql);
			} catch (Exception e) {
				System.out.println(sql);
			}
		}
	}

	public int readFromsql(ReadFactory factory) throws ClassNotFoundException,
			SQLException {
		Connection conn = null;
		String sql = "select * from patent";
		String url = "jdbc:mysql://localhost:3306/auto";
		String user = "root", password = "123456";
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(url, user, password);
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		int count = 0;
		while (rs.next()) {
			PatentNodeStorage node = new PatentNodeStorage(rs.getInt(1),
					rs.getString(2), rs.getString(3), rs.getString(4),
					rs.getInt(5), rs.getBoolean(6), rs.getBoolean(7));
			factory.patentStorage.addPatent(node);
			count++;
		}
		return count;
	}

}
