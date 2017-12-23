package de.upb.snlp.scm.util;

import org.jsoup.examples.HtmlToPlainText;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 
 * @author Kadiray Karakaya
 *
 */
public class Parser {

	public static String getPlainText(Document doc) {
		Elements paragraphs = doc.getElementsByClass("mw-parser-output").get(0).getElementsByTag("p");

		String article = null;

		HtmlToPlainText html2text = new HtmlToPlainText();

		for (Element e : paragraphs) {
			article += html2text.getPlainText(e);
		}

		article = normalize(article);

		return article;
	}

	private static String normalize(String text) {
		text = removeParenthesis(text);
		return text;
	}

	private static String removeParenthesis(String text) {

		text = text.replaceAll("\\s*\\[[^\\]]*\\]\\s*", " ");
		text = text.replaceAll("\\s*\\{[^\\}]*\\}\\s*", " ");
		// text = text.replaceAll("\\s*\\([^\\)]*\\)\\s*", " ");

		text = text.replaceAll("\\s*\\<[^\\)]*\\>\\s*", " ");
		text = text.replaceAll("\\s*\\<[^\\)]*\\>\\s*", " ");

		return text;

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
