package de.upb.snlp.scm;

import java.util.List;

import de.upb.snlp.scm.core.Network;
import de.upb.snlp.scm.core.Parser;
import de.upb.snlp.scm.core.Relation;
import de.upb.snlp.scm.model.Triplet;

/**
 * 
 * @author Kadiray Karakaya
 *
 */
public class Main {

	public static void main(String args[]) {

		// findExactInfo("Sinclair Lewis' award is Nobel Prize in Physics.");
		// findExactInfo("Nobel Peace Prize is Henry Dunant's honour.");
		// findExactInfo("Kate Winslet's spouse is Sam Mendes.");
		// findExactInfo("Tom Cruise is Katie Holmes' better half.");
		// findExactInfo("São Tomé and Príncipe is Aimo Cajander's role.");
		// findExactInfo("Dom Mintoff's office is Spain.");
		findExactInfo("Saginaw, Michigan is Stevie Wonder's nascence place.");
		findExactInfo("Martina Navratilova's birth place is Prague.");
		// findExactInfo("Kwame Brown's team is Detroit Pistons.");
		// findExactInfo("Golden State Warriors is Kwame Brown's squad.");
		// findExactInfo("Intec Telecom Systems' foundation place is Woking.");
		// findExactInfo("Hong Kong is Team and Concepts' innovation place.");
		findExactInfo("Hugo Chávez's death place is Caracas.");
		findExactInfo("Los Angeles is Fred Astaire's last place.");
		// findExactInfo("Mad Max stars Hugh Keays-Byrne.");
		// findExactInfo("John Wyndham is The Boat of a Million Years'
		// generator.");
		// findExactInfo("Neuromancer's author is Edwin Abbott Abbott.");
		// findExactInfo("Westwood Studios is Electronic Arts' subordinate.");
		// findExactInfo("Yahoo!'s subsidiary is Zimbra.");

		// String classifierPath = Config.NER_3_CLASSIFIER;
		//
		// String input = "Elizabeth Taylor's death place is London.";
		//
		// System.out.println(NLP.findNamedEntities(input, classifierPath));
		//
		// Map<String, LinkedHashSet<String>> EntityMap =
		// NLP.findNamedEntities(input, classifierPath);
		//
		// LinkedHashSet<String> personSet = EntityMap.get("PERSON");
		//
		// if (personSet == null) {
		// return;
		// }
		//
		// List<Triplet> inputRelations = NLP.findRelations(input);
		//
		// List<Triplet> inputList = new ArrayList<>();
		//
		// List<String> inputObjects = new ArrayList<>();
		//
		// for (Triplet t : inputRelations) {
		// inputList.add(t);
		// System.out.println(t.toString());
		// inputObjects.add(t.getObject());
		// }
		//
		// String articleTitle = "";
		// for (String s : personSet) {
		// articleTitle += s + "_";
		// }
		// if (articleTitle.endsWith("_")) {
		// articleTitle = articleTitle.substring(0, articleTitle.length() - 1);
		// }
		//
		// String corpus = Network.getCorpus(articleTitle);
		//
		// List<String> candidateSentences = NLP.findCandidateSentences(corpus,
		// inputObjects);
		//
		// List<Triplet> corpusRelations =
		// NLP.findRelations(candidateSentences);
		//
		// corpusRelations.addAll(Parser.getInfobox(articleTitle.replace("_", "
		// "), Network.doc));
		//
		// // List<Triplet> corpusRelations = NLP.findRelations(corpus);
		//
		// List<Triplet> corpusList = new ArrayList<>();
		// for (Triplet t : corpusRelations) {
		// corpusList.add(t);
		// }
		//
		// List<Pair<Triplet, Triplet>> pairs =
		// TripletUtil.findCandidatesForComparison(inputList, corpusList);
		//
		// Similarity similarity = new Similarity(Similarity.WU_PALMER);
		//
		// Map<Pair<Triplet, Triplet>, Double> similarities = new HashMap<>();
		//
		// int prefix = 0;
		// for (Pair<Triplet, Triplet> pair : pairs) {
		// pair = TripletUtil.preprocessTriples(pair.first, pair.second);
		// double sim = similarity.findSimilarity(pair.first, pair.second);
		// if (similarities.containsKey(pair)) {
		// prefix++;
		// pair.first.setSubject(prefix + pair.first.getSubject());
		// }
		// similarities.put(pair, sim);
		// }
		//
		// Map<Pair<Triplet, Triplet>, Double> sortedSimilarities =
		// MapUtil.sortByValue(similarities);
		//
		// for (Map.Entry<Pair<Triplet, Triplet>, Double> e :
		// sortedSimilarities.entrySet()) {
		// // System.out.println("sim: " + e.getValue() + " | " +
		// // e.getKey().first.toString() + " | "
		// // + e.getKey().second.toString());
		// for (String o : inputObjects) {
		// e.getKey().second.getObject().contains(o);
		// System.out.println("sim: " + e.getValue() + " | " +
		// e.getKey().first.toString() + " | "
		// + e.getKey().second.toString());
		// }
		// break;
		// }

	}

