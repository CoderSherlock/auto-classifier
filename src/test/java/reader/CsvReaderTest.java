package reader;

import java.io.IOException;
import java.sql.SQLException;

import org.htmlparser.util.ParserException;
import org.junit.Test;

import storage.CategoryNodeStorage;
import storage.CategoryStorage;
import core.ReadFactory;

public class CsvReaderTest {

	// @Test
	public void testreadfile() throws IOException, ParserException,
			ClassNotFoundException, SQLException {
		CsvReader csv = new CsvReader();
		ReadFactory f = new ReadFactory();
		csv.readOriginfile(f, "./src/main/resources/", "index1.csv");

	}

	// @Test
	public void testWriteSql() throws ClassNotFoundException, SQLException,
			ParserException, IOException {
		CsvReader csv = new CsvReader();
		ReadFactory f = new ReadFactory();
		csv.readOriginfile(f, "./src/main/resources/", "index1.csv");
		f.categoryStorage.write2sql();
		// f.patentStorage.write2sql();
	}

	// @Test
	public void string() {
		System.out.println(new String("hello's").replace("'", "''"));
		String a = new String("hello");
		System.out.println(a.substring(0, a.length() - 1) + a.length());
	}

	// @Test
	public void testReadSQL() throws ClassNotFoundException, SQLException {
		ReadFactory f = new ReadFactory();
		System.out.println("Cate:" + f.categoryStorage.readFromsql(f));
		System.out.println("patn:" + f.patentStorage.readFromsql(f));
		f.patentStorage.print();
		f.categoryStorage.print();
	}

	//@Test
	public void testFa_SonNode() throws Exception {
		ReadFactory f = new ReadFactory();
		f.readFiles();
		for (int i = 0, j = f.categoryStorage.index.size(); i < j; i++) {
			int countSon = 0;
			int father = f.categoryStorage.index.get(i).id;
			if (f.categoryStorage.hasChild(father)) {
				int countFather = f.categoryStorage.index.get(i).count;
				for (int k = 0, l = f.categoryStorage.index.size(); k < l; k++) {
					if (f.categoryStorage.index.get(k).fatherid == father)
						countSon += f.categoryStorage.index.get(k).count;
				}
				if (countFather != countSon)
					System.out.println(father + ":" + countSon + "/"
							+ countFather);
			} else {
				continue;
			}
		}
	}
}
