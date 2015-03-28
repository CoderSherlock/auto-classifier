package reader;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.htmlparser.util.ParserException;
import org.junit.Test;

import reader.HtmlReader;

public class HtmlReaderTest {

	@Test
	public void testReturnAbstract() throws FileNotFoundException, ParserException, IOException {
		String addr = "./src/main/resources/patents/";
		HtmlReader reader = new HtmlReader();
		System.out.println(reader.returnAbstract(addr, "US-US20010020245.html"));
	}

}
