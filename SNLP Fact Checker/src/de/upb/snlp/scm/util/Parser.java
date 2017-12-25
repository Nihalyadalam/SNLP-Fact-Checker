package de.upb.snlp.scm.util;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import de.upb.snlp.scm.core.NLP;
import de.upb.snlp.scm.model.Triplet;

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

	public static List<Triplet> getInfobox(String subject, Document doc) {
		List<Triplet> triplets = new ArrayList<>();
		Elements infoboxRows = doc.select("table[class*=infobox]").get(0).select("tbody").select("tr");
		for (Element e : infoboxRows) {
			if (e.text().startsWith("Born")) {
				triplets.addAll(getBirthInfo(subject, e.text()));
			}
		}
		return triplets;
	}

	public static List<Triplet> getBirthInfo(String subject, String bornLine) {
		List<Triplet> birthInfo = new ArrayList<>();
		Map<String, LinkedHashSet<String>> entityMap = NLP.findNamedEntities(bornLine, Config.NER_3_CLASSIFIER);
		LinkedHashSet<String> locationSet = entityMap.get("LOCATION");
		for (String s : locationSet) {
			Triplet t1 = new Triplet(subject + " 's birth place", "be", s);
			Triplet t2 = new Triplet(subject, "be bear in", s);
			birthInfo.add(t1);
			birthInfo.add(t2);
		}
		return birthInfo;
	}

}