	private static void findExactInfo(String input) {

		// Map<String, LinkedHashSet<String>> EntityMap =
		// NLP.findNamedEntities(input, Config.NER_3_CLASSIFIER);

		// System.out.println(EntityMap);

		// LinkedHashSet<String> personSet = EntityMap.get("PERSON");
		//
		// if (personSet != null) {
		// // award
		// // birth
		// // death
		// //
		// return;
		// }

		// List<Triplet> inputRelations = NLP.findRelations(input);

		Triplet inputRelations = Relation.findRelation(input);

		String article = inputRelations.getSubject().replaceAll(" ", "_");

		String corpus = Network.getCorpus(article);

		List<Triplet> triplets = Parser.getInfobox(inputRelations.getSubject(), Network.doc);

		for (Triplet t : triplets) {
			if (t.getObject().contains(inputRelations.getObject())
					|| inputRelations.getObject().contains(t.getObject())) {
				System.out.println("sim: " + 1.0);
				break;
			} else {

			}
		}

		System.out.println(inputRelations);

		System.out.println("------------");

		// List<Triplet> inputList = new ArrayList<>();
		//
		// List<String> inputObjects = new ArrayList<>();
		//
		// for (Triplet t : inputRelations) {
		// inputList.add(t);
		// System.out.println(t.toString());
		// inputObjects.add(t.getObject());
		// }
		//
		// String articleTitle = "";
		// for (String s : personSet) {
		// articleTitle += s + "_";
		// }
		// if (articleTitle.endsWith("_")) {
		// articleTitle = articleTitle.substring(0, articleTitle.length() - 1);
		// }
		//
		// String corpus = Network.getCorpus(articleTitle);
		//
		// List<String> candidateSentences = NLP.findCandidateSentences(corpus,
		// inputObjects);
		//
		// List<Triplet> corpusRelations =
		// NLP.findRelations(candidateSentences);
		//
		// corpusRelations.addAll(Parser.getInfobox(articleTitle.replace("_", "
		// "), Network.doc));
		//
		// // List<Triplet> corpusRelations = NLP.findRelations(corpus);
		//
		// List<Triplet> corpusList = new ArrayList<>();
		// for (Triplet t : corpusRelations) {
		// corpusList.add(t);
		// }
		//
		// List<Pair<Triplet, Triplet>> pairs =
		// TripletUtil.findCandidatesForComparison(inputList, corpusList);
		//
		// Similarity similarity = new Similarity(Similarity.WU_PALMER);
		//
		// Map<Pair<Triplet, Triplet>, Double> similarities = new HashMap<>();
		//
		// int prefix = 0;
		// for (Pair<Triplet, Triplet> pair : pairs) {
		// pair = TripletUtil.preprocessTriples(pair.first, pair.second);
		// double sim = similarity.findSimilarity(pair.first, pair.second);
		// if (similarities.containsKey(pair)) {
		// prefix++;
		// pair.first.setSubject(prefix + pair.first.getSubject());
		// }
		// similarities.put(pair, sim);
		// }
		//
		// Map<Pair<Triplet, Triplet>, Double> sortedSimilarities =
		// MapUtil.sortByValue(similarities);
		//
		// for (Map.Entry<Pair<Triplet, Triplet>, Double> e :
		// sortedSimilarities.entrySet()) {
		// // System.out.println("sim: " + e.getValue() + " | " +
		// // e.getKey().first.toString() + " | "
		// // + e.getKey().second.toString());
		// for (String o : inputObjects) {
		// e.getKey().second.getObject().contains(o);
		// System.out.println("sim: " + e.getValue() + " | " +
		// e.getKey().first.toString() + " | "
		// + e.getKey().second.toString());
		// }
		// break;
		// }
	}

}
