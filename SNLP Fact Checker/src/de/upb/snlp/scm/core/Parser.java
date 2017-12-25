package de.upb.snlp.scm.core;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import de.upb.snlp.scm.model.Triplet;
import de.upb.snlp.scm.util.Config;

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
			} else if (e.text().startsWith("Died")) {
				triplets.addAll(getDeathInfo(subject, e.text()));
			}
		}
		return triplets;
	}

	private static List<Triplet> getDeathInfo(String subject, String line) {
		List<Triplet> birthInfo = new ArrayList<>();
		Map<String, LinkedHashSet<String>> entityMap = NLP.findNamedEntities(line, Config.NER_3_CLASSIFIER);
		LinkedHashSet<String> locationSet = entityMap.get("LOCATION");
		for (String s : locationSet) {
			Triplet t1 = new Triplet(subject + " 's death place", "be", s);
			Triplet t2 = new Triplet(subject, "be die in", s);
			birthInfo.add(t1);
			birthInfo.add(t2);
		}
		return birthInfo;
	}

	public static List<Triplet> getBirthInfo(String subject, String line) {
		List<Triplet> birthInfo = new ArrayList<>();
		Map<String, LinkedHashSet<String>> entityMap = NLP.findNamedEntities(line, Config.NER_3_CLASSIFIER);
		LinkedHashSet<String> locationSet = entityMap.get("LOCATION");
		for (String s : locationSet) {
			Triplet t1 = new Triplet(subject + " 's birth place", "be", s);
			Triplet t2 = new Triplet(subject, "be bear in", s);
			birthInfo.add(t1);
			birthInfo.add(t2);
		}
		return birthInfo;
	}

	public static String normalize(String input) {
		if (input.contains("'")) {
			int apIndex = input.indexOf("'");
			input = input.substring(0, apIndex);
		}
		input = input.split(",")[0];
		return input.replaceAll("\\.", "").trim();
	}

	public static Triplet parseRegular(String input, String word, String relation) {
		int predIndex = input.indexOf(word);
		String subject = input.substring(0, predIndex);
		subject = Parser.normalize(subject);
		String object = input.substring(predIndex + word.length(), input.length());
		object = Parser.normalize(object);
		return new Triplet(subject, relation, object);
	}

	public static Triplet parseInverted(String input, String word, String relation) {
		int predIndex = input.indexOf(" is");
		String object = input.substring(0, predIndex);
		int honIndex = input.indexOf(word);
		String subject = input.substring(predIndex + 3, honIndex);
		subject = Parser.normalize(subject);
		object = Parser.normalize(object);
		return new Triplet(subject, relation, object);
	}

}
