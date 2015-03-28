package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

public class TextParser {
	public HashMap<String, Integer> segment(String text) {
		HashMap<String, Integer> Caculation = new HashMap<String, Integer>();
		String[] split = text.split("\\s+|,\\s*|\\.\\s*");
		for (String i : split) {
			if (isAvlieable(i)) { /* Check for the word */
				i = lowercase(i);
				if (Caculation.containsKey(i)) {
					int value = Caculation.get(i) + 1;
					Caculation.put(i, value);
				} else {
					Caculation.put(i, 1);
				}
			}
		}
		return Caculation;
	}

	private boolean isAvlieable(String i) {
		// TODO : Wittern Method for checking words available
		return true;
	}

	@SuppressWarnings("deprecation")
	public HashMap<String, Integer> luceneTokenizer(String testString)
			throws Exception {
		HashMap<String, Integer> Caculation = new HashMap<String, Integer>();
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);

		Reader r = new StringReader(testString);
		StopFilter sf = (StopFilter) analyzer.tokenStream("", r);
		PorterStemFilter ps = new PorterStemFilter(sf);
		// System.err.println("=====standard analyzer====");
		// System.err.println("分析方法：混合分割,包括了去掉停止词，支持汉语");

		CharTermAttribute cab = ps.addAttribute(CharTermAttribute.class);

		// sf.reset();
		ps.reset();
		while (ps.incrementToken()) {
			String i = cab.toString();
			if (Caculation.containsKey(i)) {
				int value = Caculation.get(i) + 1;
				Caculation.put(i, value);
			} else {
				Caculation.put(i, 1);
			}
		}

		analyzer.close();
		ps.close();
		sf.close();
		return Caculation;
	}

	public HashMap<String, Integer> opennlpTokenizer(String text)
			throws Exception, IOException {
		HashMap<String, Integer> Caculation = new HashMap<String, Integer>();
		InputStream infile = new FileInputStream(
				".\\src\\main\\resources\\en-token.bin");
		TokenizerModel model = new TokenizerModel(infile);
		Tokenizer tokenizer = new TokenizerME(model);
		String[] split = tokenizer.tokenize(text);
		for (String i : split) {
			if (isAvlieable(i)) { /* Check for the word */
				i = lowercase(i);
				if (Caculation.containsKey(i)) {
					int value = Caculation.get(i) + 1;
					Caculation.put(i, value);
				} else {
					Caculation.put(i, 1);
				}
			}
		}
		return Caculation;
	}

	private String lowercase(String word) {
		/*
		 * char []letters = word.toCharArray(); String str = "";
		 * if(!Character.isLowerCase(letters[0]) &&
		 * Character.isLowerCase(letters[letters.length])){ str+=letters[0]. for
		 * (int i = 1,j=letters.length; i < j; i++) {
		 * 
		 * } }
		 */
		return word.toLowerCase();
	}

}
