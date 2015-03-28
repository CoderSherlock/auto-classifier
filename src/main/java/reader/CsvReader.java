package reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.htmlparser.util.ParserException;

import storage.CategoryNodeStorage;
import storage.PatentNodeStorage;
import core.ReadFactory;

public class CsvReader {
	@SuppressWarnings({ "resource" })
	public void readOriginfile(ReadFactory factory, String addr, String name)
			throws IOException, ParserException {
		File input = new File(addr + name);
		InputStreamReader read = new InputStreamReader(new FileInputStream(
				input));
		BufferedReader in = new BufferedReader(read);
		String tmpString = "";
		while ((tmpString = in.readLine()) != null) {
			/*
			 *
			 */
			String[] tmpPatent = tmpString.split(",");
			if (!tmpPatent[0].startsWith("U")) {
				tmpPatent[0] = tmpPatent[0].substring(1, tmpPatent[0].length());
			}
			String gStr = null, fStr = null, sStr = null;
			for (int i = 0, j = tmpPatent.length; i < j; i++) {
				if (tmpPatent[i].equals("网站链接")) {
					gStr = tmpPatent[i + 2];
					fStr = tmpPatent[i + 3];
					sStr = tmpPatent[i + 4];
					// System.out.println(gStr+fStr+sStr);
					break;
				}

			}
			if (!gStr.equals("")) {
				CategoryNodeStorage gNode = new CategoryNodeStorage(gStr, 1);
				factory.categoryStorage.addCategoryNode(gNode, "", "");
			} else {
				String tmpname = tmpPatent[0];
				String abStr = new HtmlReader().returnAbstract(addr
						+ "patents/", "US-" + tmpname + ".html");
				PatentNodeStorage ps = new PatentNodeStorage(tmpPatent[0],
						tmpPatent[1], abStr, -1);
				factory.patentStorage.addPatent(ps);
				factory.categoryStorage.addPatent(ps);
				continue;
			}
			if (!fStr.equals("")) {
				CategoryNodeStorage fNode = new CategoryNodeStorage(fStr, 2);
				factory.categoryStorage.addCategoryNode(fNode, gStr, "");
			} else {
				String tmpname = tmpPatent[0];
				String abStr = new HtmlReader().returnAbstract(addr
						+ "patents/", "US-" + tmpname + ".html");

				PatentNodeStorage ps = new PatentNodeStorage(tmpPatent[0],
						tmpPatent[1], abStr,
						factory.categoryStorage.returnCategoryID(gStr, 1, -1));
				factory.patentStorage.addPatent(ps);
				factory.categoryStorage.addPatent(ps);
				continue;
			}
			if (!sStr.equals("")) {
				CategoryNodeStorage sNode = new CategoryNodeStorage(sStr, 3);
				factory.categoryStorage.addCategoryNode(sNode, fStr, gStr);
				String tmpname = tmpPatent[0];
				String abStr = new HtmlReader().returnAbstract(addr
						+ "patents/", "US-" + tmpname + ".html");

				PatentNodeStorage ps = new PatentNodeStorage(
						tmpPatent[0],
						tmpPatent[1],
						abStr,
						factory.categoryStorage
								.returnCategoryID(
										sStr,
										3,
										factory.categoryStorage
												.returnCategoryID(
														fStr,
														2,
														factory.categoryStorage
																.returnCategoryID(
																		gStr,
																		1, -1))));
				factory.patentStorage.addPatent(ps);
				factory.categoryStorage.addPatent(ps);
				continue;

			} else {
				String tmpname = tmpPatent[0];
				String abStr = new HtmlReader().returnAbstract(addr
						+ "patents/", "US-" + tmpname + ".html");

				PatentNodeStorage ps = new PatentNodeStorage(tmpPatent[0],
						tmpPatent[1], abStr,
						factory.categoryStorage.returnCategoryID(fStr, 2,
								factory.categoryStorage.returnCategoryID(gStr,
										1, -1)));
				factory.patentStorage.addPatent(ps);
				factory.categoryStorage.addPatent(ps);
				continue;
			}

		}
		//factory.categoryStorage.print();
		//factory.patentStorage.print();
	}

}
