package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;

import org.junit.Test;

import util.TextParser;

public class TextParserTest {

	public void printHashMap(HashMap<?, ?> hm) {
		int count = 0;
		Iterator<?> it = hm.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			System.out.print("key:\t" + key + "\t");
			System.out.println("value:\t" + hm.get(key));
			count += 1;
		}
		System.out.println("Count:" + count);
	}

	/* Test For Text Parser */
	@Test
	public void Testmain() throws Exception {
		String str = new String(
				"A method and system for ordering software modules in a guaranteed orderfor execution. Unique values are statically assigned to software modules (e.g., filter drivers) when fully developed. Each module's assigned value determines its relative position to other modules in a stack or other arrangement, fixing the execution order for any set of filter drivers. Static values may comprise floating-point numbers, whereby each new software module may be assigned a number that enables positioning it between any two existing software modules. For example, filter drivers may be generally classified and assigned values in a range according to type. Drivers of the same type may be ordered within their general range to guarantee one possible ordering. A filter manager architecture is described, in which filter drivers register with a manager for relevant file system I/O operations. The manager calls appropriately registered filter drivers in an order based on their assigned numbers.");
		TextParser t = new TextParser();
		printHashMap(t.segment(str));
	}

	@Test
	public void TestLucene() throws Exception {
		String str = new String(
				"A method and system for ordering software modules in a guaranteed orderfor execution. Unique values are statically assigned to software modules (e.g., filter drivers) when fully developed. Each module's assigned value determines its relative position to other modules in a stack or other arrangement, fixing the execution order for any set of filter drivers. Static values may comprise floating-point numbers, whereby each new software module may be assigned a number that enables positioning it between any two existing software modules. For example, filter drivers may be generally classified and assigned values in a range according to type. Drivers of the same type may be ordered within their general range to guarantee one possible ordering. A filter manager architecture is described, in which filter drivers register with a manager for relevant file system I/O operations. The manager calls appropriately registered filter drivers in an order based on their assigned numbers.");
		TextParser t = new TextParser();
		printHashMap(t.luceneTokenizer(str));
	}

	@Test
	public void TestOpenNLP() throws Exception {
		String str = new String(
				"A method and system for ordering software modules in a guaranteed orderfor execution. Unique values are statically assigned to software modules (e.g., filter drivers) when fully developed. Each module's assigned value determines its relative position to other modules in a stack or other arrangement, fixing the execution order for any set of filter drivers. Static values may comprise floating-point numbers, whereby each new software module may be assigned a number that enables positioning it between any two existing software modules. For example, filter drivers may be generally classified and assigned values in a range according to type. Drivers of the same type may be ordered within their general range to guarantee one possible ordering. A filter manager architecture is described, in which filter drivers register with a manager for relevant file system I/O operations. The manager calls appropriately registered filter drivers in an order based on their assigned numbers.");
		TextParser t = new TextParser();
		printHashMap(t.opennlpTokenizer(str));
	}
	
	@SuppressWarnings("resource")
	public String TestRawFile(String url) throws Exception{
		BufferedReader br=new BufferedReader(new FileReader(url));
		String line="";
		StringBuffer  buffer = new StringBuffer();
		while((line=br.readLine())!=null){
		buffer.append(line);
		}
		String fileContent = buffer.toString();
		return fileContent;
	}
	
	@Test
	public void Testmain2() throws Exception {
		String str = TestRawFile(".\\src\\main\\resources\\US-US20010020245.html");
		TextParser t = new TextParser();
		printHashMap(t.segment(str));
	}

	@Test
	public void TestLucene2() throws Exception {
		String str = TestRawFile(".\\src\\main\\resources\\US-US20010020245.html");
		TextParser t = new TextParser();
		printHashMap(t.luceneTokenizer(str));
	}
	
	@Test
	public void TestOpenNLP2() throws Exception {
		String str = TestRawFile(".\\src\\main\\resources\\US-US20010020245.html");
		TextParser t = new TextParser();
		printHashMap(t.opennlpTokenizer(str));
	}
}
