package core;

import org.junit.Test;

public class VectorFactoryTest {

	//@Test
	public void testEASY() throws Exception {
		VectorFactory f = new VectorFactory();
		f.readFiles();
		f.deleteVector();
		f.deleteVectorTable();
		f.IG();
		f.TFIDF();
		f.caculateEASY();
	}
	
	@Test
	public void testALL20150102() throws Exception{
		/*
		 * Read Origin Files with 266.114s
		 * Study Progress using 20.647s
		 */
		VectorFactory f = new VectorFactory();
		//CsvReader c = new CsvReader();
		//c.readOriginfile(f,  "./src/main/resources/", "index1.csv");
		//f.categoryStorage.write2sql();
		//f.patentStorage.write2sql();
		f.readFiles();
		f.deleteVector();
		f.deleteVectorTable();
		f.IG();
		f.TFIDF();
		f.caculateEASY();
	}

}
