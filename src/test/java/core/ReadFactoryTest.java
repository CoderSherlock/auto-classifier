package core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;

import org.junit.Test;

import storage.PatentNodeStorage;
import util.TextParser;
import util.TextParserTest;

public class ReadFactoryTest {

	@SuppressWarnings("unused")
	// @Test
	public void caculation() throws Exception {
		ReadFactory f = new ReadFactory();
		f.readFiles();
		System.out.println(f.patentStorage.Patents.size());
		String fuckoff = "";
		for (PatentNodeStorage node : f.patentStorage.Patents) {
			fuckoff += " " + node.abstruct;
		}
		TextParser t = new TextParser();
		HashMap<String, Integer> hash = null;
		new TextParserTest().printHashMap(hash = t.luceneTokenizer(fuckoff));

		Connection conn = null;
		String sql;
		String url = "jdbc:mysql://localhost:3306/auto";
		String user = "root", password = "123456";
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(url, user, password);
		Statement stmt = conn.createStatement();

		Iterator<?> it = hash.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			sql = "insert cal(k,v) values('" + key.replace("'", "''") + "',"
					+ hash.get(key) + ")";
			try {
				int result = stmt.executeUpdate(sql);
			} catch (Exception e) {
				System.out.println(sql);
			}

		}
	}

	// @Test
	@SuppressWarnings("unused")
	public void caculation2() throws Exception {
		ReadFactory f = new ReadFactory();
		f.readFiles();
		f.IG();
		String all = f.AbstructSummary(-1);
		HashMap<String, Integer> hash = new TextParser().luceneTokenizer(all);
		Iterator<?> it1 = hash.keySet().iterator();

		Connection conn = null;
		String sql;
		String url = "jdbc:mysql://localhost:3306/auto";
		String user = "root", password = "123456";
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(url, user, password);
		Statement stmt = conn.createStatement();

		while (it1.hasNext()) {
			String word = (String) it1.next();
			int count = 0;
			for (int i = 0, j = f.vec.size(); i < j; i++) {
				if (f.vec.get(i).containsKey(word))
					count++;
			}
			sql = "insert cal2(k,v) values('" + word.replace("'", "''") + "',"
					+ count + ")";
			try {
				int result = stmt.executeUpdate(sql);
			} catch (Exception e) {
				System.out.println(sql);
			}

		}
	}

	// @Test
	public void testIG() throws Exception {
		ReadFactory f = new ReadFactory();
		f.categoryStorage.readFromsql(f);
		f.patentStorage.readFromsql(f);
		f.deleteVector();
		f.IG();
		System.out.println("finsih");
		f.updateIndexVector2SQL();
	}

	// @Test
	public void testBUG() throws Exception {
		ReadFactory f = new ReadFactory();
		f.categoryStorage.readFromsql(f);
		f.patentStorage.readFromsql(f);

		f.fulfillVec();
		for (int i = 0, j = f.vec.size(); i < j; i++) {
			if (f.vec.get(i).containsKey("practic"))
				System.out.println("!!!" + i);
		}
	}

	@Test
	public void testTFIDF() throws Exception {
		ReadFactory f = new ReadFactory();
		f.categoryStorage.readFromsql(f);
		f.patentStorage.readFromsql(f);
		f.deleteVector();
		f.IG();
		f.TFIDF();
		System.out.println("finsih");
		f.updateIndexVector2SQL();
	}

	// @Test
	public void testTokenizer() throws Exception {
		ReadFactory f = new ReadFactory();
		f.readFiles();
		f.fulfillVec();
	}

}
