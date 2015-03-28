package reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class HtmlReader {

	/*
	 * This filter only for tag <P>(both lowcase) TODO:Provide different filter
	 * for whole documents
	 */
	public String returnAbstract(String addr, String name)
			throws FileNotFoundException, IOException, ParserException {
		String tmpabstract = "";
		String html = returnString(addr + name);
		Parser parser = new Parser(html);
		parser.setEncoding("UTF-8");
		NodeFilter filter = new TagNameFilter("P");
		NodeList nodeList = parser.extractAllNodesThatMatch(filter);
		Node text = (Node) nodeList.elementAt(0);
		tmpabstract = text.toPlainTextString();
		return tmpabstract;
	}

	@SuppressWarnings("resource")
	public String returnString(String addr) throws IOException,
			FileNotFoundException {
		File input = new File(addr);
		InputStreamReader read = new InputStreamReader(new FileInputStream(
				input), "GBK");
		BufferedReader in = new BufferedReader(read);
		String rString = "";
		String tmpString = "";
		while ((tmpString = in.readLine()) != null) {
			rString += tmpString;
		}
		return rString;
	}

}
