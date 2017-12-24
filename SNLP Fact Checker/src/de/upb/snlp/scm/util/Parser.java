package de.upb.snlp.scm.util;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * 
 * @author Kadiray Karakaya
 *
 */
public class Parser {

	public static String getPlainText(Document doc) {
		Elements ps = doc.select("p");
		return ps.text();
	}

	public static String getInfobox(String article) {
		int startOfInfoBox = article.indexOf("Infobox");
		int nextLine = article.indexOf("\n", startOfInfoBox);
		while (!article.substring(nextLine + 1, nextLine + 3).equals("}}")) {
			nextLine = article.indexOf("\n", nextLine + 3);
		}
		return article.substring(startOfInfoBox, nextLine);
	}

}
