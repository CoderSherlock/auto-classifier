package storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import core.ReadFactory;

public class CategoryStorage {
	public ArrayList<CategoryNodeStorage> index = new ArrayList<CategoryNodeStorage>();
	public static int count = 0;

	public void addpatent(int id, int patent) {
		for (CategoryNodeStorage i : index) {
			if (i.id == id) {
				i.patents.add(patent);
				i.count++;
				break;
			}
		}
	}

	public void changeCategory(int idfrom, int idto, int patentid) {

	}

	public boolean hasNode(int father, int level, String name) {
		for (int i = 0, j = index.size(); i < j; i++) {
			if (index.get(i).name.equals(name)
					&& index.get(i).fatherid == father
					&& index.get(i).level == level) {
				return true;
			}
		}
		return false;
	}

	public int addCategoryNode(CategoryNodeStorage node, String fathername,
			String gfathername) {
		fulfillCategory(node, fathername, gfathername);
		if (hasNode(node.fatherid, node.level, node.name)) {
			return -3;
		} else {
			node.id = count++; // Create Category ID
			index.add(node);
			return count;
		}
	}

	public int returnCategoryID(String name, int level, int fatherid) {
		for (int i = 0, j = index.size(); i < j; i++) {
			if (index.get(i).name.equals(name) && index.get(i).level == level
					&& index.get(i).fatherid == fatherid) {
				return index.get(i).id;
			}
		}
		return -2;
	}

	/*
	 * Method: FulfillCategory Before: name,level (2/7) After:
	 * id,name,fatherid,level (4/7) Remain: vector,patents,count
	 */
	public void fulfillCategory(CategoryNodeStorage node, String fathername,
			String gfathername) {
		if (fathername != "" && gfathername != "") {
			int gfatherid = returnCategoryID(gfathername, 1, -1);
			int fatherid = returnCategoryID(fathername, 2, gfatherid);
			node.fatherid = fatherid;

		} else if (fathername != "") {
			int fathid = returnCategoryID(fathername, 1, -1);
			node.fatherid = fathid;
		} else {
			node.fatherid = -1;
		}

	}

	public boolean hasChild(int id) {
		for (CategoryNodeStorage node : index) {
			if (node.fatherid == id)
				return true;
		}
		return false;
	}

	public void addPatent(PatentNodeStorage node) {
		if (node.category != -1) {
			switch (index.get(node.category).level) {
			case 3:
				index.get(node.category).addPatent(node.id);
				index.get(index.get(node.category).fatherid).addPatent(node.id);
				index.get(index.get(index.get(node.category).fatherid).fatherid)
						.addPatent(node.id);
				break;
			case 2:
				index.get(node.category).addPatent(node.id);
				index.get(index.get(node.category).fatherid).addPatent(node.id);
				break;
			case 1:
				index.get(node.category).addPatent(node.id);
				break;
			}
		}
	}

	public void print() {
		for (CategoryNodeStorage node : index) {
			if (!hasChild(node.id)) {
				if (node.level == 3) {
					System.out.println(node.id + ":"
							+ index.get(index.get(node.fatherid).fatherid).name
							+ ",\t" + index.get(node.fatherid).name + ",\t"
							+ node.name + ":\t" + node.level + "-" + node.count
							+ "-" + node.fatherid);
				} else if (node.level == 2) {
					System.out.println(node.id + ":"
							+ index.get(node.fatherid).name + ",\t" + node.name
							+ ":\t" + node.level + "-" + node.count + "-"
							+ node.fatherid);
				} else if (node.level == 1) {
					System.out.println(node.id + ":" + node.name + ":\t"
							+ node.level + "-" + node.count + "-"
							+ node.fatherid);
				}

			}
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
		for (CategoryNodeStorage node : index) {
			sql = "insert auto.index(id,fatherid,level,name,vector,patents,count) values("
					+ node.id
					+ ","
					+ node.fatherid
					+ ","
					+ node.level
					+ ",'"
					+ node.name
					+ "','"
					+ node.vector.Vector2Str().replace("'", "''")
					+ "','"
					+ node.patents2Str() + "'," + node.count + ")";
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
		String sql = "select * from auto.index";
		String url = "jdbc:mysql://localhost:3306/auto";
		String user = "root", password = "123456";
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(url, user, password);
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		int count = 0;
		while (rs.next()) {
			CategoryNodeStorage node = new CategoryNodeStorage(rs.getInt(1),
					rs.getInt(2), rs.getInt(3), rs.getString(4),
					rs.getString(5), rs.getString(6), rs.getInt(7));
			factory.categoryStorage.index.add(node);
			count++;
		}
		return count;
	}

	public String StringAt(int i) {
		try {
			CategoryNodeStorage tmp = index.get(i);
			switch (tmp.level) {
			case 1:
				return tmp.name;
			case 2:
				return index.get(tmp.fatherid).name + "," + tmp.name;
			case 3:
				return index.get(index.get(tmp.fatherid).fatherid).name + ","
						+ index.get(tmp.fatherid).name + "," + tmp.name;
			}
			return "未分类";
		} catch (Exception exception) {
			return "未分类";
		}
	}

}