package de.upb.snlp.scm.core;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Network utility class
 * 
 * @author Kadiray Karakaya
 *
 */
public class Network {

	public static Document doc = null;

	/**
	 * fetches wikipedia article as html and returns as plain text
	 * 
	 * @param title
	 * @return
	 */
	public static String getCorpus(String title) {
		String plaintext = null;
		try {
			doc = Jsoup.connect("https://en.wikipedia.org/wiki/" + title).get();
			plaintext = Parser.getPlainText(doc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return plaintext;

	}

}
