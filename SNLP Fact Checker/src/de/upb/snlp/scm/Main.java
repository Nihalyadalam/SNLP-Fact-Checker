package de.upb.snlp.scm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import de.upb.snlp.scm.core.NLP;
import de.upb.snlp.scm.model.Triplet;
import de.upb.snlp.scm.util.Config;
import de.upb.snlp.scm.util.MapUtil;
import de.upb.snlp.scm.util.Network;
import de.upb.snlp.scm.util.Parser;
import de.upb.snlp.scm.util.Similarity;
import de.upb.snlp.scm.util.TripletUtil;
import edu.stanford.nlp.util.Pair;

/**
 * 
 * @author Kadiray Karakaya
 *
 */
public class Main {

	public static void main(String args[]) {

		String classifierPath = Config.NER_3_CLASSIFIER;

		String input = "Tony Blair's birth place is Edinburgh.";

		System.out.println(NLP.findNamedEntities(input, classifierPath));

		Map<String, LinkedHashSet<String>> EntityMap = NLP.findNamedEntities(input, classifierPath);

		LinkedHashSet<String> personSet = EntityMap.get("PERSON");

		List<Triplet> inputRelations = NLP.findRelations(input);

		List<Triplet> inputList = new ArrayList<>();

		List<String> inputObjects = new ArrayList<>();

		for (Triplet t : inputRelations) {
			inputList.add(t);
			System.out.println(t.toString());
			inputObjects.add(t.getObject());
		}

		String articleTitle = "";
		for (String s : personSet) {
			articleTitle += s + "_";
		}
		if (articleTitle.endsWith("_")) {
			articleTitle = articleTitle.substring(0, articleTitle.length() - 1);
		}

		String corpus = Network.getCorpus(articleTitle);

		List<String> candidateSentences = NLP.findCandidateSentences(corpus, inputObjects);

		List<Triplet> corpusRelations = NLP.findRelations(candidateSentences);

		corpusRelations.addAll(Parser.getInfobox(articleTitle.replace("_", " "), Network.doc));

		// List<Triplet> corpusRelations = NLP.findRelations(corpus);

		List<Triplet> corpusList = new ArrayList<>();
		for (Triplet t : corpusRelations) {
			corpusList.add(t);
		}

		List<Pair<Triplet, Triplet>> pairs = TripletUtil.findCandidatesForComparison(inputList, corpusList);

		Similarity similarity = new Similarity(Similarity.WU_PALMER);

		Map<Pair<Triplet, Triplet>, Double> similarities = new HashMap<>();

		int prefix = 0;
		for (Pair<Triplet, Triplet> pair : pairs) {
			pair = TripletUtil.preprocessTriples(pair.first, pair.second);
			double sim = similarity.findSimilarity(pair.first, pair.second);
			if (similarities.containsKey(pair)) {
				prefix++;
				pair.first.setSubject(prefix + pair.first.getSubject());
			}
			similarities.put(pair, sim);
		}

		Map<Pair<Triplet, Triplet>, Double> sortedSimilarities = MapUtil.sortByValue(similarities);

		for (Map.Entry<Pair<Triplet, Triplet>, Double> e : sortedSimilarities.entrySet()) {
			System.out.println("sim: " + e.getValue() + " | " + e.getKey().first.toString() + " | "
					+ e.getKey().second.toString());
		}

	}

}
