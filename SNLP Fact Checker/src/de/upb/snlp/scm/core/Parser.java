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
import de.upb.snlp.scm.util.ListUtil;

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
		Elements infobox = doc.select("table[class*=infobox]");
		if (ListUtil.isNotEmpty(infobox)) {
			Elements infoboxRows = infobox.get(0).select("tbody").select("tr");
			for (Element e : infoboxRows) {
				String line = e.text();
				if (line.startsWith("Born")) {
					triplets.addAll(getBirthInfo(subject, line));
				} else if (line.startsWith("Died")) {
					triplets.addAll(getDeathInfo(subject, line));
				} else if (line.startsWith("Awards")) {
					triplets.addAll(getAwardInfo(subject, line));
				} else if (line.startsWith("Spouse")) {
					triplets.addAll(getSpouseInfo(subject, line));
				} else if (line.contains("Prime Minister of")) {
					triplets.addAll(getLeaderInfo(subject, line));
				} else if (line.contains("Founded")) {
					triplets.addAll(getFoundationInfo(subject, line));
				} else if (line.contains("Starring")) {
					triplets.addAll(getStarringInfo(subject, line));
				} else if (line.contains("Author")) {
					triplets.addAll(getStarringInfo(subject, line));
				}

				// else if (line.contains("Career history")) {
				// triplets.addAll(getTeamInfo(subject, line));
				// }
			}
		}

		return triplets;
	}

	public static List<Triplet> getFoundationInfo(String subject, String line) {
		List<Triplet> birthInfo = new ArrayList<>();
		Map<String, LinkedHashSet<String>> entityMap = NLP.findNamedEntities(line, Config.NER_3_CLASSIFIER);
		LinkedHashSet<String> locationSet = entityMap.get("LOCATION");

		if (locationSet != null) {
			for (String s : locationSet) {
				Triplet t1 = new Triplet(subject, Relation.FOUND, s);
				// Triplet t2 = new Triplet(subject, "be bear in", s);
				birthInfo.add(t1);
				// birthInfo.add(t2);
			}
		} else {
			locationSet = new LinkedHashSet<>();
		}

		String[] locations = line.substring(line.indexOf("Founded") + "Founded".length(), line.length()).split(",");

		for (String l : locations) {
			locationSet.add(l);
		}

		return birthInfo;
	}

	public static List<Triplet> getLeaderInfo(String subject, String line) {
		List<Triplet> info = new ArrayList<>();
		int index = line.indexOf("Prime Minister of");
		String country = line.substring(index + "Prime Minister of".length(), line.length());

		Triplet t1 = new Triplet(subject, Relation.LEADER, country.trim());
		info.add(t1);
		return info;
	}

	private static List<Triplet> getDeathInfo(String subject, String line) {
		List<Triplet> birthInfo = new ArrayList<>();
		Map<String, LinkedHashSet<String>> entityMap = NLP.findNamedEntities(line, Config.NER_3_CLASSIFIER);
		LinkedHashSet<String> locationSet = entityMap.get("LOCATION");

		if (locationSet != null) {
			for (String s : locationSet) {
				Triplet t1 = new Triplet(subject, Relation.DIE_IN, s);
				// Triplet t2 = new Triplet(subject, "be die in", s);
				birthInfo.add(t1);
				// birthInfo.add(t2);
			}
		}
		return birthInfo;
	}

	public static List<Triplet> getBirthInfo(String subject, String line) {
		List<Triplet> birthInfo = new ArrayList<>();
		Map<String, LinkedHashSet<String>> entityMap = NLP.findNamedEntities(line, Config.NER_3_CLASSIFIER);
		LinkedHashSet<String> locationSet = entityMap.get("LOCATION");
		if (locationSet != null) {
			for (String s : locationSet) {
				Triplet t1 = new Triplet(subject, Relation.BORN_IN, s);
				// Triplet t2 = new Triplet(subject, "be bear in", s);
				birthInfo.add(t1);
				// birthInfo.add(t2);
			}
		}

		return birthInfo;
	}

	public static List<Triplet> getAwardInfo(String subject, String line) {
		List<Triplet> info = new ArrayList<>();
		// Awards Nobel Peace Prize (1901)
		String[] awards = line.split("\\(\\d{4}\\)");

		for (String s : awards) {
			Triplet t1 = new Triplet(subject, Relation.AWARD, s.trim());
			info.add(t1);
		}
		return info;
	}

	public static List<Triplet> getStarringInfo(String subject, String line) {
		List<Triplet> info = new ArrayList<>();
		// Awards Nobel Peace Prize (1901)
		String[] stars = line.substring("Starring".length() + 1, line.length()).split(" ");

		for (String s : stars) {
			Triplet t1 = new Triplet(subject, Relation.STARS, s.trim());
			info.add(t1);
		}
		return info;
	}

	public static List<Triplet> getAuthorInfo(String subject, String line) {
		List<Triplet> info = new ArrayList<>();
		// Awards Nobel Peace Prize (1901)
		String[] authors = line.substring("Author".length() + 1, line.length()).split(" ");

		for (String s : authors) {
			Triplet t1 = new Triplet(subject, Relation.AUTHOR, s.trim());
			info.add(t1);
		}
		return info;
	}

	public static List<Triplet> getTeamInfo(String subject, String line) {
		List<Triplet> info = new ArrayList<>();
		// Awards Nobel Peace Prize (1901)
		// String[] awards = line.split("\\(\\d{4}\\)");
		System.out.println(line);

		// for (String s : awards) {
		// Triplet t1 = new Triplet(subject, Relation.AWARD, s.trim());
		// info.add(t1);
		// }
		return info;
	}

	public static List<Triplet> getSpouseInfo(String subject, String line) {
		List<Triplet> info = new ArrayList<>();
		// Awards Nobel Peace Prize (1901)
		String[] spouses = line.split("\\(\\*\\)");

		for (String s : spouses) {
			Triplet t1 = new Triplet(subject, Relation.SPOUSE, s.trim());
			info.add(t1);
		}
		return info;
	}

	public static String normalize(String input) {
		if (input.contains("'")) {
			int apIndex = input.lastIndexOf("'");
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
