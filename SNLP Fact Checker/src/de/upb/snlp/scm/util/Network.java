package de.upb.snlp.scm.util;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.examples.HtmlToPlainText;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * 
 * @author Kadiray Karakaya
 *
 */
public class Network {

	public static String getCorpus(String title) {
		String plt = null;
		try {
			Document doc = Jsoup.connect("https://en.wikipedia.org/wiki/Henry_Dunant").get();
			plt = Parser.getPlainText(doc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// MediaWikiBot wikiBot = new
		// MediaWikiBot("https://en.wikipedia.org/w/");
		// Article article = wikiBot.getArticle(title);
		// return Parser.getPlainText(article.getText());
		return plt;

	}

}
