package core;

import java.sql.SQLException;

import org.junit.Test;

import reader.CsvReader;

public class VectorFactoryTest {

	// @Test
	public void testEASY() throws Exception {
		VectorFactory f = new VectorFactory();
		f.readFiles();
		f.deleteVector();
		f.deleteVectorTable();
		f.IG();
		f.TFIDF();
		f.caculateEASY();
	}

	// @Test
	public void testALL20150102() throws Exception {
		/*
		 * Read Origin Files with 266.114s Study Progress using 20.647s
		 */
		VectorFactory f = new VectorFactory();
		CsvReader c = new CsvReader();
		c.readOriginfile(f, "./src/main/resources/", "index1.csv");
		f.categoryStorage.write2sql();
		f.patentStorage.write2sql();
		// f.readFiles();
		// sf.deleteVector();
		// f.deleteVectorTable();
		// f.IG();
		// f.categoryStorage.write2sql();
		// f.TFIDF();
		// f.caculateEASY();
	}

	// @Test
	public void testSingle() throws Exception, SQLException {
		VectorFactory f = new VectorFactory();
		f.readFiles();
		f.IG();
		f.TFIDF4single(-1, 372);
		System.out.println("haha");
	}

	@Test
	public void testClassify() throws Exception, SQLException {
		VectorFactory f = new VectorFactory();
		f.readFiles();
		f.IG();
		 f.classify(372, 70);
		/*
		for (int i = 0; i < 544; i++) {
			System.out.println(String.valueOf(i) + "," + f.classify(i, -1));
		}*/
	}

	// @Test
	public void testClassifyCal() throws Exception, SQLException {
		VectorFactory f = new VectorFactory();
		f.readFiles();
		f.IG();
		// f.classify(491, -1);
		for (int i = 0; i < 544; i++) {
			System.out.println(String.valueOf(i) + "," + f.classify(i, -1));
		}

		f.THRESHOLD = 11;
		f.IG();
		for (int i = 0; i < 544; i++) {
			System.out.println(String.valueOf(i) + "," + f.classify(i, -1));
		}

		f.THRESHOLD = 16;
		f.IG();
		for (int i = 0; i < 544; i++) {
			System.out.println(String.valueOf(i) + "," + f.classify(i, -1));
		}

		f.THRESHOLD = 21;
		f.IG();
		for (int i = 0; i < 544; i++) {
			System.out.println(String.valueOf(i) + "," + f.classify(i, -1));
		}

		f.THRESHOLD = 26;
		f.IG();
		for (int i = 0; i < 544; i++) {
			System.out.println(String.valueOf(i) + "," + f.classify(i, -1));
		}

		f.THRESHOLD = 31;
		f.IG();
		for (int i = 0; i < 544; i++) {
			System.out.println(String.valueOf(i) + "," + f.classify(i, -1));
		}
	}

	// @Test
	public void testClassify2() throws Exception, SQLException {
		VectorFactory f = new VectorFactory();
		f.readFiles();
		f.IG();
		// f.classify(491, -1);
		for (int i = 0; i < 544; i++) {
			System.out.println(String.valueOf(i) + "," + f.classify(i, -1, -1));
		}
		// f.categoryStorage.write2sql();
	}

}
