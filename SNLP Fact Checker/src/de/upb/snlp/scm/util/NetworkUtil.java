package de.upb.snlp.scm.util;

import net.sourceforge.jwbf.core.contentRep.Article;
import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;

public class NetworkUtil {

	public static String getCorpus(String title) {

		MediaWikiBot wikiBot = new MediaWikiBot("https://en.wikipedia.org/w/");
		Article article = wikiBot.getArticle(title);
		return getInfobox(article.getText());

	}

	private static String getInfobox(String article) {
		int startOfInfoBox = article.indexOf("Infobox");
		int nextLine = article.indexOf("\n", startOfInfoBox);
		while (!article.substring(nextLine + 1, nextLine + 3).equals("}}")) {
			nextLine = article.indexOf("\n", nextLine + 3);
		}
		return article.substring(startOfInfoBox, nextLine);
	}

}
